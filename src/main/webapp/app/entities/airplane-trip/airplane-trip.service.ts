import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAirplaneTrip } from 'app/shared/model/airplane-trip.model';

type EntityResponseType = HttpResponse<IAirplaneTrip>;
type EntityArrayResponseType = HttpResponse<IAirplaneTrip[]>;

@Injectable({ providedIn: 'root' })
export class AirplaneTripService {
    public resourceUrl = SERVER_API_URL + 'api/airplane-trips';

    constructor(protected http: HttpClient) {}

    create(airplaneTrip: IAirplaneTrip): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(airplaneTrip);
        return this.http
            .post<IAirplaneTrip>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(airplaneTrip: IAirplaneTrip): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(airplaneTrip);
        return this.http
            .put<IAirplaneTrip>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAirplaneTrip>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAirplaneTrip[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(airplaneTrip: IAirplaneTrip): IAirplaneTrip {
        const copy: IAirplaneTrip = Object.assign({}, airplaneTrip, {
            departureDate:
                airplaneTrip.departureDate != null && airplaneTrip.departureDate.isValid()
                    ? airplaneTrip.departureDate.format(DATE_FORMAT)
                    : null,
            arrivalDate:
                airplaneTrip.arrivalDate != null && airplaneTrip.arrivalDate.isValid()
                    ? airplaneTrip.arrivalDate.format(DATE_FORMAT)
                    : null,
            departureTime:
                airplaneTrip.departureTime != null && airplaneTrip.departureTime.isValid() ? airplaneTrip.departureTime.toJSON() : null,
            arrivalTime: airplaneTrip.arrivalTime != null && airplaneTrip.arrivalTime.isValid() ? airplaneTrip.arrivalTime.toJSON() : null
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
            res.body.forEach((airplaneTrip: IAirplaneTrip) => {
                airplaneTrip.departureDate = airplaneTrip.departureDate != null ? moment(airplaneTrip.departureDate) : null;
                airplaneTrip.arrivalDate = airplaneTrip.arrivalDate != null ? moment(airplaneTrip.arrivalDate) : null;
                airplaneTrip.departureTime = airplaneTrip.departureTime != null ? moment(airplaneTrip.departureTime) : null;
                airplaneTrip.arrivalTime = airplaneTrip.arrivalTime != null ? moment(airplaneTrip.arrivalTime) : null;
            });
        }
        return res;
    }
}
