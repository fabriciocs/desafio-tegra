import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAirline } from 'app/shared/model/airline.model';

@Component({
    selector: 'jhi-airline-detail',
    templateUrl: './airline-detail.component.html'
})
export class AirlineDetailComponent implements OnInit {
    airline: IAirline;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ airline }) => {
            this.airline = airline;
        });
    }

    previousState() {
        window.history.back();
    }
}
