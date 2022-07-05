import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IThongTinNguoiBenh, ThongTinNguoiBenh } from '../thong-tin-nguoi-benh.model';

import { ThongTinNguoiBenhService } from './thong-tin-nguoi-benh.service';

describe('ThongTinNguoiBenh Service', () => {
  let service: ThongTinNguoiBenhService;
  let httpMock: HttpTestingController;
  let elemDefault: IThongTinNguoiBenh;
  let expectedResult: IThongTinNguoiBenh | IThongTinNguoiBenh[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ThongTinNguoiBenhService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      hoVaTen: 'AAAAAAA',
      soBenhAn: 'AAAAAAA',
      gioiTinh: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ThongTinNguoiBenh', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ThongTinNguoiBenh()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ThongTinNguoiBenh', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          hoVaTen: 'BBBBBB',
          soBenhAn: 'BBBBBB',
          gioiTinh: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ThongTinNguoiBenh', () => {
      const patchObject = Object.assign(
        {
          soBenhAn: 'BBBBBB',
        },
        new ThongTinNguoiBenh()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ThongTinNguoiBenh', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          hoVaTen: 'BBBBBB',
          soBenhAn: 'BBBBBB',
          gioiTinh: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ThongTinNguoiBenh', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addThongTinNguoiBenhToCollectionIfMissing', () => {
      it('should add a ThongTinNguoiBenh to an empty array', () => {
        const thongTinNguoiBenh: IThongTinNguoiBenh = { id: 123 };
        expectedResult = service.addThongTinNguoiBenhToCollectionIfMissing([], thongTinNguoiBenh);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(thongTinNguoiBenh);
      });

      it('should not add a ThongTinNguoiBenh to an array that contains it', () => {
        const thongTinNguoiBenh: IThongTinNguoiBenh = { id: 123 };
        const thongTinNguoiBenhCollection: IThongTinNguoiBenh[] = [
          {
            ...thongTinNguoiBenh,
          },
          { id: 456 },
        ];
        expectedResult = service.addThongTinNguoiBenhToCollectionIfMissing(thongTinNguoiBenhCollection, thongTinNguoiBenh);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ThongTinNguoiBenh to an array that doesn't contain it", () => {
        const thongTinNguoiBenh: IThongTinNguoiBenh = { id: 123 };
        const thongTinNguoiBenhCollection: IThongTinNguoiBenh[] = [{ id: 456 }];
        expectedResult = service.addThongTinNguoiBenhToCollectionIfMissing(thongTinNguoiBenhCollection, thongTinNguoiBenh);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(thongTinNguoiBenh);
      });

      it('should add only unique ThongTinNguoiBenh to an array', () => {
        const thongTinNguoiBenhArray: IThongTinNguoiBenh[] = [{ id: 123 }, { id: 456 }, { id: 21692 }];
        const thongTinNguoiBenhCollection: IThongTinNguoiBenh[] = [{ id: 123 }];
        expectedResult = service.addThongTinNguoiBenhToCollectionIfMissing(thongTinNguoiBenhCollection, ...thongTinNguoiBenhArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const thongTinNguoiBenh: IThongTinNguoiBenh = { id: 123 };
        const thongTinNguoiBenh2: IThongTinNguoiBenh = { id: 456 };
        expectedResult = service.addThongTinNguoiBenhToCollectionIfMissing([], thongTinNguoiBenh, thongTinNguoiBenh2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(thongTinNguoiBenh);
        expect(expectedResult).toContain(thongTinNguoiBenh2);
      });

      it('should accept null and undefined values', () => {
        const thongTinNguoiBenh: IThongTinNguoiBenh = { id: 123 };
        expectedResult = service.addThongTinNguoiBenhToCollectionIfMissing([], null, thongTinNguoiBenh, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(thongTinNguoiBenh);
      });

      it('should return initial array if no ThongTinNguoiBenh is added', () => {
        const thongTinNguoiBenhCollection: IThongTinNguoiBenh[] = [{ id: 123 }];
        expectedResult = service.addThongTinNguoiBenhToCollectionIfMissing(thongTinNguoiBenhCollection, undefined, null);
        expect(expectedResult).toEqual(thongTinNguoiBenhCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
