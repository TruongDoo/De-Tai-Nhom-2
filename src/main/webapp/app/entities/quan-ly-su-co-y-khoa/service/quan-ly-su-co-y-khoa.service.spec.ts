import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IQuanLySuCoYKhoa, QuanLySuCoYKhoa } from '../quan-ly-su-co-y-khoa.model';

import { QuanLySuCoYKhoaService } from './quan-ly-su-co-y-khoa.service';

describe('QuanLySuCoYKhoa Service', () => {
  let service: QuanLySuCoYKhoaService;
  let httpMock: HttpTestingController;
  let elemDefault: IQuanLySuCoYKhoa;
  let expectedResult: IQuanLySuCoYKhoa | IQuanLySuCoYKhoa[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(QuanLySuCoYKhoaService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      maSoSuCo: 'AAAAAAA',
      ngayBaoCao: currentDate,
      donViBaoCao: 'AAAAAAA',
      soThuTu: 'AAAAAAA',
      tenSuCo: 'AAAAAAA',
      nhomSuCo: 'AAAAAAA',
      mucDoSuCo: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          ngayBaoCao: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a QuanLySuCoYKhoa', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          ngayBaoCao: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          ngayBaoCao: currentDate,
        },
        returnedFromService
      );

      service.create(new QuanLySuCoYKhoa()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a QuanLySuCoYKhoa', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          maSoSuCo: 'BBBBBB',
          ngayBaoCao: currentDate.format(DATE_TIME_FORMAT),
          donViBaoCao: 'BBBBBB',
          soThuTu: 'BBBBBB',
          tenSuCo: 'BBBBBB',
          nhomSuCo: 'BBBBBB',
          mucDoSuCo: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          ngayBaoCao: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a QuanLySuCoYKhoa', () => {
      const patchObject = Object.assign(
        {
          soThuTu: 'BBBBBB',
          nhomSuCo: 'BBBBBB',
          mucDoSuCo: 'BBBBBB',
        },
        new QuanLySuCoYKhoa()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          ngayBaoCao: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of QuanLySuCoYKhoa', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          maSoSuCo: 'BBBBBB',
          ngayBaoCao: currentDate.format(DATE_TIME_FORMAT),
          donViBaoCao: 'BBBBBB',
          soThuTu: 'BBBBBB',
          tenSuCo: 'BBBBBB',
          nhomSuCo: 'BBBBBB',
          mucDoSuCo: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          ngayBaoCao: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a QuanLySuCoYKhoa', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addQuanLySuCoYKhoaToCollectionIfMissing', () => {
      it('should add a QuanLySuCoYKhoa to an empty array', () => {
        const quanLySuCoYKhoa: IQuanLySuCoYKhoa = { id: 123 };
        expectedResult = service.addQuanLySuCoYKhoaToCollectionIfMissing([], quanLySuCoYKhoa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(quanLySuCoYKhoa);
      });

      it('should not add a QuanLySuCoYKhoa to an array that contains it', () => {
        const quanLySuCoYKhoa: IQuanLySuCoYKhoa = { id: 123 };
        const quanLySuCoYKhoaCollection: IQuanLySuCoYKhoa[] = [
          {
            ...quanLySuCoYKhoa,
          },
          { id: 456 },
        ];
        expectedResult = service.addQuanLySuCoYKhoaToCollectionIfMissing(quanLySuCoYKhoaCollection, quanLySuCoYKhoa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a QuanLySuCoYKhoa to an array that doesn't contain it", () => {
        const quanLySuCoYKhoa: IQuanLySuCoYKhoa = { id: 123 };
        const quanLySuCoYKhoaCollection: IQuanLySuCoYKhoa[] = [{ id: 456 }];
        expectedResult = service.addQuanLySuCoYKhoaToCollectionIfMissing(quanLySuCoYKhoaCollection, quanLySuCoYKhoa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(quanLySuCoYKhoa);
      });

      it('should add only unique QuanLySuCoYKhoa to an array', () => {
        const quanLySuCoYKhoaArray: IQuanLySuCoYKhoa[] = [{ id: 123 }, { id: 456 }, { id: 79603 }];
        const quanLySuCoYKhoaCollection: IQuanLySuCoYKhoa[] = [{ id: 123 }];
        expectedResult = service.addQuanLySuCoYKhoaToCollectionIfMissing(quanLySuCoYKhoaCollection, ...quanLySuCoYKhoaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const quanLySuCoYKhoa: IQuanLySuCoYKhoa = { id: 123 };
        const quanLySuCoYKhoa2: IQuanLySuCoYKhoa = { id: 456 };
        expectedResult = service.addQuanLySuCoYKhoaToCollectionIfMissing([], quanLySuCoYKhoa, quanLySuCoYKhoa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(quanLySuCoYKhoa);
        expect(expectedResult).toContain(quanLySuCoYKhoa2);
      });

      it('should accept null and undefined values', () => {
        const quanLySuCoYKhoa: IQuanLySuCoYKhoa = { id: 123 };
        expectedResult = service.addQuanLySuCoYKhoaToCollectionIfMissing([], null, quanLySuCoYKhoa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(quanLySuCoYKhoa);
      });

      it('should return initial array if no QuanLySuCoYKhoa is added', () => {
        const quanLySuCoYKhoaCollection: IQuanLySuCoYKhoa[] = [{ id: 123 }];
        expectedResult = service.addQuanLySuCoYKhoaToCollectionIfMissing(quanLySuCoYKhoaCollection, undefined, null);
        expect(expectedResult).toEqual(quanLySuCoYKhoaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
