import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPhuLuc, PhuLuc } from '../phu-luc.model';

import { PhuLucService } from './phu-luc.service';

describe('PhuLuc Service', () => {
  let service: PhuLucService;
  let httpMock: HttpTestingController;
  let elemDefault: IPhuLuc;
  let expectedResult: IPhuLuc | IPhuLuc[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PhuLucService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      soThuTu: 'AAAAAAA',
      tenBieuMau: 'AAAAAAA',
      maHieu: 'AAAAAAA',
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

    it('should create a PhuLuc', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PhuLuc()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PhuLuc', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          soThuTu: 'BBBBBB',
          tenBieuMau: 'BBBBBB',
          maHieu: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PhuLuc', () => {
      const patchObject = Object.assign(
        {
          maHieu: 'BBBBBB',
        },
        new PhuLuc()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PhuLuc', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          soThuTu: 'BBBBBB',
          tenBieuMau: 'BBBBBB',
          maHieu: 'BBBBBB',
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

    it('should delete a PhuLuc', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPhuLucToCollectionIfMissing', () => {
      it('should add a PhuLuc to an empty array', () => {
        const phuLuc: IPhuLuc = { id: 123 };
        expectedResult = service.addPhuLucToCollectionIfMissing([], phuLuc);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(phuLuc);
      });

      it('should not add a PhuLuc to an array that contains it', () => {
        const phuLuc: IPhuLuc = { id: 123 };
        const phuLucCollection: IPhuLuc[] = [
          {
            ...phuLuc,
          },
          { id: 456 },
        ];
        expectedResult = service.addPhuLucToCollectionIfMissing(phuLucCollection, phuLuc);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PhuLuc to an array that doesn't contain it", () => {
        const phuLuc: IPhuLuc = { id: 123 };
        const phuLucCollection: IPhuLuc[] = [{ id: 456 }];
        expectedResult = service.addPhuLucToCollectionIfMissing(phuLucCollection, phuLuc);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(phuLuc);
      });

      it('should add only unique PhuLuc to an array', () => {
        const phuLucArray: IPhuLuc[] = [{ id: 123 }, { id: 456 }, { id: 73589 }];
        const phuLucCollection: IPhuLuc[] = [{ id: 123 }];
        expectedResult = service.addPhuLucToCollectionIfMissing(phuLucCollection, ...phuLucArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const phuLuc: IPhuLuc = { id: 123 };
        const phuLuc2: IPhuLuc = { id: 456 };
        expectedResult = service.addPhuLucToCollectionIfMissing([], phuLuc, phuLuc2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(phuLuc);
        expect(expectedResult).toContain(phuLuc2);
      });

      it('should accept null and undefined values', () => {
        const phuLuc: IPhuLuc = { id: 123 };
        expectedResult = service.addPhuLucToCollectionIfMissing([], null, phuLuc, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(phuLuc);
      });

      it('should return initial array if no PhuLuc is added', () => {
        const phuLucCollection: IPhuLuc[] = [{ id: 123 }];
        expectedResult = service.addPhuLucToCollectionIfMissing(phuLucCollection, undefined, null);
        expect(expectedResult).toEqual(phuLucCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
