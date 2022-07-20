import { user } from "./user";
import { Flight } from "./flight";
export class Bookingdata {
    id: any;
    user: user = new user;
    flight: Flight = new Flight;
    seats: any;
    status: any;
    date: any;
}


