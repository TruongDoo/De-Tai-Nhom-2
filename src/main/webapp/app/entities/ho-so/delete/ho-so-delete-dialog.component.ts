import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHoSo } from '../ho-so.model';
import { HoSoService } from '../service/ho-so.service';

@Component({
  templateUrl: './ho-so-delete-dialog.component.html',
})
export class HoSoDeleteDialogComponent {
  hoSo?: IHoSo;

  constructor(protected hoSoService: HoSoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hoSoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
