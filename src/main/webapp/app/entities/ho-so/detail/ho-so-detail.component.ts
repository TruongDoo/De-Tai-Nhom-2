import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHoSo } from '../ho-so.model';

@Component({
  selector: 'jhi-ho-so-detail',
  templateUrl: './ho-so-detail.component.html',
})
export class HoSoDetailComponent implements OnInit {
  hoSo: IHoSo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hoSo }) => {
      this.hoSo = hoSo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
