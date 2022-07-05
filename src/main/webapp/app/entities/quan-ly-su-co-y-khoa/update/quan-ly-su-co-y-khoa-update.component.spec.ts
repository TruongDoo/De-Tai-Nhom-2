import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { QuanLySuCoYKhoaService } from '../service/quan-ly-su-co-y-khoa.service';
import { IQuanLySuCoYKhoa, QuanLySuCoYKhoa } from '../quan-ly-su-co-y-khoa.model';
import { IThongTinSuCo } from 'app/entities/thong-tin-su-co/thong-tin-su-co.model';
import { ThongTinSuCoService } from 'app/entities/thong-tin-su-co/service/thong-tin-su-co.service';
import { IThongTinNguoiBenh } from 'app/entities/thong-tin-nguoi-benh/thong-tin-nguoi-benh.model';
import { ThongTinNguoiBenhService } from 'app/entities/thong-tin-nguoi-benh/service/thong-tin-nguoi-benh.service';
import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';
import { NhanVienService } from 'app/entities/nhan-vien/service/nhan-vien.service';

import { QuanLySuCoYKhoaUpdateComponent } from './quan-ly-su-co-y-khoa-update.component';

describe('QuanLySuCoYKhoa Management Update Component', () => {
  let comp: QuanLySuCoYKhoaUpdateComponent;
  let fixture: ComponentFixture<QuanLySuCoYKhoaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let quanLySuCoYKhoaService: QuanLySuCoYKhoaService;
  let thongTinSuCoService: ThongTinSuCoService;
  let thongTinNguoiBenhService: ThongTinNguoiBenhService;
  let nhanVienService: NhanVienService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [QuanLySuCoYKhoaUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(QuanLySuCoYKhoaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(QuanLySuCoYKhoaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    quanLySuCoYKhoaService = TestBed.inject(QuanLySuCoYKhoaService);
    thongTinSuCoService = TestBed.inject(ThongTinSuCoService);
    thongTinNguoiBenhService = TestBed.inject(ThongTinNguoiBenhService);
    nhanVienService = TestBed.inject(NhanVienService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ThongTinSuCo query and add missing value', () => {
      const quanLySuCoYKhoa: IQuanLySuCoYKhoa = { id: 456 };
      const thongTinSuCo: IThongTinSuCo = { id: 67219 };
      quanLySuCoYKhoa.thongTinSuCo = thongTinSuCo;

      const thongTinSuCoCollection: IThongTinSuCo[] = [{ id: 17285 }];
      jest.spyOn(thongTinSuCoService, 'query').mockReturnValue(of(new HttpResponse({ body: thongTinSuCoCollection })));
      const additionalThongTinSuCos = [thongTinSuCo];
      const expectedCollection: IThongTinSuCo[] = [...additionalThongTinSuCos, ...thongTinSuCoCollection];
      jest.spyOn(thongTinSuCoService, 'addThongTinSuCoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ quanLySuCoYKhoa });
      comp.ngOnInit();

      expect(thongTinSuCoService.query).toHaveBeenCalled();
      expect(thongTinSuCoService.addThongTinSuCoToCollectionIfMissing).toHaveBeenCalledWith(
        thongTinSuCoCollection,
        ...additionalThongTinSuCos
      );
      expect(comp.thongTinSuCosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ThongTinNguoiBenh query and add missing value', () => {
      const quanLySuCoYKhoa: IQuanLySuCoYKhoa = { id: 456 };
      const thongTinNguoiBenh: IThongTinNguoiBenh = { id: 94143 };
      quanLySuCoYKhoa.thongTinNguoiBenh = thongTinNguoiBenh;

      const thongTinNguoiBenhCollection: IThongTinNguoiBenh[] = [{ id: 27321 }];
      jest.spyOn(thongTinNguoiBenhService, 'query').mockReturnValue(of(new HttpResponse({ body: thongTinNguoiBenhCollection })));
      const additionalThongTinNguoiBenhs = [thongTinNguoiBenh];
      const expectedCollection: IThongTinNguoiBenh[] = [...additionalThongTinNguoiBenhs, ...thongTinNguoiBenhCollection];
      jest.spyOn(thongTinNguoiBenhService, 'addThongTinNguoiBenhToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ quanLySuCoYKhoa });
      comp.ngOnInit();

      expect(thongTinNguoiBenhService.query).toHaveBeenCalled();
      expect(thongTinNguoiBenhService.addThongTinNguoiBenhToCollectionIfMissing).toHaveBeenCalledWith(
        thongTinNguoiBenhCollection,
        ...additionalThongTinNguoiBenhs
      );
      expect(comp.thongTinNguoiBenhsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NhanVien query and add missing value', () => {
      const quanLySuCoYKhoa: IQuanLySuCoYKhoa = { id: 456 };
      const quanLySuCoYKhoa: INhanVien = { id: 56436 };
      quanLySuCoYKhoa.quanLySuCoYKhoa = quanLySuCoYKhoa;

      const nhanVienCollection: INhanVien[] = [{ id: 75742 }];
      jest.spyOn(nhanVienService, 'query').mockReturnValue(of(new HttpResponse({ body: nhanVienCollection })));
      const additionalNhanViens = [quanLySuCoYKhoa];
      const expectedCollection: INhanVien[] = [...additionalNhanViens, ...nhanVienCollection];
      jest.spyOn(nhanVienService, 'addNhanVienToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ quanLySuCoYKhoa });
      comp.ngOnInit();

      expect(nhanVienService.query).toHaveBeenCalled();
      expect(nhanVienService.addNhanVienToCollectionIfMissing).toHaveBeenCalledWith(nhanVienCollection, ...additionalNhanViens);
      expect(comp.nhanViensSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const quanLySuCoYKhoa: IQuanLySuCoYKhoa = { id: 456 };
      const thongTinSuCo: IThongTinSuCo = { id: 84854 };
      quanLySuCoYKhoa.thongTinSuCo = thongTinSuCo;
      const thongTinNguoiBenh: IThongTinNguoiBenh = { id: 27236 };
      quanLySuCoYKhoa.thongTinNguoiBenh = thongTinNguoiBenh;
      const quanLySuCoYKhoa: INhanVien = { id: 39951 };
      quanLySuCoYKhoa.quanLySuCoYKhoa = quanLySuCoYKhoa;

      activatedRoute.data = of({ quanLySuCoYKhoa });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(quanLySuCoYKhoa));
      expect(comp.thongTinSuCosSharedCollection).toContain(thongTinSuCo);
      expect(comp.thongTinNguoiBenhsSharedCollection).toContain(thongTinNguoiBenh);
      expect(comp.nhanViensSharedCollection).toContain(quanLySuCoYKhoa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<QuanLySuCoYKhoa>>();
      const quanLySuCoYKhoa = { id: 123 };
      jest.spyOn(quanLySuCoYKhoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ quanLySuCoYKhoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: quanLySuCoYKhoa }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(quanLySuCoYKhoaService.update).toHaveBeenCalledWith(quanLySuCoYKhoa);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<QuanLySuCoYKhoa>>();
      const quanLySuCoYKhoa = new QuanLySuCoYKhoa();
      jest.spyOn(quanLySuCoYKhoaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ quanLySuCoYKhoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: quanLySuCoYKhoa }));
      saveSubject.complete();

      // THEN
      expect(quanLySuCoYKhoaService.create).toHaveBeenCalledWith(quanLySuCoYKhoa);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<QuanLySuCoYKhoa>>();
      const quanLySuCoYKhoa = { id: 123 };
      jest.spyOn(quanLySuCoYKhoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ quanLySuCoYKhoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(quanLySuCoYKhoaService.update).toHaveBeenCalledWith(quanLySuCoYKhoa);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackThongTinSuCoById', () => {
      it('Should return tracked ThongTinSuCo primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackThongTinSuCoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackThongTinNguoiBenhById', () => {
      it('Should return tracked ThongTinNguoiBenh primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackThongTinNguoiBenhById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackNhanVienById', () => {
      it('Should return tracked NhanVien primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNhanVienById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
