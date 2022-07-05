import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQuanLySuCoYKhoa, QuanLySuCoYKhoa } from '../quan-ly-su-co-y-khoa.model';
import { QuanLySuCoYKhoaService } from '../service/quan-ly-su-co-y-khoa.service';

@Injectable({ providedIn: 'root' })
export class QuanLySuCoYKhoaRoutingResolveService implements Resolve<IQuanLySuCoYKhoa> {
  constructor(protected service: QuanLySuCoYKhoaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuanLySuCoYKhoa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((quanLySuCoYKhoa: HttpResponse<QuanLySuCoYKhoa>) => {
          if (quanLySuCoYKhoa.body) {
            return of(quanLySuCoYKhoa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuanLySuCoYKhoa());
  }
}
