import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuanLySuCoYKhoa } from '../quan-ly-su-co-y-khoa.model';
import { QuanLySuCoYKhoaService } from '../service/quan-ly-su-co-y-khoa.service';

@Component({
  templateUrl: './quan-ly-su-co-y-khoa-delete-dialog.component.html',
})
export class QuanLySuCoYKhoaDeleteDialogComponent {
  quanLySuCoYKhoa?: IQuanLySuCoYKhoa;

  constructor(protected quanLySuCoYKhoaService: QuanLySuCoYKhoaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.quanLySuCoYKhoaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
