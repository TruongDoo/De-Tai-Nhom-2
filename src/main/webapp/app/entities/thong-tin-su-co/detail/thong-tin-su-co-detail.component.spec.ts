import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ThongTinSuCoDetailComponent } from './thong-tin-su-co-detail.component';

describe('ThongTinSuCo Management Detail Component', () => {
  let comp: ThongTinSuCoDetailComponent;
  let fixture: ComponentFixture<ThongTinSuCoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ThongTinSuCoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ thongTinSuCo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ThongTinSuCoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ThongTinSuCoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load thongTinSuCo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.thongTinSuCo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
