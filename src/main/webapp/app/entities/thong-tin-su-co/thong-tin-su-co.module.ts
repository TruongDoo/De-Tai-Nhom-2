import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ThongTinSuCoComponent } from './list/thong-tin-su-co.component';
import { ThongTinSuCoDetailComponent } from './detail/thong-tin-su-co-detail.component';
import { ThongTinSuCoUpdateComponent } from './update/thong-tin-su-co-update.component';
import { ThongTinSuCoDeleteDialogComponent } from './delete/thong-tin-su-co-delete-dialog.component';
import { ThongTinSuCoRoutingModule } from './route/thong-tin-su-co-routing.module';

@NgModule({
  imports: [SharedModule, ThongTinSuCoRoutingModule],
  declarations: [ThongTinSuCoComponent, ThongTinSuCoDetailComponent, ThongTinSuCoUpdateComponent, ThongTinSuCoDeleteDialogComponent],
  entryComponents: [ThongTinSuCoDeleteDialogComponent],
})
export class ThongTinSuCoModule {}
