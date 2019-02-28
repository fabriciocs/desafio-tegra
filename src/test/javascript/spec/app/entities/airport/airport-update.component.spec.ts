/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TesteTestModule } from '../../../test.module';
import { AirportUpdateComponent } from 'app/entities/airport/airport-update.component';
import { AirportService } from 'app/entities/airport/airport.service';
import { Airport } from 'app/shared/model/airport.model';

describe('Component Tests', () => {
    describe('Airport Management Update Component', () => {
        let comp: AirportUpdateComponent;
        let fixture: ComponentFixture<AirportUpdateComponent>;
        let service: AirportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TesteTestModule],
                declarations: [AirportUpdateComponent]
            })
                .overrideTemplate(AirportUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AirportUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AirportService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Airport(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.airport = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Airport();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.airport = entity;
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
