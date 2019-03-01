import { Moment } from 'moment';
import { IAirport } from 'app/shared/model/airport.model';
import { IAirline } from 'app/shared/model/airline.model';

export interface IAirplanetrip {
    id?: number;
    flight?: string;
    departureDate?: Moment;
    arrivalDate?: Moment;
    departureTime?: Moment;
    arrivalTime?: Moment;
    price?: number;
    departureAirport?: IAirport;
    arrivalAirport?: IAirport;
    airline?: IAirline;
}

export class Airplanetrip implements IAirplanetrip {
    constructor(
        public id?: number,
        public flight?: string,
        public departureDate?: Moment,
        public arrivalDate?: Moment,
        public departureTime?: Moment,
        public arrivalTime?: Moment,
        public price?: number,
        public departureAirport?: IAirport,
        public arrivalAirport?: IAirport,
        public airline?: IAirline
    ) {}
}
