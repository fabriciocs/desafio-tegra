import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAirplaneTrip } from 'app/shared/model/airplane-trip.model';

@Component({
    selector: 'jhi-airplane-trip-detail',
    templateUrl: './airplane-trip-detail.component.html'
})
export class AirplaneTripDetailComponent implements OnInit {
    airplaneTrip: IAirplaneTrip;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ airplaneTrip }) => {
            this.airplaneTrip = airplaneTrip;
        });
    }

    previousState() {
        window.history.back();
    }
}
