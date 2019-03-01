/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TesteTestModule } from '../../../test.module';
import { AirplanetripDetailComponent } from 'app/entities/airplanetrip/airplanetrip-detail.component';
import { Airplanetrip } from 'app/shared/model/airplanetrip.model';

describe('Component Tests', () => {
    describe('Airplanetrip Management Detail Component', () => {
        let comp: AirplanetripDetailComponent;
        let fixture: ComponentFixture<AirplanetripDetailComponent>;
        const route = ({ data: of({ airplanetrip: new Airplanetrip(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TesteTestModule],
                declarations: [AirplanetripDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AirplanetripDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AirplanetripDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.airplanetrip).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
