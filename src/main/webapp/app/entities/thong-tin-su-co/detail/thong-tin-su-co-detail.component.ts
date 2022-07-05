import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IThongTinSuCo } from '../thong-tin-su-co.model';

@Component({
  selector: 'jhi-thong-tin-su-co-detail',
  templateUrl: './thong-tin-su-co-detail.component.html',
})
export class ThongTinSuCoDetailComponent implements OnInit {
  thongTinSuCo: IThongTinSuCo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thongTinSuCo }) => {
      this.thongTinSuCo = thongTinSuCo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
