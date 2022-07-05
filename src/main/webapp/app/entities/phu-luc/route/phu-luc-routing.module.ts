import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PhuLucComponent } from '../list/phu-luc.component';
import { PhuLucDetailComponent } from '../detail/phu-luc-detail.component';
import { PhuLucUpdateComponent } from '../update/phu-luc-update.component';
import { PhuLucRoutingResolveService } from './phu-luc-routing-resolve.service';

const phuLucRoute: Routes = [
  {
    path: '',
    component: PhuLucComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PhuLucDetailComponent,
    resolve: {
      phuLuc: PhuLucRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhuLucUpdateComponent,
    resolve: {
      phuLuc: PhuLucRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PhuLucUpdateComponent,
    resolve: {
      phuLuc: PhuLucRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(phuLucRoute)],
  exports: [RouterModule],
})
export class PhuLucRoutingModule {}
