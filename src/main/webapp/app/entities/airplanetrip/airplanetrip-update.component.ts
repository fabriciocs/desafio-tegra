import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAirplanetrip } from 'app/shared/model/airplanetrip.model';
import { AirplanetripService } from './airplanetrip.service';
import { IAirport } from 'app/shared/model/airport.model';
import { AirportService } from 'app/entities/airport';
import { IAirline } from 'app/shared/model/airline.model';
import { AirlineService } from 'app/entities/airline';

@Component({
    selector: 'jhi-airplanetrip-update',
    templateUrl: './airplanetrip-update.component.html'
})
export class AirplanetripUpdateComponent implements OnInit {
    airplanetrip: IAirplanetrip;
    isSaving: boolean;

    airports: IAirport[];

    airlines: IAirline[];
    departureDateDp: any;
    arrivalDateDp: any;
    departureTime: string;
    arrivalTime: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected airplanetripService: AirplanetripService,
        protected airportService: AirportService,
        protected airlineService: AirlineService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ airplanetrip }) => {
            this.airplanetrip = airplanetrip;
            this.departureTime = this.airplanetrip.departureTime != null ? this.airplanetrip.departureTime.format(DATE_TIME_FORMAT) : null;
            this.arrivalTime = this.airplanetrip.arrivalTime != null ? this.airplanetrip.arrivalTime.format(DATE_TIME_FORMAT) : null;
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
        this.airplanetrip.departureTime = this.departureTime != null ? moment(this.departureTime, DATE_TIME_FORMAT) : null;
        this.airplanetrip.arrivalTime = this.arrivalTime != null ? moment(this.arrivalTime, DATE_TIME_FORMAT) : null;
        if (this.airplanetrip.id !== undefined) {
            this.subscribeToSaveResponse(this.airplanetripService.update(this.airplanetrip));
        } else {
            this.subscribeToSaveResponse(this.airplanetripService.create(this.airplanetrip));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAirplanetrip>>) {
        result.subscribe((res: HttpResponse<IAirplanetrip>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
