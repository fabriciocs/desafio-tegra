import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IAirplaneTripImport } from 'app/shared/model/airplane-trip-import.model';
import { AirplaneTripImportService } from './airplane-trip-import.service';

@Component({
    selector: 'jhi-airplane-trip-import-update',
    templateUrl: './airplane-trip-import-update.component.html'
})
export class AirplaneTripImportUpdateComponent implements OnInit {
    airplaneTripImport: IAirplaneTripImport;
    isSaving: boolean;
    dateTime: string;
    file: File;

    constructor(protected airplaneTripImportService: AirplaneTripImportService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ airplaneTripImport }) => {
            this.airplaneTripImport = airplaneTripImport;
            this.dateTime = this.airplaneTripImport.dateTime != null ? this.airplaneTripImport.dateTime.format(DATE_TIME_FORMAT) : null;
        });
    }

    setFile(files: FileList) {
        this.file = files.item(0);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;

        this.subscribeToSaveResponse(this.airplaneTripImportService.create(this.airplaneTripImport, this.file));
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAirplaneTripImport>>) {
        result.subscribe((res: HttpResponse<IAirplaneTripImport>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
