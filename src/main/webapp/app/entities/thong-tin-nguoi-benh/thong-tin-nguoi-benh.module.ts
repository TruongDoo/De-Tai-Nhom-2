import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ThongTinNguoiBenhComponent } from './list/thong-tin-nguoi-benh.component';
import { ThongTinNguoiBenhDetailComponent } from './detail/thong-tin-nguoi-benh-detail.component';
import { ThongTinNguoiBenhUpdateComponent } from './update/thong-tin-nguoi-benh-update.component';
import { ThongTinNguoiBenhDeleteDialogComponent } from './delete/thong-tin-nguoi-benh-delete-dialog.component';
import { ThongTinNguoiBenhRoutingModule } from './route/thong-tin-nguoi-benh-routing.module';

@NgModule({
  imports: [SharedModule, ThongTinNguoiBenhRoutingModule],
  declarations: [
    ThongTinNguoiBenhComponent,
    ThongTinNguoiBenhDetailComponent,
    ThongTinNguoiBenhUpdateComponent,
    ThongTinNguoiBenhDeleteDialogComponent,
  ],
  entryComponents: [ThongTinNguoiBenhDeleteDialogComponent],
})
export class ThongTinNguoiBenhModule {}
