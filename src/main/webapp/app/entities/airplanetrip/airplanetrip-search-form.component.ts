import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAirplanetrip } from 'app/shared/model/airplanetrip.model';
import { AccountService } from 'app/core';
import { AirplaneTrip } from 'app/shared/model/airplane-trip.model';
import { IAirline } from 'app/shared/model/airline.model';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { AirportService } from 'app/entities/airport';
import { AirlineService } from 'app/entities/airline';
import { IAirport } from 'app/shared/model/airport.model';

@Component({
    selector: 'jhi-airplane-trip-search-form',
    templateUrl: './airplanetrip-search-form.component.html'
})
export class AirplanetripSearchFormComponent implements OnInit, OnDestroy {
    currentAccount: any;
    error: any;
    success: any;
    eventSubscriber: Subscription;
    totalItems: any;
    page: any;
    predicate: any;
    reverse: any;
    airplaneTrip: IAirplanetrip = new AirplaneTrip();
    airlines: IAirline[];
    airports: IAirport[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected airportService: AirportService,
        protected airlineService: AirlineService,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
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

    ngOnDestroy() {
        // this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAirplanetrip) {
        return item.id;
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
