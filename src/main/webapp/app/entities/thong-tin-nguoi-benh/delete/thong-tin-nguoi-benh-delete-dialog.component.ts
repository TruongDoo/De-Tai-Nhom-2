import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IThongTinNguoiBenh } from '../thong-tin-nguoi-benh.model';
import { ThongTinNguoiBenhService } from '../service/thong-tin-nguoi-benh.service';

@Component({
  templateUrl: './thong-tin-nguoi-benh-delete-dialog.component.html',
})
export class ThongTinNguoiBenhDeleteDialogComponent {
  thongTinNguoiBenh?: IThongTinNguoiBenh;

  constructor(protected thongTinNguoiBenhService: ThongTinNguoiBenhService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.thongTinNguoiBenhService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
