import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IThongTinNguoiBenh, ThongTinNguoiBenh } from '../thong-tin-nguoi-benh.model';
import { ThongTinNguoiBenhService } from '../service/thong-tin-nguoi-benh.service';

@Injectable({ providedIn: 'root' })
export class ThongTinNguoiBenhRoutingResolveService implements Resolve<IThongTinNguoiBenh> {
  constructor(protected service: ThongTinNguoiBenhService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IThongTinNguoiBenh> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((thongTinNguoiBenh: HttpResponse<ThongTinNguoiBenh>) => {
          if (thongTinNguoiBenh.body) {
            return of(thongTinNguoiBenh.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ThongTinNguoiBenh());
  }
}
