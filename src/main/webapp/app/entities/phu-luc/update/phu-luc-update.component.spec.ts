import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PhuLucService } from '../service/phu-luc.service';
import { IPhuLuc, PhuLuc } from '../phu-luc.model';

import { PhuLucUpdateComponent } from './phu-luc-update.component';

describe('PhuLuc Management Update Component', () => {
  let comp: PhuLucUpdateComponent;
  let fixture: ComponentFixture<PhuLucUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let phuLucService: PhuLucService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PhuLucUpdateComponent],
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
      .overrideTemplate(PhuLucUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PhuLucUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    phuLucService = TestBed.inject(PhuLucService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const phuLuc: IPhuLuc = { id: 456 };

      activatedRoute.data = of({ phuLuc });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(phuLuc));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PhuLuc>>();
      const phuLuc = { id: 123 };
      jest.spyOn(phuLucService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ phuLuc });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: phuLuc }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(phuLucService.update).toHaveBeenCalledWith(phuLuc);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PhuLuc>>();
      const phuLuc = new PhuLuc();
      jest.spyOn(phuLucService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ phuLuc });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: phuLuc }));
      saveSubject.complete();

      // THEN
      expect(phuLucService.create).toHaveBeenCalledWith(phuLuc);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PhuLuc>>();
      const phuLuc = { id: 123 };
      jest.spyOn(phuLucService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ phuLuc });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(phuLucService.update).toHaveBeenCalledWith(phuLuc);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
