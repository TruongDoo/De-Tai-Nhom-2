import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhuLuc } from '../phu-luc.model';
import { PhuLucService } from '../service/phu-luc.service';

@Component({
  templateUrl: './phu-luc-delete-dialog.component.html',
})
export class PhuLucDeleteDialogComponent {
  phuLuc?: IPhuLuc;

  constructor(protected phuLucService: PhuLucService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.phuLucService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
