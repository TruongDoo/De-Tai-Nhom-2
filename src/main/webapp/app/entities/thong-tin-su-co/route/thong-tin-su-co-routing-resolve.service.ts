import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IThongTinSuCo, ThongTinSuCo } from '../thong-tin-su-co.model';
import { ThongTinSuCoService } from '../service/thong-tin-su-co.service';

@Injectable({ providedIn: 'root' })
export class ThongTinSuCoRoutingResolveService implements Resolve<IThongTinSuCo> {
  constructor(protected service: ThongTinSuCoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IThongTinSuCo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((thongTinSuCo: HttpResponse<ThongTinSuCo>) => {
          if (thongTinSuCo.body) {
            return of(thongTinSuCo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ThongTinSuCo());
  }
}
