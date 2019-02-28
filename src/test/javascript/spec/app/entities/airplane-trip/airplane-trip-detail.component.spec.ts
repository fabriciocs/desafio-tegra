/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TesteTestModule } from '../../../test.module';
import { AirplaneTripDetailComponent } from 'app/entities/airplane-trip/airplane-trip-detail.component';
import { AirplaneTrip } from 'app/shared/model/airplane-trip.model';

describe('Component Tests', () => {
    describe('AirplaneTrip Management Detail Component', () => {
        let comp: AirplaneTripDetailComponent;
        let fixture: ComponentFixture<AirplaneTripDetailComponent>;
        const route = ({ data: of({ airplaneTrip: new AirplaneTrip(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TesteTestModule],
                declarations: [AirplaneTripDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AirplaneTripDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AirplaneTripDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.airplaneTrip).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
