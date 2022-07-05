import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IThongTinSuCo, getThongTinSuCoIdentifier } from '../thong-tin-su-co.model';

export type EntityResponseType = HttpResponse<IThongTinSuCo>;
export type EntityArrayResponseType = HttpResponse<IThongTinSuCo[]>;

@Injectable({ providedIn: 'root' })
export class ThongTinSuCoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/thong-tin-su-cos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(thongTinSuCo: IThongTinSuCo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(thongTinSuCo);
    return this.http
      .post<IThongTinSuCo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(thongTinSuCo: IThongTinSuCo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(thongTinSuCo);
    return this.http
      .put<IThongTinSuCo>(`${this.resourceUrl}/${getThongTinSuCoIdentifier(thongTinSuCo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(thongTinSuCo: IThongTinSuCo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(thongTinSuCo);
    return this.http
      .patch<IThongTinSuCo>(`${this.resourceUrl}/${getThongTinSuCoIdentifier(thongTinSuCo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IThongTinSuCo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IThongTinSuCo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addThongTinSuCoToCollectionIfMissing(
    thongTinSuCoCollection: IThongTinSuCo[],
    ...thongTinSuCosToCheck: (IThongTinSuCo | null | undefined)[]
  ): IThongTinSuCo[] {
    const thongTinSuCos: IThongTinSuCo[] = thongTinSuCosToCheck.filter(isPresent);
    if (thongTinSuCos.length > 0) {
      const thongTinSuCoCollectionIdentifiers = thongTinSuCoCollection.map(
        thongTinSuCoItem => getThongTinSuCoIdentifier(thongTinSuCoItem)!
      );
      const thongTinSuCosToAdd = thongTinSuCos.filter(thongTinSuCoItem => {
        const thongTinSuCoIdentifier = getThongTinSuCoIdentifier(thongTinSuCoItem);
        if (thongTinSuCoIdentifier == null || thongTinSuCoCollectionIdentifiers.includes(thongTinSuCoIdentifier)) {
          return false;
        }
        thongTinSuCoCollectionIdentifiers.push(thongTinSuCoIdentifier);
        return true;
      });
      return [...thongTinSuCosToAdd, ...thongTinSuCoCollection];
    }
    return thongTinSuCoCollection;
  }

  protected convertDateFromClient(thongTinSuCo: IThongTinSuCo): IThongTinSuCo {
    return Object.assign({}, thongTinSuCo, {
      ngayXayRaSuCo: thongTinSuCo.ngayXayRaSuCo?.isValid() ? thongTinSuCo.ngayXayRaSuCo.toJSON() : undefined,
      thoiGian: thongTinSuCo.thoiGian?.isValid() ? thongTinSuCo.thoiGian.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ngayXayRaSuCo = res.body.ngayXayRaSuCo ? dayjs(res.body.ngayXayRaSuCo) : undefined;
      res.body.thoiGian = res.body.thoiGian ? dayjs(res.body.thoiGian) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((thongTinSuCo: IThongTinSuCo) => {
        thongTinSuCo.ngayXayRaSuCo = thongTinSuCo.ngayXayRaSuCo ? dayjs(thongTinSuCo.ngayXayRaSuCo) : undefined;
        thongTinSuCo.thoiGian = thongTinSuCo.thoiGian ? dayjs(thongTinSuCo.thoiGian) : undefined;
      });
    }
    return res;
  }
}
