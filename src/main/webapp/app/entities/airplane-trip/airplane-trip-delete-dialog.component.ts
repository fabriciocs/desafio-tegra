import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAirplaneTrip } from 'app/shared/model/airplane-trip.model';
import { AirplaneTripService } from './airplane-trip.service';

@Component({
    selector: 'jhi-airplane-trip-delete-dialog',
    templateUrl: './airplane-trip-delete-dialog.component.html'
})
export class AirplaneTripDeleteDialogComponent {
    airplaneTrip: IAirplaneTrip;

    constructor(
        protected airplaneTripService: AirplaneTripService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.airplaneTripService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'airplaneTripListModification',
                content: 'Deleted an airplaneTrip'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-airplane-trip-delete-popup',
    template: ''
})
export class AirplaneTripDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ airplaneTrip }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AirplaneTripDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.airplaneTrip = airplaneTrip;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/airplane-trip', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/airplane-trip', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
