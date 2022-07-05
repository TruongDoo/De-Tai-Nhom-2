jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PhuLucService } from '../service/phu-luc.service';

import { PhuLucDeleteDialogComponent } from './phu-luc-delete-dialog.component';

describe('PhuLuc Management Delete Component', () => {
  let comp: PhuLucDeleteDialogComponent;
  let fixture: ComponentFixture<PhuLucDeleteDialogComponent>;
  let service: PhuLucService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PhuLucDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(PhuLucDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PhuLucDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PhuLucService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
