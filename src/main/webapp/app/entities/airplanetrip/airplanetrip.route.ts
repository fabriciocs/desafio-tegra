import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Airplanetrip } from 'app/shared/model/airplanetrip.model';
import { AirplanetripService } from './airplanetrip.service';
import { AirplanetripComponent } from './airplanetrip.component';
import { AirplanetripDetailComponent } from './airplanetrip-detail.component';
import { AirplanetripUpdateComponent } from './airplanetrip-update.component';
import { AirplanetripDeletePopupComponent } from './airplanetrip-delete-dialog.component';
import { IAirplanetrip } from 'app/shared/model/airplanetrip.model';

@Injectable({ providedIn: 'root' })
export class AirplanetripResolve implements Resolve<IAirplanetrip> {
    constructor(private service: AirplanetripService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAirplanetrip> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Airplanetrip>) => response.ok),
                map((airplanetrip: HttpResponse<Airplanetrip>) => airplanetrip.body)
            );
        }
        return of(new Airplanetrip());
    }
}

export const airplanetripRoute: Routes = [
    {
        path: '',
        component: AirplanetripComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Airplanetrips'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AirplanetripDetailComponent,
        resolve: {
            airplanetrip: AirplanetripResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Airplanetrips'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AirplanetripUpdateComponent,
        resolve: {
            airplanetrip: AirplanetripResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Airplanetrips'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AirplanetripUpdateComponent,
        resolve: {
            airplanetrip: AirplanetripResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Airplanetrips'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const airplanetripPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AirplanetripDeletePopupComponent,
        resolve: {
            airplanetrip: AirplanetripResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Airplanetrips'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
