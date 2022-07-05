import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IThongTinNguoiBenh } from '../thong-tin-nguoi-benh.model';

@Component({
  selector: 'jhi-thong-tin-nguoi-benh-detail',
  templateUrl: './thong-tin-nguoi-benh-detail.component.html',
})
export class ThongTinNguoiBenhDetailComponent implements OnInit {
  thongTinNguoiBenh: IThongTinNguoiBenh | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thongTinNguoiBenh }) => {
      this.thongTinNguoiBenh = thongTinNguoiBenh;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
