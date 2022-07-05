import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPhuLuc, PhuLuc } from '../phu-luc.model';
import { PhuLucService } from '../service/phu-luc.service';

@Injectable({ providedIn: 'root' })
export class PhuLucRoutingResolveService implements Resolve<IPhuLuc> {
  constructor(protected service: PhuLucService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhuLuc> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((phuLuc: HttpResponse<PhuLuc>) => {
          if (phuLuc.body) {
            return of(phuLuc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PhuLuc());
  }
}
