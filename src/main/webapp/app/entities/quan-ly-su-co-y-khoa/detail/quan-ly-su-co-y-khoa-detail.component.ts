import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuanLySuCoYKhoa } from '../quan-ly-su-co-y-khoa.model';

@Component({
  selector: 'jhi-quan-ly-su-co-y-khoa-detail',
  templateUrl: './quan-ly-su-co-y-khoa-detail.component.html',
})
export class QuanLySuCoYKhoaDetailComponent implements OnInit {
  quanLySuCoYKhoa: IQuanLySuCoYKhoa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quanLySuCoYKhoa }) => {
      this.quanLySuCoYKhoa = quanLySuCoYKhoa;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
