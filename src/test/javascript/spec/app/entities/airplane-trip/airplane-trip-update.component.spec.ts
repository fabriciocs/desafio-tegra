/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TesteTestModule } from '../../../test.module';
import { AirplaneTripUpdateComponent } from 'app/entities/airplane-trip/airplane-trip-update.component';
import { AirplaneTripService } from 'app/entities/airplane-trip/airplane-trip.service';
import { AirplaneTrip } from 'app/shared/model/airplane-trip.model';

describe('Component Tests', () => {
    describe('AirplaneTrip Management Update Component', () => {
        let comp: AirplaneTripUpdateComponent;
        let fixture: ComponentFixture<AirplaneTripUpdateComponent>;
        let service: AirplaneTripService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TesteTestModule],
                declarations: [AirplaneTripUpdateComponent]
            })
                .overrideTemplate(AirplaneTripUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AirplaneTripUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AirplaneTripService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AirplaneTrip(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.airplaneTrip = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AirplaneTrip();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.airplaneTrip = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
