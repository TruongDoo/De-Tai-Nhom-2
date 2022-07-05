import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IThongTinNguoiBenh, ThongTinNguoiBenh } from '../thong-tin-nguoi-benh.model';
import { ThongTinNguoiBenhService } from '../service/thong-tin-nguoi-benh.service';

@Component({
  selector: 'jhi-thong-tin-nguoi-benh-update',
  templateUrl: './thong-tin-nguoi-benh-update.component.html',
})
export class ThongTinNguoiBenhUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    hoVaTen: [],
    soBenhAn: [],
    gioiTinh: [],
  });

  constructor(
    protected thongTinNguoiBenhService: ThongTinNguoiBenhService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thongTinNguoiBenh }) => {
      this.updateForm(thongTinNguoiBenh);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const thongTinNguoiBenh = this.createFromForm();
    if (thongTinNguoiBenh.id !== undefined) {
      this.subscribeToSaveResponse(this.thongTinNguoiBenhService.update(thongTinNguoiBenh));
    } else {
      this.subscribeToSaveResponse(this.thongTinNguoiBenhService.create(thongTinNguoiBenh));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IThongTinNguoiBenh>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(thongTinNguoiBenh: IThongTinNguoiBenh): void {
    this.editForm.patchValue({
      id: thongTinNguoiBenh.id,
      hoVaTen: thongTinNguoiBenh.hoVaTen,
      soBenhAn: thongTinNguoiBenh.soBenhAn,
      gioiTinh: thongTinNguoiBenh.gioiTinh,
    });
  }

  protected createFromForm(): IThongTinNguoiBenh {
    return {
      ...new ThongTinNguoiBenh(),
      id: this.editForm.get(['id'])!.value,
      hoVaTen: this.editForm.get(['hoVaTen'])!.value,
      soBenhAn: this.editForm.get(['soBenhAn'])!.value,
      gioiTinh: this.editForm.get(['gioiTinh'])!.value,
    };
  }
}
