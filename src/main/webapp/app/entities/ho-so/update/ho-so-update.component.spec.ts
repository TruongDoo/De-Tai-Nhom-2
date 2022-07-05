import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HoSoService } from '../service/ho-so.service';
import { IHoSo, HoSo } from '../ho-so.model';
import { IQuanLySuCoYKhoa } from 'app/entities/quan-ly-su-co-y-khoa/quan-ly-su-co-y-khoa.model';
import { QuanLySuCoYKhoaService } from 'app/entities/quan-ly-su-co-y-khoa/service/quan-ly-su-co-y-khoa.service';
import { IPhuLuc } from 'app/entities/phu-luc/phu-luc.model';
import { PhuLucService } from 'app/entities/phu-luc/service/phu-luc.service';

import { HoSoUpdateComponent } from './ho-so-update.component';

describe('HoSo Management Update Component', () => {
  let comp: HoSoUpdateComponent;
  let fixture: ComponentFixture<HoSoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hoSoService: HoSoService;
  let quanLySuCoYKhoaService: QuanLySuCoYKhoaService;
  let phuLucService: PhuLucService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HoSoUpdateComponent],
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
      .overrideTemplate(HoSoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HoSoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hoSoService = TestBed.inject(HoSoService);
    quanLySuCoYKhoaService = TestBed.inject(QuanLySuCoYKhoaService);
    phuLucService = TestBed.inject(PhuLucService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call QuanLySuCoYKhoa query and add missing value', () => {
      const hoSo: IHoSo = { id: 456 };
      const quanLySuCoYKhoa: IQuanLySuCoYKhoa = { id: 81947 };
      hoSo.quanLySuCoYKhoa = quanLySuCoYKhoa;

      const quanLySuCoYKhoaCollection: IQuanLySuCoYKhoa[] = [{ id: 79751 }];
      jest.spyOn(quanLySuCoYKhoaService, 'query').mockReturnValue(of(new HttpResponse({ body: quanLySuCoYKhoaCollection })));
      const additionalQuanLySuCoYKhoas = [quanLySuCoYKhoa];
      const expectedCollection: IQuanLySuCoYKhoa[] = [...additionalQuanLySuCoYKhoas, ...quanLySuCoYKhoaCollection];
      jest.spyOn(quanLySuCoYKhoaService, 'addQuanLySuCoYKhoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hoSo });
      comp.ngOnInit();

      expect(quanLySuCoYKhoaService.query).toHaveBeenCalled();
      expect(quanLySuCoYKhoaService.addQuanLySuCoYKhoaToCollectionIfMissing).toHaveBeenCalledWith(
        quanLySuCoYKhoaCollection,
        ...additionalQuanLySuCoYKhoas
      );
      expect(comp.quanLySuCoYKhoasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PhuLuc query and add missing value', () => {
      const hoSo: IHoSo = { id: 456 };
      const phuLuc: IPhuLuc = { id: 72516 };
      hoSo.phuLuc = phuLuc;

      const phuLucCollection: IPhuLuc[] = [{ id: 44121 }];
      jest.spyOn(phuLucService, 'query').mockReturnValue(of(new HttpResponse({ body: phuLucCollection })));
      const additionalPhuLucs = [phuLuc];
      const expectedCollection: IPhuLuc[] = [...additionalPhuLucs, ...phuLucCollection];
      jest.spyOn(phuLucService, 'addPhuLucToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hoSo });
      comp.ngOnInit();

      expect(phuLucService.query).toHaveBeenCalled();
      expect(phuLucService.addPhuLucToCollectionIfMissing).toHaveBeenCalledWith(phuLucCollection, ...additionalPhuLucs);
      expect(comp.phuLucsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const hoSo: IHoSo = { id: 456 };
      const quanLySuCoYKhoa: IQuanLySuCoYKhoa = { id: 69940 };
      hoSo.quanLySuCoYKhoa = quanLySuCoYKhoa;
      const phuLuc: IPhuLuc = { id: 20348 };
      hoSo.phuLuc = phuLuc;

      activatedRoute.data = of({ hoSo });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(hoSo));
      expect(comp.quanLySuCoYKhoasSharedCollection).toContain(quanLySuCoYKhoa);
      expect(comp.phuLucsSharedCollection).toContain(phuLuc);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HoSo>>();
      const hoSo = { id: 123 };
      jest.spyOn(hoSoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hoSo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hoSo }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(hoSoService.update).toHaveBeenCalledWith(hoSo);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HoSo>>();
      const hoSo = new HoSo();
      jest.spyOn(hoSoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hoSo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hoSo }));
      saveSubject.complete();

      // THEN
      expect(hoSoService.create).toHaveBeenCalledWith(hoSo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HoSo>>();
      const hoSo = { id: 123 };
      jest.spyOn(hoSoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hoSo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hoSoService.update).toHaveBeenCalledWith(hoSo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackQuanLySuCoYKhoaById', () => {
      it('Should return tracked QuanLySuCoYKhoa primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackQuanLySuCoYKhoaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPhuLucById', () => {
      it('Should return tracked PhuLuc primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPhuLucById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
