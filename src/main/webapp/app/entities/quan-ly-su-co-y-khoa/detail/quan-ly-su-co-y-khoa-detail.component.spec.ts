import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QuanLySuCoYKhoaDetailComponent } from './quan-ly-su-co-y-khoa-detail.component';

describe('QuanLySuCoYKhoa Management Detail Component', () => {
  let comp: QuanLySuCoYKhoaDetailComponent;
  let fixture: ComponentFixture<QuanLySuCoYKhoaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuanLySuCoYKhoaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ quanLySuCoYKhoa: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(QuanLySuCoYKhoaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(QuanLySuCoYKhoaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load quanLySuCoYKhoa on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.quanLySuCoYKhoa).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
