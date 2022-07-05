import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IQuanLySuCoYKhoa, QuanLySuCoYKhoa } from '../quan-ly-su-co-y-khoa.model';
import { QuanLySuCoYKhoaService } from '../service/quan-ly-su-co-y-khoa.service';
import { IThongTinSuCo } from 'app/entities/thong-tin-su-co/thong-tin-su-co.model';
import { ThongTinSuCoService } from 'app/entities/thong-tin-su-co/service/thong-tin-su-co.service';
import { IThongTinNguoiBenh } from 'app/entities/thong-tin-nguoi-benh/thong-tin-nguoi-benh.model';
import { ThongTinNguoiBenhService } from 'app/entities/thong-tin-nguoi-benh/service/thong-tin-nguoi-benh.service';
import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';
import { NhanVienService } from 'app/entities/nhan-vien/service/nhan-vien.service';

@Component({
  selector: 'jhi-quan-ly-su-co-y-khoa-update',
  templateUrl: './quan-ly-su-co-y-khoa-update.component.html',
})
export class QuanLySuCoYKhoaUpdateComponent implements OnInit {
  isSaving = false;

  thongTinSuCosSharedCollection: IThongTinSuCo[] = [];
  thongTinNguoiBenhsSharedCollection: IThongTinNguoiBenh[] = [];
  nhanViensSharedCollection: INhanVien[] = [];

  editForm = this.fb.group({
    id: [],
    maSoSuCo: [null, [Validators.required]],
    ngayBaoCao: [],
    donViBaoCao: [],
    soThuTu: [],
    tenSuCo: [],
    nhomSuCo: [],
    mucDoSuCo: [],
    thongTinSuCo: [],
    thongTinNguoiBenh: [],
    quanLySuCoYKhoa: [],
  });

