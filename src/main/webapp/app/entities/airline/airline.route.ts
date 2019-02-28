import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Airline } from 'app/shared/model/airline.model';
import { AirlineService } from './airline.service';
import { AirlineComponent } from './airline.component';
import { AirlineDetailComponent } from './airline-detail.component';
import { AirlineUpdateComponent } from './airline-update.component';
import { AirlineDeletePopupComponent } from './airline-delete-dialog.component';
import { IAirline } from 'app/shared/model/airline.model';

@Injectable({ providedIn: 'root' })
export class AirlineResolve implements Resolve<IAirline> {
    constructor(private service: AirlineService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAirline> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Airline>) => response.ok),
                map((airline: HttpResponse<Airline>) => airline.body)
            );
        }
        return of(new Airline());
    }
}

export const airlineRoute: Routes = [
    {
        path: '',
        component: AirlineComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Airlines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AirlineDetailComponent,
        resolve: {
            airline: AirlineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Airlines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AirlineUpdateComponent,
        resolve: {
            airline: AirlineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Airlines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AirlineUpdateComponent,
        resolve: {
            airline: AirlineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Airlines'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const airlinePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AirlineDeletePopupComponent,
        resolve: {
            airline: AirlineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Airlines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
