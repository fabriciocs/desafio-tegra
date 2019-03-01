/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TesteTestModule } from '../../../test.module';
import { AirplanetripUpdateComponent } from 'app/entities/airplanetrip/airplanetrip-update.component';
import { AirplanetripService } from 'app/entities/airplanetrip/airplanetrip.service';
import { Airplanetrip } from 'app/shared/model/airplanetrip.model';

describe('Component Tests', () => {
    describe('Airplanetrip Management Update Component', () => {
        let comp: AirplanetripUpdateComponent;
        let fixture: ComponentFixture<AirplanetripUpdateComponent>;
        let service: AirplanetripService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TesteTestModule],
                declarations: [AirplanetripUpdateComponent]
            })
                .overrideTemplate(AirplanetripUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AirplanetripUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AirplanetripService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Airplanetrip(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.airplanetrip = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Airplanetrip();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.airplanetrip = entity;
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
