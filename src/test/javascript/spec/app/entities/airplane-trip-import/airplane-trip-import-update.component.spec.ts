/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TesteTestModule } from '../../../test.module';
import { AirplaneTripImportUpdateComponent } from 'app/entities/airplane-trip-import/airplane-trip-import-update.component';
import { AirplaneTripImportService } from 'app/entities/airplane-trip-import/airplane-trip-import.service';
import { AirplaneTripImport } from 'app/shared/model/airplane-trip-import.model';

describe('Component Tests', () => {
    describe('AirplaneTripImport Management Update Component', () => {
        let comp: AirplaneTripImportUpdateComponent;
        let fixture: ComponentFixture<AirplaneTripImportUpdateComponent>;
        let service: AirplaneTripImportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TesteTestModule],
                declarations: [AirplaneTripImportUpdateComponent]
            })
                .overrideTemplate(AirplaneTripImportUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AirplaneTripImportUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AirplaneTripImportService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AirplaneTripImport(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.airplaneTripImport = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AirplaneTripImport();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.airplaneTripImport = entity;
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
