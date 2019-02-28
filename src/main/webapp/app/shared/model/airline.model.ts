export interface IAirline {
    id?: number;
    name?: string;
}

export class Airline implements IAirline {
    constructor(public id?: number, public name?: string) {}
}
