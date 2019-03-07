import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAirplaneTripImport } from 'app/shared/model/airplane-trip-import.model';

type EntityResponseType = HttpResponse<IAirplaneTripImport>;
type EntityArrayResponseType = HttpResponse<IAirplaneTripImport[]>;

@Injectable({ providedIn: 'root' })
export class AirplaneTripImportService {
    public resourceUrl = SERVER_API_URL + 'api/airplane-trip-imports';

    constructor(protected http: HttpClient) {}

    create(airplaneTripImport: IAirplaneTripImport, file: File): Observable<EntityResponseType> {
        const formData = new FormData();
        const copy = this.convertDateFromClient(airplaneTripImport);
        formData.append('file', file);
        return this.http
            .post<IAirplaneTripImport>(`${this.resourceUrl}/${copy.airline}`, formData, {
                observe: 'response'
            })
            .pipe(map((res: HttpResponse<IAirplaneTripImport>) => this.convertDateFromServer(res)));
    }

    update(airplaneTripImport: IAirplaneTripImport): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(airplaneTripImport);
        return this.http
            .put<IAirplaneTripImport>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAirplaneTripImport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAirplaneTripImport[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    import(id: number): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${this.resourceUrl}/${id}/import`, null, { observe: 'response' });
    }

    protected convertDateFromClient(airplaneTripImport: IAirplaneTripImport): IAirplaneTripImport {
        const copy: IAirplaneTripImport = Object.assign({}, airplaneTripImport, {
            dateTime:
                airplaneTripImport.dateTime != null && airplaneTripImport.dateTime.isValid() ? airplaneTripImport.dateTime.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateTime = res.body.dateTime != null ? moment(res.body.dateTime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((airplaneTripImport: IAirplaneTripImport) => {
                airplaneTripImport.dateTime = airplaneTripImport.dateTime != null ? moment(airplaneTripImport.dateTime) : null;
            });
        }
        return res;
    }
}
