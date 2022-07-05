import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHoSo, HoSo } from '../ho-so.model';

import { HoSoService } from './ho-so.service';

describe('HoSo Service', () => {
  let service: HoSoService;
  let httpMock: HttpTestingController;
  let elemDefault: IHoSo;
  let expectedResult: IHoSo | IHoSo[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HoSoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      soThuTu: 'AAAAAAA',
      tenHoSo: 'AAAAAAA',
      noiLuu: 'AAAAAAA',
      thoiGianLuu: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          thoiGianLuu: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a HoSo', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          thoiGianLuu: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          thoiGianLuu: currentDate,
        },
        returnedFromService
      );

      service.create(new HoSo()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HoSo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          soThuTu: 'BBBBBB',
          tenHoSo: 'BBBBBB',
          noiLuu: 'BBBBBB',
          thoiGianLuu: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          thoiGianLuu: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a HoSo', () => {
      const patchObject = Object.assign(
        {
          soThuTu: 'BBBBBB',
          noiLuu: 'BBBBBB',
          thoiGianLuu: currentDate.format(DATE_TIME_FORMAT),
        },
        new HoSo()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          thoiGianLuu: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HoSo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          soThuTu: 'BBBBBB',
          tenHoSo: 'BBBBBB',
          noiLuu: 'BBBBBB',
          thoiGianLuu: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          thoiGianLuu: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a HoSo', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addHoSoToCollectionIfMissing', () => {
      it('should add a HoSo to an empty array', () => {
        const hoSo: IHoSo = { id: 123 };
        expectedResult = service.addHoSoToCollectionIfMissing([], hoSo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hoSo);
      });

      it('should not add a HoSo to an array that contains it', () => {
        const hoSo: IHoSo = { id: 123 };
        const hoSoCollection: IHoSo[] = [
          {
            ...hoSo,
          },
          { id: 456 },
        ];
        expectedResult = service.addHoSoToCollectionIfMissing(hoSoCollection, hoSo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HoSo to an array that doesn't contain it", () => {
        const hoSo: IHoSo = { id: 123 };
        const hoSoCollection: IHoSo[] = [{ id: 456 }];
        expectedResult = service.addHoSoToCollectionIfMissing(hoSoCollection, hoSo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hoSo);
      });

      it('should add only unique HoSo to an array', () => {
        const hoSoArray: IHoSo[] = [{ id: 123 }, { id: 456 }, { id: 48599 }];
        const hoSoCollection: IHoSo[] = [{ id: 123 }];
        expectedResult = service.addHoSoToCollectionIfMissing(hoSoCollection, ...hoSoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const hoSo: IHoSo = { id: 123 };
        const hoSo2: IHoSo = { id: 456 };
        expectedResult = service.addHoSoToCollectionIfMissing([], hoSo, hoSo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hoSo);
        expect(expectedResult).toContain(hoSo2);
      });

      it('should accept null and undefined values', () => {
        const hoSo: IHoSo = { id: 123 };
        expectedResult = service.addHoSoToCollectionIfMissing([], null, hoSo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hoSo);
      });

      it('should return initial array if no HoSo is added', () => {
        const hoSoCollection: IHoSo[] = [{ id: 123 }];
        expectedResult = service.addHoSoToCollectionIfMissing(hoSoCollection, undefined, null);
        expect(expectedResult).toEqual(hoSoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
