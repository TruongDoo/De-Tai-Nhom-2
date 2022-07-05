import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ThongTinNguoiBenhDetailComponent } from './thong-tin-nguoi-benh-detail.component';

describe('ThongTinNguoiBenh Management Detail Component', () => {
  let comp: ThongTinNguoiBenhDetailComponent;
  let fixture: ComponentFixture<ThongTinNguoiBenhDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ThongTinNguoiBenhDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ thongTinNguoiBenh: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ThongTinNguoiBenhDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ThongTinNguoiBenhDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load thongTinNguoiBenh on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.thongTinNguoiBenh).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
