import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INhanVien, NhanVien } from '../nhan-vien.model';
import { NhanVienService } from '../service/nhan-vien.service';

@Component({
  selector: 'jhi-nhan-vien-update',
  templateUrl: './nhan-vien-update.component.html',
})
export class NhanVienUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    chucVu: [],
    hoTen: [],
    email: [],
    diaChi: [],
    soDienThoai: [],
  });

  constructor(protected nhanVienService: NhanVienService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nhanVien }) => {
      this.updateForm(nhanVien);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nhanVien = this.createFromForm();
    if (nhanVien.id !== undefined) {
      this.subscribeToSaveResponse(this.nhanVienService.update(nhanVien));
    } else {
      this.subscribeToSaveResponse(this.nhanVienService.create(nhanVien));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INhanVien>>): void {
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

  protected updateForm(nhanVien: INhanVien): void {
    this.editForm.patchValue({
      id: nhanVien.id,
      chucVu: nhanVien.chucVu,
      hoTen: nhanVien.hoTen,
      email: nhanVien.email,
      diaChi: nhanVien.diaChi,
      soDienThoai: nhanVien.soDienThoai,
    });
  }

  protected createFromForm(): INhanVien {
    return {
      ...new NhanVien(),
      id: this.editForm.get(['id'])!.value,
      chucVu: this.editForm.get(['chucVu'])!.value,
      hoTen: this.editForm.get(['hoTen'])!.value,
      email: this.editForm.get(['email'])!.value,
      diaChi: this.editForm.get(['diaChi'])!.value,
      soDienThoai: this.editForm.get(['soDienThoai'])!.value,
    };
  }
}
