import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HoSoDetailComponent } from './ho-so-detail.component';

describe('HoSo Management Detail Component', () => {
  let comp: HoSoDetailComponent;
  let fixture: ComponentFixture<HoSoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HoSoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ hoSo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HoSoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HoSoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load hoSo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.hoSo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
