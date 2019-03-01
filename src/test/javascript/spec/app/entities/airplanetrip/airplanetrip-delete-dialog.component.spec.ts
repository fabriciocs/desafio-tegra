/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TesteTestModule } from '../../../test.module';
import { AirplanetripDeleteDialogComponent } from 'app/entities/airplanetrip/airplanetrip-delete-dialog.component';
import { AirplanetripService } from 'app/entities/airplanetrip/airplanetrip.service';

describe('Component Tests', () => {
    describe('Airplanetrip Management Delete Component', () => {
        let comp: AirplanetripDeleteDialogComponent;
        let fixture: ComponentFixture<AirplanetripDeleteDialogComponent>;
        let service: AirplanetripService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TesteTestModule],
                declarations: [AirplanetripDeleteDialogComponent]
            })
                .overrideTemplate(AirplanetripDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AirplanetripDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AirplanetripService);
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
