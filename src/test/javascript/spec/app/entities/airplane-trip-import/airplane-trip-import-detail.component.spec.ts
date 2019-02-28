/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TesteTestModule } from '../../../test.module';
import { AirplaneTripImportDetailComponent } from 'app/entities/airplane-trip-import/airplane-trip-import-detail.component';
import { AirplaneTripImport } from 'app/shared/model/airplane-trip-import.model';

describe('Component Tests', () => {
    describe('AirplaneTripImport Management Detail Component', () => {
        let comp: AirplaneTripImportDetailComponent;
        let fixture: ComponentFixture<AirplaneTripImportDetailComponent>;
        const route = ({ data: of({ airplaneTripImport: new AirplaneTripImport(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TesteTestModule],
                declarations: [AirplaneTripImportDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AirplaneTripImportDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AirplaneTripImportDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.airplaneTripImport).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
