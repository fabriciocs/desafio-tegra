import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAirport } from 'app/shared/model/airport.model';

@Component({
    selector: 'jhi-airport-detail',
    templateUrl: './airport-detail.component.html'
})
export class AirportDetailComponent implements OnInit {
    airport: IAirport;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ airport }) => {
            this.airport = airport;
        });
    }

    previousState() {
        window.history.back();
    }
}
