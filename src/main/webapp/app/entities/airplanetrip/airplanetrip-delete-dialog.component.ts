import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAirplanetrip } from 'app/shared/model/airplanetrip.model';
import { AirplanetripService } from './airplanetrip.service';

@Component({
    selector: 'jhi-airplanetrip-delete-dialog',
    templateUrl: './airplanetrip-delete-dialog.component.html'
})
export class AirplanetripDeleteDialogComponent {
    airplanetrip: IAirplanetrip;

    constructor(
        protected airplanetripService: AirplanetripService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.airplanetripService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'airplanetripListModification',
                content: 'Deleted an airplanetrip'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-airplanetrip-delete-popup',
    template: ''
})
export class AirplanetripDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ airplanetrip }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AirplanetripDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.airplanetrip = airplanetrip;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/airplanetrip', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/airplanetrip', { outlets: { popup: null } }]);
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
