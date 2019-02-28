import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AirplaneTripImport } from 'app/shared/model/airplane-trip-import.model';
import { AirplaneTripImportService } from './airplane-trip-import.service';
import { AirplaneTripImportComponent } from './airplane-trip-import.component';
import { AirplaneTripImportDetailComponent } from './airplane-trip-import-detail.component';
import { AirplaneTripImportUpdateComponent } from './airplane-trip-import-update.component';
import { AirplaneTripImportDeletePopupComponent } from './airplane-trip-import-delete-dialog.component';
import { IAirplaneTripImport } from 'app/shared/model/airplane-trip-import.model';

@Injectable({ providedIn: 'root' })
export class AirplaneTripImportResolve implements Resolve<IAirplaneTripImport> {
    constructor(private service: AirplaneTripImportService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAirplaneTripImport> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AirplaneTripImport>) => response.ok),
                map((airplaneTripImport: HttpResponse<AirplaneTripImport>) => airplaneTripImport.body)
            );
        }
        return of(new AirplaneTripImport());
    }
}

export const airplaneTripImportRoute: Routes = [
    {
        path: '',
        component: AirplaneTripImportComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'AirplaneTripImports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AirplaneTripImportDetailComponent,
        resolve: {
            airplaneTripImport: AirplaneTripImportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AirplaneTripImports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AirplaneTripImportUpdateComponent,
        resolve: {
            airplaneTripImport: AirplaneTripImportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AirplaneTripImports'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AirplaneTripImportUpdateComponent,
        resolve: {
            airplaneTripImport: AirplaneTripImportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AirplaneTripImports'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const airplaneTripImportPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AirplaneTripImportDeletePopupComponent,
        resolve: {
            airplaneTripImport: AirplaneTripImportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AirplaneTripImports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
