import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HoSoComponent } from '../list/ho-so.component';
import { HoSoDetailComponent } from '../detail/ho-so-detail.component';
import { HoSoUpdateComponent } from '../update/ho-so-update.component';
import { HoSoRoutingResolveService } from './ho-so-routing-resolve.service';

const hoSoRoute: Routes = [
  {
    path: '',
    component: HoSoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HoSoDetailComponent,
    resolve: {
      hoSo: HoSoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HoSoUpdateComponent,
    resolve: {
      hoSo: HoSoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HoSoUpdateComponent,
    resolve: {
      hoSo: HoSoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hoSoRoute)],
  exports: [RouterModule],
})
export class HoSoRoutingModule {}
