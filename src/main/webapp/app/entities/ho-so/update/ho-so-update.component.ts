import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IHoSo, HoSo } from '../ho-so.model';
import { HoSoService } from '../service/ho-so.service';
import { IQuanLySuCoYKhoa } from 'app/entities/quan-ly-su-co-y-khoa/quan-ly-su-co-y-khoa.model';
import { QuanLySuCoYKhoaService } from 'app/entities/quan-ly-su-co-y-khoa/service/quan-ly-su-co-y-khoa.service';
import { IPhuLuc } from 'app/entities/phu-luc/phu-luc.model';
import { PhuLucService } from 'app/entities/phu-luc/service/phu-luc.service';

@Component({
  selector: 'jhi-ho-so-update',
  templateUrl: './ho-so-update.component.html',
})
export class HoSoUpdateComponent implements OnInit {
  isSaving = false;

  quanLySuCoYKhoasSharedCollection: IQuanLySuCoYKhoa[] = [];
  phuLucsSharedCollection: IPhuLuc[] = [];

  editForm = this.fb.group({
    id: [],
    soThuTu: [],
    tenHoSo: [],
    noiLuu: [],
    thoiGianLuu: [],
    quanLySuCoYKhoa: [],
    phuLuc: [],
  });

  constructor(
    protected hoSoService: HoSoService,
    protected quanLySuCoYKhoaService: QuanLySuCoYKhoaService,
    protected phuLucService: PhuLucService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hoSo }) => {
      if (hoSo.id === undefined) {
        const today = dayjs().startOf('day');
        hoSo.thoiGianLuu = today;
      }

      this.updateForm(hoSo);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hoSo = this.createFromForm();
    if (hoSo.id !== undefined) {
      this.subscribeToSaveResponse(this.hoSoService.update(hoSo));
    } else {
      this.subscribeToSaveResponse(this.hoSoService.create(hoSo));
    }
  }

  trackQuanLySuCoYKhoaById(_index: number, item: IQuanLySuCoYKhoa): number {
    return item.id!;
  }

  trackPhuLucById(_index: number, item: IPhuLuc): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHoSo>>): void {
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

  protected updateForm(hoSo: IHoSo): void {
    this.editForm.patchValue({
      id: hoSo.id,
      soThuTu: hoSo.soThuTu,
      tenHoSo: hoSo.tenHoSo,
      noiLuu: hoSo.noiLuu,
      thoiGianLuu: hoSo.thoiGianLuu ? hoSo.thoiGianLuu.format(DATE_TIME_FORMAT) : null,
      quanLySuCoYKhoa: hoSo.quanLySuCoYKhoa,
      phuLuc: hoSo.phuLuc,
    });

    this.quanLySuCoYKhoasSharedCollection = this.quanLySuCoYKhoaService.addQuanLySuCoYKhoaToCollectionIfMissing(
      this.quanLySuCoYKhoasSharedCollection,
      hoSo.quanLySuCoYKhoa
    );
    this.phuLucsSharedCollection = this.phuLucService.addPhuLucToCollectionIfMissing(this.phuLucsSharedCollection, hoSo.phuLuc);
  }

  protected loadRelationshipsOptions(): void {
    this.quanLySuCoYKhoaService
      .query()
      .pipe(map((res: HttpResponse<IQuanLySuCoYKhoa[]>) => res.body ?? []))
      .pipe(
        map((quanLySuCoYKhoas: IQuanLySuCoYKhoa[]) =>
          this.quanLySuCoYKhoaService.addQuanLySuCoYKhoaToCollectionIfMissing(quanLySuCoYKhoas, this.editForm.get('quanLySuCoYKhoa')!.value)
        )
      )
      .subscribe((quanLySuCoYKhoas: IQuanLySuCoYKhoa[]) => (this.quanLySuCoYKhoasSharedCollection = quanLySuCoYKhoas));

    this.phuLucService
      .query()
      .pipe(map((res: HttpResponse<IPhuLuc[]>) => res.body ?? []))
      .pipe(map((phuLucs: IPhuLuc[]) => this.phuLucService.addPhuLucToCollectionIfMissing(phuLucs, this.editForm.get('phuLuc')!.value)))
      .subscribe((phuLucs: IPhuLuc[]) => (this.phuLucsSharedCollection = phuLucs));
  }

  protected createFromForm(): IHoSo {
    return {
      ...new HoSo(),
      id: this.editForm.get(['id'])!.value,
      soThuTu: this.editForm.get(['soThuTu'])!.value,
      tenHoSo: this.editForm.get(['tenHoSo'])!.value,
      noiLuu: this.editForm.get(['noiLuu'])!.value,
      thoiGianLuu: this.editForm.get(['thoiGianLuu'])!.value
        ? dayjs(this.editForm.get(['thoiGianLuu'])!.value, DATE_TIME_FORMAT)
        : undefined,
      quanLySuCoYKhoa: this.editForm.get(['quanLySuCoYKhoa'])!.value,
      phuLuc: this.editForm.get(['phuLuc'])!.value,
    };
  }
}
