import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IQuanLySuCoYKhoa, getQuanLySuCoYKhoaIdentifier } from '../quan-ly-su-co-y-khoa.model';

export type EntityResponseType = HttpResponse<IQuanLySuCoYKhoa>;
export type EntityArrayResponseType = HttpResponse<IQuanLySuCoYKhoa[]>;

@Injectable({ providedIn: 'root' })
export class QuanLySuCoYKhoaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/quan-ly-su-co-y-khoas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(quanLySuCoYKhoa: IQuanLySuCoYKhoa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(quanLySuCoYKhoa);
    return this.http
      .post<IQuanLySuCoYKhoa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(quanLySuCoYKhoa: IQuanLySuCoYKhoa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(quanLySuCoYKhoa);
    return this.http
      .put<IQuanLySuCoYKhoa>(`${this.resourceUrl}/${getQuanLySuCoYKhoaIdentifier(quanLySuCoYKhoa) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(quanLySuCoYKhoa: IQuanLySuCoYKhoa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(quanLySuCoYKhoa);
    return this.http
      .patch<IQuanLySuCoYKhoa>(`${this.resourceUrl}/${getQuanLySuCoYKhoaIdentifier(quanLySuCoYKhoa) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IQuanLySuCoYKhoa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IQuanLySuCoYKhoa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addQuanLySuCoYKhoaToCollectionIfMissing(
    quanLySuCoYKhoaCollection: IQuanLySuCoYKhoa[],
    ...quanLySuCoYKhoasToCheck: (IQuanLySuCoYKhoa | null | undefined)[]
  ): IQuanLySuCoYKhoa[] {
    const quanLySuCoYKhoas: IQuanLySuCoYKhoa[] = quanLySuCoYKhoasToCheck.filter(isPresent);
    if (quanLySuCoYKhoas.length > 0) {
      const quanLySuCoYKhoaCollectionIdentifiers = quanLySuCoYKhoaCollection.map(
        quanLySuCoYKhoaItem => getQuanLySuCoYKhoaIdentifier(quanLySuCoYKhoaItem)!
      );
      const quanLySuCoYKhoasToAdd = quanLySuCoYKhoas.filter(quanLySuCoYKhoaItem => {
        const quanLySuCoYKhoaIdentifier = getQuanLySuCoYKhoaIdentifier(quanLySuCoYKhoaItem);
        if (quanLySuCoYKhoaIdentifier == null || quanLySuCoYKhoaCollectionIdentifiers.includes(quanLySuCoYKhoaIdentifier)) {
          return false;
        }
        quanLySuCoYKhoaCollectionIdentifiers.push(quanLySuCoYKhoaIdentifier);
        return true;
      });
      return [...quanLySuCoYKhoasToAdd, ...quanLySuCoYKhoaCollection];
    }
    return quanLySuCoYKhoaCollection;
  }

  protected convertDateFromClient(quanLySuCoYKhoa: IQuanLySuCoYKhoa): IQuanLySuCoYKhoa {
    return Object.assign({}, quanLySuCoYKhoa, {
      ngayBaoCao: quanLySuCoYKhoa.ngayBaoCao?.isValid() ? quanLySuCoYKhoa.ngayBaoCao.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ngayBaoCao = res.body.ngayBaoCao ? dayjs(res.body.ngayBaoCao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((quanLySuCoYKhoa: IQuanLySuCoYKhoa) => {
        quanLySuCoYKhoa.ngayBaoCao = quanLySuCoYKhoa.ngayBaoCao ? dayjs(quanLySuCoYKhoa.ngayBaoCao) : undefined;
      });
    }
    return res;
  }
}
