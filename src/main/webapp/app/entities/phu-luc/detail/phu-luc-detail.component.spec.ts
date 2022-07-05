import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PhuLucDetailComponent } from './phu-luc-detail.component';

describe('PhuLuc Management Detail Component', () => {
  let comp: PhuLucDetailComponent;
  let fixture: ComponentFixture<PhuLucDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PhuLucDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ phuLuc: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PhuLucDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PhuLucDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load phuLuc on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.phuLuc).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
