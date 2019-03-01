import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAirplanetrip } from 'app/shared/model/airplanetrip.model';

@Component({
    selector: 'jhi-airplanetrip-detail',
    templateUrl: './airplanetrip-detail.component.html'
})
export class AirplanetripDetailComponent implements OnInit {
    airplanetrip: IAirplanetrip;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ airplanetrip }) => {
            this.airplanetrip = airplanetrip;
        });
    }

    previousState() {
        window.history.back();
    }
}
