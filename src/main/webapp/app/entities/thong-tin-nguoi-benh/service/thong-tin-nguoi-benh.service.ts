import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IThongTinNguoiBenh, getThongTinNguoiBenhIdentifier } from '../thong-tin-nguoi-benh.model';

export type EntityResponseType = HttpResponse<IThongTinNguoiBenh>;
export type EntityArrayResponseType = HttpResponse<IThongTinNguoiBenh[]>;

@Injectable({ providedIn: 'root' })
export class ThongTinNguoiBenhService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/thong-tin-nguoi-benhs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(thongTinNguoiBenh: IThongTinNguoiBenh): Observable<EntityResponseType> {
    return this.http.post<IThongTinNguoiBenh>(this.resourceUrl, thongTinNguoiBenh, { observe: 'response' });
  }

  update(thongTinNguoiBenh: IThongTinNguoiBenh): Observable<EntityResponseType> {
    return this.http.put<IThongTinNguoiBenh>(
      `${this.resourceUrl}/${getThongTinNguoiBenhIdentifier(thongTinNguoiBenh) as number}`,
      thongTinNguoiBenh,
      { observe: 'response' }
    );
  }

  partialUpdate(thongTinNguoiBenh: IThongTinNguoiBenh): Observable<EntityResponseType> {
    return this.http.patch<IThongTinNguoiBenh>(
      `${this.resourceUrl}/${getThongTinNguoiBenhIdentifier(thongTinNguoiBenh) as number}`,
      thongTinNguoiBenh,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IThongTinNguoiBenh>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IThongTinNguoiBenh[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addThongTinNguoiBenhToCollectionIfMissing(
    thongTinNguoiBenhCollection: IThongTinNguoiBenh[],
    ...thongTinNguoiBenhsToCheck: (IThongTinNguoiBenh | null | undefined)[]
  ): IThongTinNguoiBenh[] {
    const thongTinNguoiBenhs: IThongTinNguoiBenh[] = thongTinNguoiBenhsToCheck.filter(isPresent);
    if (thongTinNguoiBenhs.length > 0) {
      const thongTinNguoiBenhCollectionIdentifiers = thongTinNguoiBenhCollection.map(
        thongTinNguoiBenhItem => getThongTinNguoiBenhIdentifier(thongTinNguoiBenhItem)!
      );
      const thongTinNguoiBenhsToAdd = thongTinNguoiBenhs.filter(thongTinNguoiBenhItem => {
        const thongTinNguoiBenhIdentifier = getThongTinNguoiBenhIdentifier(thongTinNguoiBenhItem);
        if (thongTinNguoiBenhIdentifier == null || thongTinNguoiBenhCollectionIdentifiers.includes(thongTinNguoiBenhIdentifier)) {
          return false;
        }
        thongTinNguoiBenhCollectionIdentifiers.push(thongTinNguoiBenhIdentifier);
        return true;
      });
      return [...thongTinNguoiBenhsToAdd, ...thongTinNguoiBenhCollection];
    }
    return thongTinNguoiBenhCollection;
  }
}
