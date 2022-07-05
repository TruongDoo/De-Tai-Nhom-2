import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ThongTinSuCoService } from '../service/thong-tin-su-co.service';
import { IThongTinSuCo, ThongTinSuCo } from '../thong-tin-su-co.model';

import { ThongTinSuCoUpdateComponent } from './thong-tin-su-co-update.component';

describe('ThongTinSuCo Management Update Component', () => {
  let comp: ThongTinSuCoUpdateComponent;
  let fixture: ComponentFixture<ThongTinSuCoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let thongTinSuCoService: ThongTinSuCoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ThongTinSuCoUpdateComponent],
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
      .overrideTemplate(ThongTinSuCoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ThongTinSuCoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    thongTinSuCoService = TestBed.inject(ThongTinSuCoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const thongTinSuCo: IThongTinSuCo = { id: 456 };

      activatedRoute.data = of({ thongTinSuCo });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(thongTinSuCo));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ThongTinSuCo>>();
      const thongTinSuCo = { id: 123 };
      jest.spyOn(thongTinSuCoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thongTinSuCo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: thongTinSuCo }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(thongTinSuCoService.update).toHaveBeenCalledWith(thongTinSuCo);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ThongTinSuCo>>();
      const thongTinSuCo = new ThongTinSuCo();
      jest.spyOn(thongTinSuCoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thongTinSuCo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: thongTinSuCo }));
      saveSubject.complete();

      // THEN
      expect(thongTinSuCoService.create).toHaveBeenCalledWith(thongTinSuCo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ThongTinSuCo>>();
      const thongTinSuCo = { id: 123 };
      jest.spyOn(thongTinSuCoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thongTinSuCo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(thongTinSuCoService.update).toHaveBeenCalledWith(thongTinSuCo);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
