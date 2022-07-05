import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ThongTinSuCoComponent } from '../list/thong-tin-su-co.component';
import { ThongTinSuCoDetailComponent } from '../detail/thong-tin-su-co-detail.component';
import { ThongTinSuCoUpdateComponent } from '../update/thong-tin-su-co-update.component';
import { ThongTinSuCoRoutingResolveService } from './thong-tin-su-co-routing-resolve.service';

const thongTinSuCoRoute: Routes = [
  {
    path: '',
    component: ThongTinSuCoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ThongTinSuCoDetailComponent,
    resolve: {
      thongTinSuCo: ThongTinSuCoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ThongTinSuCoUpdateComponent,
    resolve: {
      thongTinSuCo: ThongTinSuCoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ThongTinSuCoUpdateComponent,
    resolve: {
      thongTinSuCo: ThongTinSuCoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(thongTinSuCoRoute)],
  exports: [RouterModule],
})
export class ThongTinSuCoRoutingModule {}
