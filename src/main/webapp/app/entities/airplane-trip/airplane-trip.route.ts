import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AirplaneTrip } from 'app/shared/model/airplane-trip.model';
import { AirplaneTripService } from './airplane-trip.service';
import { AirplaneTripComponent } from './airplane-trip.component';
import { AirplaneTripDetailComponent } from './airplane-trip-detail.component';
import { AirplaneTripUpdateComponent } from './airplane-trip-update.component';
import { AirplaneTripDeletePopupComponent } from './airplane-trip-delete-dialog.component';
import { IAirplaneTrip } from 'app/shared/model/airplane-trip.model';

@Injectable({ providedIn: 'root' })
export class AirplaneTripResolve implements Resolve<IAirplaneTrip> {
    constructor(private service: AirplaneTripService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAirplaneTrip> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AirplaneTrip>) => response.ok),
                map((airplaneTrip: HttpResponse<AirplaneTrip>) => airplaneTrip.body)
            );
        }
        return of(new AirplaneTrip());
    }
}

export const airplaneTripRoute: Routes = [
    {
        path: '',
        component: AirplaneTripComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'AirplaneTrips'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AirplaneTripDetailComponent,
        resolve: {
            airplaneTrip: AirplaneTripResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AirplaneTrips'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AirplaneTripUpdateComponent,
        resolve: {
            airplaneTrip: AirplaneTripResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AirplaneTrips'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AirplaneTripUpdateComponent,
        resolve: {
            airplaneTrip: AirplaneTripResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AirplaneTrips'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const airplaneTripPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AirplaneTripDeletePopupComponent,
        resolve: {
            airplaneTrip: AirplaneTripResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AirplaneTrips'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
