/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TesteTestModule } from '../../../test.module';
import { AirplaneTripImportDeleteDialogComponent } from 'app/entities/airplane-trip-import/airplane-trip-import-delete-dialog.component';
import { AirplaneTripImportService } from 'app/entities/airplane-trip-import/airplane-trip-import.service';

describe('Component Tests', () => {
    describe('AirplaneTripImport Management Delete Component', () => {
        let comp: AirplaneTripImportDeleteDialogComponent;
        let fixture: ComponentFixture<AirplaneTripImportDeleteDialogComponent>;
        let service: AirplaneTripImportService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TesteTestModule],
                declarations: [AirplaneTripImportDeleteDialogComponent]
            })
                .overrideTemplate(AirplaneTripImportDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AirplaneTripImportDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AirplaneTripImportService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
