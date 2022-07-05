import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PhuLucComponent } from './list/phu-luc.component';
import { PhuLucDetailComponent } from './detail/phu-luc-detail.component';
import { PhuLucUpdateComponent } from './update/phu-luc-update.component';
import { PhuLucDeleteDialogComponent } from './delete/phu-luc-delete-dialog.component';
import { PhuLucRoutingModule } from './route/phu-luc-routing.module';

@NgModule({
  imports: [SharedModule, PhuLucRoutingModule],
  declarations: [PhuLucComponent, PhuLucDetailComponent, PhuLucUpdateComponent, PhuLucDeleteDialogComponent],
  entryComponents: [PhuLucDeleteDialogComponent],
})
export class PhuLucModule {}
