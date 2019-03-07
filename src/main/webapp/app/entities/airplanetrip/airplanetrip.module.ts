import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TesteSharedModule } from 'app/shared';
import {
    AirplanetripComponent,
    AirplanetripDetailComponent,
    AirplanetripUpdateComponent,
    AirplanetripDeletePopupComponent,
    AirplanetripDeleteDialogComponent,
    airplanetripRoute,
    airplanetripPopupRoute
} from './';
const ENTITY_STATES = [...airplanetripRoute, ...airplanetripPopupRoute];

@NgModule({
    imports: [TesteSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AirplanetripComponent,
        AirplanetripDetailComponent,
        AirplanetripUpdateComponent,
        AirplanetripDeleteDialogComponent,
        AirplanetripDeletePopupComponent
    ],
    entryComponents: [
        AirplanetripComponent,
        AirplanetripUpdateComponent,
        AirplanetripDeleteDialogComponent,
        AirplanetripDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TesteAirplanetripModule {}
