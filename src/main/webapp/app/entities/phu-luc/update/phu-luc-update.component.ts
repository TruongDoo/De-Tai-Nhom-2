import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPhuLuc, PhuLuc } from '../phu-luc.model';
import { PhuLucService } from '../service/phu-luc.service';

@Component({
  selector: 'jhi-phu-luc-update',
  templateUrl: './phu-luc-update.component.html',
})
export class PhuLucUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    soThuTu: [],
    tenBieuMau: [],
    maHieu: [null, [Validators.required]],
  });

  constructor(protected phuLucService: PhuLucService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phuLuc }) => {
      this.updateForm(phuLuc);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phuLuc = this.createFromForm();
    if (phuLuc.id !== undefined) {
      this.subscribeToSaveResponse(this.phuLucService.update(phuLuc));
    } else {
      this.subscribeToSaveResponse(this.phuLucService.create(phuLuc));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhuLuc>>): void {
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

  protected updateForm(phuLuc: IPhuLuc): void {
    this.editForm.patchValue({
      id: phuLuc.id,
      soThuTu: phuLuc.soThuTu,
      tenBieuMau: phuLuc.tenBieuMau,
      maHieu: phuLuc.maHieu,
    });
  }

  protected createFromForm(): IPhuLuc {
    return {
      ...new PhuLuc(),
      id: this.editForm.get(['id'])!.value,
      soThuTu: this.editForm.get(['soThuTu'])!.value,
      tenBieuMau: this.editForm.get(['tenBieuMau'])!.value,
      maHieu: this.editForm.get(['maHieu'])!.value,
    };
  }
}
