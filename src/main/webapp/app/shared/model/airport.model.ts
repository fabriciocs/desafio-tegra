export interface IAirport {
    id?: number;
    name?: string;
    airport?: string;
    city?: string;
}

export class Airport implements IAirport {
    constructor(public id?: number, public name?: string, public airport?: string, public city?: string) {}
}
