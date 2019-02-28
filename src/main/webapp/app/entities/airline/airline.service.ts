import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAirline } from 'app/shared/model/airline.model';

type EntityResponseType = HttpResponse<IAirline>;
type EntityArrayResponseType = HttpResponse<IAirline[]>;

@Injectable({ providedIn: 'root' })
export class AirlineService {
    public resourceUrl = SERVER_API_URL + 'api/airlines';

    constructor(protected http: HttpClient) {}

    create(airline: IAirline): Observable<EntityResponseType> {
        return this.http.post<IAirline>(this.resourceUrl, airline, { observe: 'response' });
    }

    update(airline: IAirline): Observable<EntityResponseType> {
        return this.http.put<IAirline>(this.resourceUrl, airline, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAirline>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAirline[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
