import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPhuLuc, getPhuLucIdentifier } from '../phu-luc.model';

export type EntityResponseType = HttpResponse<IPhuLuc>;
export type EntityArrayResponseType = HttpResponse<IPhuLuc[]>;

@Injectable({ providedIn: 'root' })
export class PhuLucService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/phu-lucs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(phuLuc: IPhuLuc): Observable<EntityResponseType> {
    return this.http.post<IPhuLuc>(this.resourceUrl, phuLuc, { observe: 'response' });
  }

  update(phuLuc: IPhuLuc): Observable<EntityResponseType> {
    return this.http.put<IPhuLuc>(`${this.resourceUrl}/${getPhuLucIdentifier(phuLuc) as number}`, phuLuc, { observe: 'response' });
  }

  partialUpdate(phuLuc: IPhuLuc): Observable<EntityResponseType> {
    return this.http.patch<IPhuLuc>(`${this.resourceUrl}/${getPhuLucIdentifier(phuLuc) as number}`, phuLuc, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhuLuc>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhuLuc[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPhuLucToCollectionIfMissing(phuLucCollection: IPhuLuc[], ...phuLucsToCheck: (IPhuLuc | null | undefined)[]): IPhuLuc[] {
    const phuLucs: IPhuLuc[] = phuLucsToCheck.filter(isPresent);
    if (phuLucs.length > 0) {
      const phuLucCollectionIdentifiers = phuLucCollection.map(phuLucItem => getPhuLucIdentifier(phuLucItem)!);
      const phuLucsToAdd = phuLucs.filter(phuLucItem => {
        const phuLucIdentifier = getPhuLucIdentifier(phuLucItem);
        if (phuLucIdentifier == null || phuLucCollectionIdentifiers.includes(phuLucIdentifier)) {
          return false;
        }
        phuLucCollectionIdentifiers.push(phuLucIdentifier);
        return true;
      });
      return [...phuLucsToAdd, ...phuLucCollection];
    }
    return phuLucCollection;
  }
}
