import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ThongTinNguoiBenhService } from '../service/thong-tin-nguoi-benh.service';
import { IThongTinNguoiBenh, ThongTinNguoiBenh } from '../thong-tin-nguoi-benh.model';

import { ThongTinNguoiBenhUpdateComponent } from './thong-tin-nguoi-benh-update.component';

describe('ThongTinNguoiBenh Management Update Component', () => {
  let comp: ThongTinNguoiBenhUpdateComponent;
  let fixture: ComponentFixture<ThongTinNguoiBenhUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let thongTinNguoiBenhService: ThongTinNguoiBenhService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ThongTinNguoiBenhUpdateComponent],
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
      .overrideTemplate(ThongTinNguoiBenhUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ThongTinNguoiBenhUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    thongTinNguoiBenhService = TestBed.inject(ThongTinNguoiBenhService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const thongTinNguoiBenh: IThongTinNguoiBenh = { id: 456 };

      activatedRoute.data = of({ thongTinNguoiBenh });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(thongTinNguoiBenh));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ThongTinNguoiBenh>>();
      const thongTinNguoiBenh = { id: 123 };
      jest.spyOn(thongTinNguoiBenhService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thongTinNguoiBenh });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: thongTinNguoiBenh }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(thongTinNguoiBenhService.update).toHaveBeenCalledWith(thongTinNguoiBenh);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ThongTinNguoiBenh>>();
      const thongTinNguoiBenh = new ThongTinNguoiBenh();
      jest.spyOn(thongTinNguoiBenhService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thongTinNguoiBenh });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: thongTinNguoiBenh }));
      saveSubject.complete();

      // THEN
      expect(thongTinNguoiBenhService.create).toHaveBeenCalledWith(thongTinNguoiBenh);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ThongTinNguoiBenh>>();
      const thongTinNguoiBenh = { id: 123 };
      jest.spyOn(thongTinNguoiBenhService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thongTinNguoiBenh });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(thongTinNguoiBenhService.update).toHaveBeenCalledWith(thongTinNguoiBenh);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
