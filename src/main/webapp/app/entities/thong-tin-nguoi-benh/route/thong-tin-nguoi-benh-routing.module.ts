import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ThongTinNguoiBenhComponent } from '../list/thong-tin-nguoi-benh.component';
import { ThongTinNguoiBenhDetailComponent } from '../detail/thong-tin-nguoi-benh-detail.component';
import { ThongTinNguoiBenhUpdateComponent } from '../update/thong-tin-nguoi-benh-update.component';
import { ThongTinNguoiBenhRoutingResolveService } from './thong-tin-nguoi-benh-routing-resolve.service';

const thongTinNguoiBenhRoute: Routes = [
  {
    path: '',
    component: ThongTinNguoiBenhComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ThongTinNguoiBenhDetailComponent,
    resolve: {
      thongTinNguoiBenh: ThongTinNguoiBenhRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ThongTinNguoiBenhUpdateComponent,
    resolve: {
      thongTinNguoiBenh: ThongTinNguoiBenhRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ThongTinNguoiBenhUpdateComponent,
    resolve: {
      thongTinNguoiBenh: ThongTinNguoiBenhRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(thongTinNguoiBenhRoute)],
  exports: [RouterModule],
})
export class ThongTinNguoiBenhRoutingModule {}
