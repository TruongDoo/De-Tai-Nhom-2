import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPhuLuc } from '../phu-luc.model';

@Component({
  selector: 'jhi-phu-luc-detail',
  templateUrl: './phu-luc-detail.component.html',
})
export class PhuLucDetailComponent implements OnInit {
  phuLuc: IPhuLuc | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phuLuc }) => {
      this.phuLuc = phuLuc;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
