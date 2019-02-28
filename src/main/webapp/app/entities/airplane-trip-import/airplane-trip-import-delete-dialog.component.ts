import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAirplaneTripImport } from 'app/shared/model/airplane-trip-import.model';
import { AirplaneTripImportService } from './airplane-trip-import.service';

@Component({
    selector: 'jhi-airplane-trip-import-delete-dialog',
    templateUrl: './airplane-trip-import-delete-dialog.component.html'
})
export class AirplaneTripImportDeleteDialogComponent {
    airplaneTripImport: IAirplaneTripImport;

    constructor(
        protected airplaneTripImportService: AirplaneTripImportService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.airplaneTripImportService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'airplaneTripImportListModification',
                content: 'Deleted an airplaneTripImport'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-airplane-trip-import-delete-popup',
    template: ''
})
export class AirplaneTripImportDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ airplaneTripImport }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AirplaneTripImportDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.airplaneTripImport = airplaneTripImport;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/airplane-trip-import', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/airplane-trip-import', { outlets: { popup: null } }]);
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
