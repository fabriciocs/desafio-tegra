import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAirplaneTrip } from 'app/shared/model/airplane-trip.model';
import { AirplaneTripService } from './airplane-trip.service';
import { IAirport } from 'app/shared/model/airport.model';
import { AirportService } from 'app/entities/airport';
import { IAirline } from 'app/shared/model/airline.model';
import { AirlineService } from 'app/entities/airline';

@Component({
    selector: 'jhi-airplane-trip-update',
    templateUrl: './airplane-trip-update.component.html'
})
export class AirplaneTripUpdateComponent implements OnInit {
    airplaneTrip: IAirplaneTrip;
    isSaving: boolean;

    airports: IAirport[];

    airlines: IAirline[];
    departureDateDp: any;
    arrivalDateDp: any;
    departureTime: string;
    arrivalTime: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected airplaneTripService: AirplaneTripService,
        protected airportService: AirportService,
        protected airlineService: AirlineService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ airplaneTrip }) => {
            this.airplaneTrip = airplaneTrip;
            this.departureTime = this.airplaneTrip.departureTime != null ? this.airplaneTrip.departureTime.format(DATE_TIME_FORMAT) : null;
            this.arrivalTime = this.airplaneTrip.arrivalTime != null ? this.airplaneTrip.arrivalTime.format(DATE_TIME_FORMAT) : null;
        });
        this.airportService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAirport[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAirport[]>) => response.body)
            )
            .subscribe((res: IAirport[]) => (this.airports = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.airlineService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAirline[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAirline[]>) => response.body)
            )
            .subscribe((res: IAirline[]) => (this.airlines = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.airplaneTrip.departureTime = this.departureTime != null ? moment(this.departureTime, DATE_TIME_FORMAT) : null;
        this.airplaneTrip.arrivalTime = this.arrivalTime != null ? moment(this.arrivalTime, DATE_TIME_FORMAT) : null;
        if (this.airplaneTrip.id !== undefined) {
            this.subscribeToSaveResponse(this.airplaneTripService.update(this.airplaneTrip));
        } else {
            this.subscribeToSaveResponse(this.airplaneTripService.create(this.airplaneTrip));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAirplaneTrip>>) {
        result.subscribe((res: HttpResponse<IAirplaneTrip>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAirportById(index: number, item: IAirport) {
        return item.id;
    }

    trackAirlineById(index: number, item: IAirline) {
        return item.id;
    }
}
