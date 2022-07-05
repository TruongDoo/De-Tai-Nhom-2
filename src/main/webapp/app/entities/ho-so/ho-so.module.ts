import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HoSoComponent } from './list/ho-so.component';
import { HoSoDetailComponent } from './detail/ho-so-detail.component';
import { HoSoUpdateComponent } from './update/ho-so-update.component';
import { HoSoDeleteDialogComponent } from './delete/ho-so-delete-dialog.component';
import { HoSoRoutingModule } from './route/ho-so-routing.module';

@NgModule({
  imports: [SharedModule, HoSoRoutingModule],
  declarations: [HoSoComponent, HoSoDetailComponent, HoSoUpdateComponent, HoSoDeleteDialogComponent],
  entryComponents: [HoSoDeleteDialogComponent],
})
export class HoSoModule {}
