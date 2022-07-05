import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IThongTinSuCo } from '../thong-tin-su-co.model';
import { ThongTinSuCoService } from '../service/thong-tin-su-co.service';

@Component({
  templateUrl: './thong-tin-su-co-delete-dialog.component.html',
})
export class ThongTinSuCoDeleteDialogComponent {
  thongTinSuCo?: IThongTinSuCo;

  constructor(protected thongTinSuCoService: ThongTinSuCoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.thongTinSuCoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
