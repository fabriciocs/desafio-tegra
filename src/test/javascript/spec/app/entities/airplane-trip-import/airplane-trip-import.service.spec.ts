/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AirplaneTripImportService } from 'app/entities/airplane-trip-import/airplane-trip-import.service';
import { IAirplaneTripImport, AirplaneTripImport, ImportStatus } from 'app/shared/model/airplane-trip-import.model';

describe('Service Tests', () => {
    describe('AirplaneTripImport Service', () => {
        let injector: TestBed;
        let service: AirplaneTripImportService;
        let httpMock: HttpTestingController;
        let elemDefault: IAirplaneTripImport;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(AirplaneTripImportService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new AirplaneTripImport(0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', ImportStatus.SUCCESS);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateTime: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a AirplaneTripImport', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateTime: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new AirplaneTripImport(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a AirplaneTripImport', async () => {
                const returnedFromService = Object.assign(
                    {
                        file: 'BBBBBB',
                        airline: 'BBBBBB',
                        dateTime: currentDate.format(DATE_TIME_FORMAT),
                        mimeType: 'BBBBBB',
                        status: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of AirplaneTripImport', async () => {
                const returnedFromService = Object.assign(
                    {
                        file: 'BBBBBB',
                        airline: 'BBBBBB',
                        dateTime: currentDate.format(DATE_TIME_FORMAT),
                        mimeType: 'BBBBBB',
                        status: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a AirplaneTripImport', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
