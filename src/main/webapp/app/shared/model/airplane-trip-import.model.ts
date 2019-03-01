import { Moment } from 'moment';

export const enum ImportStatus {
    SUCCESS = 'SUCCESS',
    FAIL = 'FAIL',
    WARNING = 'WARNING',
    WAITING = 'WAITING',
    PROCESSING = 'PROCESSING'
}

export interface IAirplaneTripImport {
    id?: number;
    file?: string;
    airline?: string;
    dateTime?: Moment;
    mimeType?: string;
    status?: ImportStatus;
}

export class AirplaneTripImport implements IAirplaneTripImport {
    constructor(
        public id?: number,
        public file?: string,
        public airline?: string,
        public dateTime?: Moment,
        public mimeType?: string,
        public status?: ImportStatus
    ) {}
}
