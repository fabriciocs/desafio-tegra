/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TesteTestModule } from '../../../test.module';
import { AirlineDetailComponent } from 'app/entities/airline/airline-detail.component';
import { Airline } from 'app/shared/model/airline.model';

describe('Component Tests', () => {
    describe('Airline Management Detail Component', () => {
        let comp: AirlineDetailComponent;
        let fixture: ComponentFixture<AirlineDetailComponent>;
        const route = ({ data: of({ airline: new Airline(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TesteTestModule],
                declarations: [AirlineDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AirlineDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AirlineDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.airline).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
