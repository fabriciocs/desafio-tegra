/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TesteTestModule } from '../../../test.module';
import { AirlineUpdateComponent } from 'app/entities/airline/airline-update.component';
import { AirlineService } from 'app/entities/airline/airline.service';
import { Airline } from 'app/shared/model/airline.model';

describe('Component Tests', () => {
    describe('Airline Management Update Component', () => {
        let comp: AirlineUpdateComponent;
        let fixture: ComponentFixture<AirlineUpdateComponent>;
        let service: AirlineService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TesteTestModule],
                declarations: [AirlineUpdateComponent]
            })
                .overrideTemplate(AirlineUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AirlineUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AirlineService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Airline(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.airline = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Airline();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.airline = entity;
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
