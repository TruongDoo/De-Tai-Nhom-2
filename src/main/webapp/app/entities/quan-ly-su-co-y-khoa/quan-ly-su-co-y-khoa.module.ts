import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { QuanLySuCoYKhoaComponent } from './list/quan-ly-su-co-y-khoa.component';
import { QuanLySuCoYKhoaDetailComponent } from './detail/quan-ly-su-co-y-khoa-detail.component';
import { QuanLySuCoYKhoaUpdateComponent } from './update/quan-ly-su-co-y-khoa-update.component';
import { QuanLySuCoYKhoaDeleteDialogComponent } from './delete/quan-ly-su-co-y-khoa-delete-dialog.component';
import { QuanLySuCoYKhoaRoutingModule } from './route/quan-ly-su-co-y-khoa-routing.module';

@NgModule({
  imports: [SharedModule, QuanLySuCoYKhoaRoutingModule],
  declarations: [
    QuanLySuCoYKhoaComponent,
    QuanLySuCoYKhoaDetailComponent,
    QuanLySuCoYKhoaUpdateComponent,
    QuanLySuCoYKhoaDeleteDialogComponent,
  ],
  entryComponents: [QuanLySuCoYKhoaDeleteDialogComponent],
})
export class QuanLySuCoYKhoaModule {}
