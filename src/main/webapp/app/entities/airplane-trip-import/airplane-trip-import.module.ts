import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TesteSharedModule } from 'app/shared';
import {
    AirplaneTripImportComponent,
    AirplaneTripImportDetailComponent,
    AirplaneTripImportUpdateComponent,
    AirplaneTripImportDeletePopupComponent,
    AirplaneTripImportDeleteDialogComponent,
    airplaneTripImportRoute,
    airplaneTripImportPopupRoute
} from './';

const ENTITY_STATES = [...airplaneTripImportRoute, ...airplaneTripImportPopupRoute];

@NgModule({
    imports: [TesteSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AirplaneTripImportComponent,
        AirplaneTripImportDetailComponent,
        AirplaneTripImportUpdateComponent,
        AirplaneTripImportDeleteDialogComponent,
        AirplaneTripImportDeletePopupComponent
    ],
    entryComponents: [
        AirplaneTripImportComponent,
        AirplaneTripImportUpdateComponent,
        AirplaneTripImportDeleteDialogComponent,
        AirplaneTripImportDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TesteAirplaneTripImportModule {}
