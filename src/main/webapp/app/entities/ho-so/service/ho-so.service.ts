import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHoSo, getHoSoIdentifier } from '../ho-so.model';

export type EntityResponseType = HttpResponse<IHoSo>;
export type EntityArrayResponseType = HttpResponse<IHoSo[]>;

@Injectable({ providedIn: 'root' })
export class HoSoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ho-sos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(hoSo: IHoSo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hoSo);
    return this.http
      .post<IHoSo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(hoSo: IHoSo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hoSo);
    return this.http
      .put<IHoSo>(`${this.resourceUrl}/${getHoSoIdentifier(hoSo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(hoSo: IHoSo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hoSo);
    return this.http
      .patch<IHoSo>(`${this.resourceUrl}/${getHoSoIdentifier(hoSo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHoSo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHoSo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHoSoToCollectionIfMissing(hoSoCollection: IHoSo[], ...hoSosToCheck: (IHoSo | null | undefined)[]): IHoSo[] {
    const hoSos: IHoSo[] = hoSosToCheck.filter(isPresent);
    if (hoSos.length > 0) {
      const hoSoCollectionIdentifiers = hoSoCollection.map(hoSoItem => getHoSoIdentifier(hoSoItem)!);
      const hoSosToAdd = hoSos.filter(hoSoItem => {
        const hoSoIdentifier = getHoSoIdentifier(hoSoItem);
        if (hoSoIdentifier == null || hoSoCollectionIdentifiers.includes(hoSoIdentifier)) {
          return false;
        }
        hoSoCollectionIdentifiers.push(hoSoIdentifier);
        return true;
      });
      return [...hoSosToAdd, ...hoSoCollection];
    }
    return hoSoCollection;
  }

  protected convertDateFromClient(hoSo: IHoSo): IHoSo {
    return Object.assign({}, hoSo, {
      thoiGianLuu: hoSo.thoiGianLuu?.isValid() ? hoSo.thoiGianLuu.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.thoiGianLuu = res.body.thoiGianLuu ? dayjs(res.body.thoiGianLuu) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((hoSo: IHoSo) => {
        hoSo.thoiGianLuu = hoSo.thoiGianLuu ? dayjs(hoSo.thoiGianLuu) : undefined;
      });
    }
    return res;
  }
}
