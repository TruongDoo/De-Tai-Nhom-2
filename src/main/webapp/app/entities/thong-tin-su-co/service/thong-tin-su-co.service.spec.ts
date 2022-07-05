import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IThongTinSuCo, ThongTinSuCo } from '../thong-tin-su-co.model';

import { ThongTinSuCoService } from './thong-tin-su-co.service';

describe('ThongTinSuCo Service', () => {
  let service: ThongTinSuCoService;
  let httpMock: HttpTestingController;
  let elemDefault: IThongTinSuCo;
  let expectedResult: IThongTinSuCo | IThongTinSuCo[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ThongTinSuCoService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      viTriXayRaSuCo: 'AAAAAAA',
      ngayXayRaSuCo: currentDate,
      thoiGian: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          ngayXayRaSuCo: currentDate.format(DATE_TIME_FORMAT),
          thoiGian: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ThongTinSuCo', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          ngayXayRaSuCo: currentDate.format(DATE_TIME_FORMAT),
          thoiGian: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          ngayXayRaSuCo: currentDate,
          thoiGian: currentDate,
        },
        returnedFromService
      );

      service.create(new ThongTinSuCo()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ThongTinSuCo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          viTriXayRaSuCo: 'BBBBBB',
          ngayXayRaSuCo: currentDate.format(DATE_TIME_FORMAT),
          thoiGian: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          ngayXayRaSuCo: currentDate,
          thoiGian: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ThongTinSuCo', () => {
      const patchObject = Object.assign(
        {
          viTriXayRaSuCo: 'BBBBBB',
          ngayXayRaSuCo: currentDate.format(DATE_TIME_FORMAT),
          thoiGian: currentDate.format(DATE_TIME_FORMAT),
        },
        new ThongTinSuCo()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          ngayXayRaSuCo: currentDate,
          thoiGian: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ThongTinSuCo', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          viTriXayRaSuCo: 'BBBBBB',
          ngayXayRaSuCo: currentDate.format(DATE_TIME_FORMAT),
          thoiGian: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          ngayXayRaSuCo: currentDate,
          thoiGian: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ThongTinSuCo', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addThongTinSuCoToCollectionIfMissing', () => {
      it('should add a ThongTinSuCo to an empty array', () => {
        const thongTinSuCo: IThongTinSuCo = { id: 123 };
        expectedResult = service.addThongTinSuCoToCollectionIfMissing([], thongTinSuCo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(thongTinSuCo);
      });

      it('should not add a ThongTinSuCo to an array that contains it', () => {
        const thongTinSuCo: IThongTinSuCo = { id: 123 };
        const thongTinSuCoCollection: IThongTinSuCo[] = [
          {
            ...thongTinSuCo,
          },
          { id: 456 },
        ];
        expectedResult = service.addThongTinSuCoToCollectionIfMissing(thongTinSuCoCollection, thongTinSuCo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ThongTinSuCo to an array that doesn't contain it", () => {
        const thongTinSuCo: IThongTinSuCo = { id: 123 };
        const thongTinSuCoCollection: IThongTinSuCo[] = [{ id: 456 }];
        expectedResult = service.addThongTinSuCoToCollectionIfMissing(thongTinSuCoCollection, thongTinSuCo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(thongTinSuCo);
      });

      it('should add only unique ThongTinSuCo to an array', () => {
        const thongTinSuCoArray: IThongTinSuCo[] = [{ id: 123 }, { id: 456 }, { id: 44315 }];
        const thongTinSuCoCollection: IThongTinSuCo[] = [{ id: 123 }];
        expectedResult = service.addThongTinSuCoToCollectionIfMissing(thongTinSuCoCollection, ...thongTinSuCoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const thongTinSuCo: IThongTinSuCo = { id: 123 };
        const thongTinSuCo2: IThongTinSuCo = { id: 456 };
        expectedResult = service.addThongTinSuCoToCollectionIfMissing([], thongTinSuCo, thongTinSuCo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(thongTinSuCo);
        expect(expectedResult).toContain(thongTinSuCo2);
      });

      it('should accept null and undefined values', () => {
        const thongTinSuCo: IThongTinSuCo = { id: 123 };
        expectedResult = service.addThongTinSuCoToCollectionIfMissing([], null, thongTinSuCo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(thongTinSuCo);
      });

      it('should return initial array if no ThongTinSuCo is added', () => {
        const thongTinSuCoCollection: IThongTinSuCo[] = [{ id: 123 }];
        expectedResult = service.addThongTinSuCoToCollectionIfMissing(thongTinSuCoCollection, undefined, null);
        expect(expectedResult).toEqual(thongTinSuCoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
