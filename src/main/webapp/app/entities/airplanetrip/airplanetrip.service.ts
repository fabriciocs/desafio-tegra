import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAirplanetrip } from 'app/shared/model/airplanetrip.model';

type EntityResponseType = HttpResponse<IAirplanetrip>;
type EntityArrayResponseType = HttpResponse<IAirplanetrip[]>;

@Injectable({ providedIn: 'root' })
export class AirplanetripService {
    public resourceUrl = SERVER_API_URL + 'api/airplanetrips';

    constructor(protected http: HttpClient) {}

    create(airplanetrip: IAirplanetrip): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(airplanetrip);
        return this.http
            .post<IAirplanetrip>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(airplanetrip: IAirplanetrip): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(airplanetrip);
        return this.http
            .put<IAirplanetrip>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAirplanetrip>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAirplanetrip[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    import(): Observable<HttpResponse<any>> {
        return this.http.post<any>(`${this.resourceUrl}/import`, null, { observe: 'response' });
    }

    protected convertDateFromClient(airplanetrip: IAirplanetrip): IAirplanetrip {
        const copy: IAirplanetrip = Object.assign({}, airplanetrip, {
            departureDate:
                airplanetrip.departureDate != null && airplanetrip.departureDate.isValid()
                    ? airplanetrip.departureDate.format(DATE_FORMAT)
                    : null,
            arrivalDate:
                airplanetrip.arrivalDate != null && airplanetrip.arrivalDate.isValid()
                    ? airplanetrip.arrivalDate.format(DATE_FORMAT)
                    : null,
            departureTime:
                airplanetrip.departureTime != null && airplanetrip.departureTime.isValid() ? airplanetrip.departureTime.toJSON() : null,
            arrivalTime: airplanetrip.arrivalTime != null && airplanetrip.arrivalTime.isValid() ? airplanetrip.arrivalTime.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.departureDate = res.body.departureDate != null ? moment(res.body.departureDate) : null;
            res.body.arrivalDate = res.body.arrivalDate != null ? moment(res.body.arrivalDate) : null;
            res.body.departureTime = res.body.departureTime != null ? moment(res.body.departureTime) : null;
            res.body.arrivalTime = res.body.arrivalTime != null ? moment(res.body.arrivalTime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((airplanetrip: IAirplanetrip) => {
                airplanetrip.departureDate = airplanetrip.departureDate != null ? moment(airplanetrip.departureDate) : null;
                airplanetrip.arrivalDate = airplanetrip.arrivalDate != null ? moment(airplanetrip.arrivalDate) : null;
                airplanetrip.departureTime = airplanetrip.departureTime != null ? moment(airplanetrip.departureTime) : null;
                airplanetrip.arrivalTime = airplanetrip.arrivalTime != null ? moment(airplanetrip.arrivalTime) : null;
            });
        }
        return res;
    }
}
