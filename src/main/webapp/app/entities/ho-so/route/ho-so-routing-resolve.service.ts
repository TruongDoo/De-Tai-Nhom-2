import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHoSo, HoSo } from '../ho-so.model';
import { HoSoService } from '../service/ho-so.service';

@Injectable({ providedIn: 'root' })
export class HoSoRoutingResolveService implements Resolve<IHoSo> {
  constructor(protected service: HoSoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHoSo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hoSo: HttpResponse<HoSo>) => {
          if (hoSo.body) {
            return of(hoSo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HoSo());
  }
}
