import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QuanLySuCoYKhoaComponent } from '../list/quan-ly-su-co-y-khoa.component';
import { QuanLySuCoYKhoaDetailComponent } from '../detail/quan-ly-su-co-y-khoa-detail.component';
import { QuanLySuCoYKhoaUpdateComponent } from '../update/quan-ly-su-co-y-khoa-update.component';
import { QuanLySuCoYKhoaRoutingResolveService } from './quan-ly-su-co-y-khoa-routing-resolve.service';

const quanLySuCoYKhoaRoute: Routes = [
  {
    path: '',
    component: QuanLySuCoYKhoaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuanLySuCoYKhoaDetailComponent,
    resolve: {
      quanLySuCoYKhoa: QuanLySuCoYKhoaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuanLySuCoYKhoaUpdateComponent,
    resolve: {
      quanLySuCoYKhoa: QuanLySuCoYKhoaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuanLySuCoYKhoaUpdateComponent,
    resolve: {
      quanLySuCoYKhoa: QuanLySuCoYKhoaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(quanLySuCoYKhoaRoute)],
  exports: [RouterModule],
})
export class QuanLySuCoYKhoaRoutingModule {}
