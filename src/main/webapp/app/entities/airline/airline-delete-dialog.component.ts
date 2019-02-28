import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAirline } from 'app/shared/model/airline.model';
import { AirlineService } from './airline.service';

@Component({
    selector: 'jhi-airline-delete-dialog',
    templateUrl: './airline-delete-dialog.component.html'
})
export class AirlineDeleteDialogComponent {
    airline: IAirline;

    constructor(protected airlineService: AirlineService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.airlineService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'airlineListModification',
                content: 'Deleted an airline'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-airline-delete-popup',
    template: ''
})
export class AirlineDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ airline }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AirlineDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.airline = airline;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/airline', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/airline', { outlets: { popup: null } }]);
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
