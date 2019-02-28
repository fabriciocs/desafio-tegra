import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAirplaneTripImport } from 'app/shared/model/airplane-trip-import.model';

@Component({
    selector: 'jhi-airplane-trip-import-detail',
    templateUrl: './airplane-trip-import-detail.component.html'
})
export class AirplaneTripImportDetailComponent implements OnInit {
    airplaneTripImport: IAirplaneTripImport;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ airplaneTripImport }) => {
            this.airplaneTripImport = airplaneTripImport;
        });
    }

    previousState() {
        window.history.back();
    }
}
