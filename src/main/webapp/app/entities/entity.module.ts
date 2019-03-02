import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'airplane-trip-import',
                loadChildren: './airplane-trip-import/airplane-trip-import.module#TesteAirplaneTripImportModule'
            },
            {
                path: 'airport',
                loadChildren: './airport/airport.module#TesteAirportModule'
            },
            {
                path: 'airline',
                loadChildren: './airline/airline.module#TesteAirlineModule'
            },
            {
                path: 'airplanetrip',
                loadChildren: './airplanetrip/airplanetrip.module#TesteAirplanetripModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TesteEntityModule {}
