import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IThongTinSuCo, ThongTinSuCo } from '../thong-tin-su-co.model';
import { ThongTinSuCoService } from '../service/thong-tin-su-co.service';

@Component({
  selector: 'jhi-thong-tin-su-co-update',
  templateUrl: './thong-tin-su-co-update.component.html',
})
export class ThongTinSuCoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    viTriXayRaSuCo: [],
    ngayXayRaSuCo: [],
    thoiGian: [],
  });

  constructor(protected thongTinSuCoService: ThongTinSuCoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thongTinSuCo }) => {
      if (thongTinSuCo.id === undefined) {
        const today = dayjs().startOf('day');
        thongTinSuCo.ngayXayRaSuCo = today;
        thongTinSuCo.thoiGian = today;
      }

      this.updateForm(thongTinSuCo);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const thongTinSuCo = this.createFromForm();
    if (thongTinSuCo.id !== undefined) {
      this.subscribeToSaveResponse(this.thongTinSuCoService.update(thongTinSuCo));
    } else {
      this.subscribeToSaveResponse(this.thongTinSuCoService.create(thongTinSuCo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IThongTinSuCo>>): void {
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

  protected updateForm(thongTinSuCo: IThongTinSuCo): void {
    this.editForm.patchValue({
      id: thongTinSuCo.id,
      viTriXayRaSuCo: thongTinSuCo.viTriXayRaSuCo,
      ngayXayRaSuCo: thongTinSuCo.ngayXayRaSuCo ? thongTinSuCo.ngayXayRaSuCo.format(DATE_TIME_FORMAT) : null,
      thoiGian: thongTinSuCo.thoiGian ? thongTinSuCo.thoiGian.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IThongTinSuCo {
    return {
      ...new ThongTinSuCo(),
      id: this.editForm.get(['id'])!.value,
      viTriXayRaSuCo: this.editForm.get(['viTriXayRaSuCo'])!.value,
      ngayXayRaSuCo: this.editForm.get(['ngayXayRaSuCo'])!.value
        ? dayjs(this.editForm.get(['ngayXayRaSuCo'])!.value, DATE_TIME_FORMAT)
        : undefined,
      thoiGian: this.editForm.get(['thoiGian'])!.value ? dayjs(this.editForm.get(['thoiGian'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
