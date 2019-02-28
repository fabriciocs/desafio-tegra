/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TesteTestModule } from '../../../test.module';
import { AirportDetailComponent } from 'app/entities/airport/airport-detail.component';
import { Airport } from 'app/shared/model/airport.model';

describe('Component Tests', () => {
    describe('Airport Management Detail Component', () => {
        let comp: AirportDetailComponent;
        let fixture: ComponentFixture<AirportDetailComponent>;
        const route = ({ data: of({ airport: new Airport(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TesteTestModule],
                declarations: [AirportDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AirportDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AirportDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.airport).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
