import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TesteSharedModule } from 'app/shared';
import {
    AirplaneTripComponent,
    AirplaneTripDetailComponent,
    AirplaneTripUpdateComponent,
    AirplaneTripDeletePopupComponent,
    AirplaneTripDeleteDialogComponent,
    airplaneTripRoute,
    airplaneTripPopupRoute
} from './';

const ENTITY_STATES = [...airplaneTripRoute, ...airplaneTripPopupRoute];

@NgModule({
    imports: [TesteSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AirplaneTripComponent,
        AirplaneTripDetailComponent,
        AirplaneTripUpdateComponent,
        AirplaneTripDeleteDialogComponent,
        AirplaneTripDeletePopupComponent
    ],
    entryComponents: [
        AirplaneTripComponent,
        AirplaneTripUpdateComponent,
        AirplaneTripDeleteDialogComponent,
        AirplaneTripDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TesteAirplaneTripModule {}