  constructor(
    protected quanLySuCoYKhoaService: QuanLySuCoYKhoaService,
    protected thongTinSuCoService: ThongTinSuCoService,
    protected thongTinNguoiBenhService: ThongTinNguoiBenhService,
    protected nhanVienService: NhanVienService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quanLySuCoYKhoa }) => {
      if (quanLySuCoYKhoa.id === undefined) {
        const today = dayjs().startOf('day');
        quanLySuCoYKhoa.ngayBaoCao = today;
      }

      this.updateForm(quanLySuCoYKhoa);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const quanLySuCoYKhoa = this.createFromForm();
    if (quanLySuCoYKhoa.id !== undefined) {
      this.subscribeToSaveResponse(this.quanLySuCoYKhoaService.update(quanLySuCoYKhoa));
    } else {
      this.subscribeToSaveResponse(this.quanLySuCoYKhoaService.create(quanLySuCoYKhoa));
    }
  }

  trackThongTinSuCoById(_index: number, item: IThongTinSuCo): number {
    return item.id!;
  }

  trackThongTinNguoiBenhById(_index: number, item: IThongTinNguoiBenh): number {
    return item.id!;
  }

  trackNhanVienById(_index: number, item: INhanVien): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuanLySuCoYKhoa>>): void {
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

  protected updateForm(quanLySuCoYKhoa: IQuanLySuCoYKhoa): void {
    this.editForm.patchValue({
      id: quanLySuCoYKhoa.id,
      maSoSuCo: quanLySuCoYKhoa.maSoSuCo,
      ngayBaoCao: quanLySuCoYKhoa.ngayBaoCao ? quanLySuCoYKhoa.ngayBaoCao.format(DATE_TIME_FORMAT) : null,
      donViBaoCao: quanLySuCoYKhoa.donViBaoCao,
      soThuTu: quanLySuCoYKhoa.soThuTu,
      tenSuCo: quanLySuCoYKhoa.tenSuCo,
      nhomSuCo: quanLySuCoYKhoa.nhomSuCo,
      mucDoSuCo: quanLySuCoYKhoa.mucDoSuCo,
      thongTinSuCo: quanLySuCoYKhoa.thongTinSuCo,
      thongTinNguoiBenh: quanLySuCoYKhoa.thongTinNguoiBenh,
      quanLySuCoYKhoa: quanLySuCoYKhoa.quanLySuCoYKhoa,
    });

    this.thongTinSuCosSharedCollection = this.thongTinSuCoService.addThongTinSuCoToCollectionIfMissing(
      this.thongTinSuCosSharedCollection,
      quanLySuCoYKhoa.thongTinSuCo
    );
    this.thongTinNguoiBenhsSharedCollection = this.thongTinNguoiBenhService.addThongTinNguoiBenhToCollectionIfMissing(
      this.thongTinNguoiBenhsSharedCollection,
      quanLySuCoYKhoa.thongTinNguoiBenh
    );
    this.nhanViensSharedCollection = this.nhanVienService.addNhanVienToCollectionIfMissing(
      this.nhanViensSharedCollection,
      quanLySuCoYKhoa.quanLySuCoYKhoa
    );
  }

  protected loadRelationshipsOptions(): void {
    this.thongTinSuCoService
      .query()
      .pipe(map((res: HttpResponse<IThongTinSuCo[]>) => res.body ?? []))
      .pipe(
        map((thongTinSuCos: IThongTinSuCo[]) =>
          this.thongTinSuCoService.addThongTinSuCoToCollectionIfMissing(thongTinSuCos, this.editForm.get('thongTinSuCo')!.value)
        )
      )
      .subscribe((thongTinSuCos: IThongTinSuCo[]) => (this.thongTinSuCosSharedCollection = thongTinSuCos));

    this.thongTinNguoiBenhService
      .query()
      .pipe(map((res: HttpResponse<IThongTinNguoiBenh[]>) => res.body ?? []))
      .pipe(
        map((thongTinNguoiBenhs: IThongTinNguoiBenh[]) =>
          this.thongTinNguoiBenhService.addThongTinNguoiBenhToCollectionIfMissing(
            thongTinNguoiBenhs,
            this.editForm.get('thongTinNguoiBenh')!.value
          )
        )
      )
      .subscribe((thongTinNguoiBenhs: IThongTinNguoiBenh[]) => (this.thongTinNguoiBenhsSharedCollection = thongTinNguoiBenhs));

    this.nhanVienService
      .query()
      .pipe(map((res: HttpResponse<INhanVien[]>) => res.body ?? []))
      .pipe(
        map((nhanViens: INhanVien[]) =>
          this.nhanVienService.addNhanVienToCollectionIfMissing(nhanViens, this.editForm.get('quanLySuCoYKhoa')!.value)
        )
      )
      .subscribe((nhanViens: INhanVien[]) => (this.nhanViensSharedCollection = nhanViens));
  }

  protected createFromForm(): IQuanLySuCoYKhoa {
    return {
      ...new QuanLySuCoYKhoa(),
      id: this.editForm.get(['id'])!.value,
      maSoSuCo: this.editForm.get(['maSoSuCo'])!.value,
      ngayBaoCao: this.editForm.get(['ngayBaoCao'])!.value ? dayjs(this.editForm.get(['ngayBaoCao'])!.value, DATE_TIME_FORMAT) : undefined,
      donViBaoCao: this.editForm.get(['donViBaoCao'])!.value,
      soThuTu: this.editForm.get(['soThuTu'])!.value,
      tenSuCo: this.editForm.get(['tenSuCo'])!.value,
      nhomSuCo: this.editForm.get(['nhomSuCo'])!.value,
      mucDoSuCo: this.editForm.get(['mucDoSuCo'])!.value,
      thongTinSuCo: this.editForm.get(['thongTinSuCo'])!.value,
      thongTinNguoiBenh: this.editForm.get(['thongTinNguoiBenh'])!.value,
      quanLySuCoYKhoa: this.editForm.get(['quanLySuCoYKhoa'])!.value,
    };
  }
}
