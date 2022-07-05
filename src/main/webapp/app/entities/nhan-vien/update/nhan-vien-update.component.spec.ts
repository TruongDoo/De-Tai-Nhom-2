import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NhanVienService } from '../service/nhan-vien.service';
import { INhanVien, NhanVien } from '../nhan-vien.model';

import { NhanVienUpdateComponent } from './nhan-vien-update.component';

describe('NhanVien Management Update Component', () => {
  let comp: NhanVienUpdateComponent;
  let fixture: ComponentFixture<NhanVienUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nhanVienService: NhanVienService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NhanVienUpdateComponent],
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
      .overrideTemplate(NhanVienUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NhanVienUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nhanVienService = TestBed.inject(NhanVienService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const nhanVien: INhanVien = { id: 456 };

      activatedRoute.data = of({ nhanVien });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(nhanVien));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NhanVien>>();
      const nhanVien = { id: 123 };
      jest.spyOn(nhanVienService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nhanVien });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nhanVien }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(nhanVienService.update).toHaveBeenCalledWith(nhanVien);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NhanVien>>();
      const nhanVien = new NhanVien();
      jest.spyOn(nhanVienService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nhanVien });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nhanVien }));
      saveSubject.complete();

      // THEN
      expect(nhanVienService.create).toHaveBeenCalledWith(nhanVien);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<NhanVien>>();
      const nhanVien = { id: 123 };
      jest.spyOn(nhanVienService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nhanVien });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nhanVienService.update).toHaveBeenCalledWith(nhanVien);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
