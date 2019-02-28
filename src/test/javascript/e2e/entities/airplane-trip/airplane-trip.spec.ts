/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AirplaneTripComponentsPage, AirplaneTripDeleteDialog, AirplaneTripUpdatePage } from './airplane-trip.page-object';

const expect = chai.expect;

describe('AirplaneTrip e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let airplaneTripUpdatePage: AirplaneTripUpdatePage;
    let airplaneTripComponentsPage: AirplaneTripComponentsPage;
    let airplaneTripDeleteDialog: AirplaneTripDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load AirplaneTrips', async () => {
        await navBarPage.goToEntity('airplane-trip');
        airplaneTripComponentsPage = new AirplaneTripComponentsPage();
        await browser.wait(ec.visibilityOf(airplaneTripComponentsPage.title), 5000);
        expect(await airplaneTripComponentsPage.getTitle()).to.eq('Airplane Trips');
    });

    it('should load create AirplaneTrip page', async () => {
        await airplaneTripComponentsPage.clickOnCreateButton();
        airplaneTripUpdatePage = new AirplaneTripUpdatePage();
        expect(await airplaneTripUpdatePage.getPageTitle()).to.eq('Create or edit a Airplane Trip');
        await airplaneTripUpdatePage.cancel();
    });

    it('should create and save AirplaneTrips', async () => {
        const nbButtonsBeforeCreate = await airplaneTripComponentsPage.countDeleteButtons();

        await airplaneTripComponentsPage.clickOnCreateButton();
        await promise.all([
            airplaneTripUpdatePage.setFlightInput('flight'),
            airplaneTripUpdatePage.setDepartureDateInput('2000-12-31'),
            airplaneTripUpdatePage.setArrivalDateInput('2000-12-31'),
            airplaneTripUpdatePage.setDepartureTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            airplaneTripUpdatePage.setArrivalTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            airplaneTripUpdatePage.setPriceInput('5'),
            airplaneTripUpdatePage.departureAirportSelectLastOption(),
            airplaneTripUpdatePage.arrivalAirportSelectLastOption(),
            airplaneTripUpdatePage.airlineSelectLastOption()
        ]);
        expect(await airplaneTripUpdatePage.getFlightInput()).to.eq('flight');
        expect(await airplaneTripUpdatePage.getDepartureDateInput()).to.eq('2000-12-31');
        expect(await airplaneTripUpdatePage.getArrivalDateInput()).to.eq('2000-12-31');
        expect(await airplaneTripUpdatePage.getDepartureTimeInput()).to.contain('2001-01-01T02:30');
        expect(await airplaneTripUpdatePage.getArrivalTimeInput()).to.contain('2001-01-01T02:30');
        expect(await airplaneTripUpdatePage.getPriceInput()).to.eq('5');
        await airplaneTripUpdatePage.save();
        expect(await airplaneTripUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await airplaneTripComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last AirplaneTrip', async () => {
        const nbButtonsBeforeDelete = await airplaneTripComponentsPage.countDeleteButtons();
        await airplaneTripComponentsPage.clickOnLastDeleteButton();

        airplaneTripDeleteDialog = new AirplaneTripDeleteDialog();
        expect(await airplaneTripDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Airplane Trip?');
        await airplaneTripDeleteDialog.clickOnConfirmButton();

        expect(await airplaneTripComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
