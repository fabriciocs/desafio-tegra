import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAirport } from 'app/shared/model/airport.model';
import { AirportService } from './airport.service';

@Component({
    selector: 'jhi-airport-delete-dialog',
    templateUrl: './airport-delete-dialog.component.html'
})
export class AirportDeleteDialogComponent {
    airport: IAirport;

    constructor(protected airportService: AirportService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.airportService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'airportListModification',
                content: 'Deleted an airport'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-airport-delete-popup',
    template: ''
})
export class AirportDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ airport }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AirportDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.airport = airport;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/airport', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/airport', { outlets: { popup: null } }]);
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
