/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AirplaneTripService } from 'app/entities/airplane-trip/airplane-trip.service';
import { IAirplaneTrip, AirplaneTrip } from 'app/shared/model/airplane-trip.model';

describe('Service Tests', () => {
    describe('AirplaneTrip Service', () => {
        let injector: TestBed;
        let service: AirplaneTripService;
        let httpMock: HttpTestingController;
        let elemDefault: IAirplaneTrip;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(AirplaneTripService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new AirplaneTrip(0, 'AAAAAAA', currentDate, currentDate, currentDate, currentDate, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        departureDate: currentDate.format(DATE_FORMAT),
                        arrivalDate: currentDate.format(DATE_FORMAT),
                        departureTime: currentDate.format(DATE_TIME_FORMAT),
                        arrivalTime: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a AirplaneTrip', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        departureDate: currentDate.format(DATE_FORMAT),
                        arrivalDate: currentDate.format(DATE_FORMAT),
                        departureTime: currentDate.format(DATE_TIME_FORMAT),
                        arrivalTime: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        departureDate: currentDate,
                        arrivalDate: currentDate,
                        departureTime: currentDate,
                        arrivalTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new AirplaneTrip(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a AirplaneTrip', async () => {
                const returnedFromService = Object.assign(
                    {
                        flight: 'BBBBBB',
                        departureDate: currentDate.format(DATE_FORMAT),
                        arrivalDate: currentDate.format(DATE_FORMAT),
                        departureTime: currentDate.format(DATE_TIME_FORMAT),
                        arrivalTime: currentDate.format(DATE_TIME_FORMAT),
                        price: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        departureDate: currentDate,
                        arrivalDate: currentDate,
                        departureTime: currentDate,
                        arrivalTime: currentDate
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

            it('should return a list of AirplaneTrip', async () => {
                const returnedFromService = Object.assign(
                    {
                        flight: 'BBBBBB',
                        departureDate: currentDate.format(DATE_FORMAT),
                        arrivalDate: currentDate.format(DATE_FORMAT),
                        departureTime: currentDate.format(DATE_TIME_FORMAT),
                        arrivalTime: currentDate.format(DATE_TIME_FORMAT),
                        price: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        departureDate: currentDate,
                        arrivalDate: currentDate,
                        departureTime: currentDate,
                        arrivalTime: currentDate
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

            it('should delete a AirplaneTrip', async () => {
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
