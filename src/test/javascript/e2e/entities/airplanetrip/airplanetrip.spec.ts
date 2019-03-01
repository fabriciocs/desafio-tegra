/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AirplanetripComponentsPage, AirplanetripDeleteDialog, AirplanetripUpdatePage } from './airplanetrip.page-object';

const expect = chai.expect;

describe('Airplanetrip e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let airplanetripUpdatePage: AirplanetripUpdatePage;
    let airplanetripComponentsPage: AirplanetripComponentsPage;
    let airplanetripDeleteDialog: AirplanetripDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Airplanetrips', async () => {
        await navBarPage.goToEntity('airplanetrip');
        airplanetripComponentsPage = new AirplanetripComponentsPage();
        await browser.wait(ec.visibilityOf(airplanetripComponentsPage.title), 5000);
        expect(await airplanetripComponentsPage.getTitle()).to.eq('Airplanetrips');
    });

    it('should load create Airplanetrip page', async () => {
        await airplanetripComponentsPage.clickOnCreateButton();
        airplanetripUpdatePage = new AirplanetripUpdatePage();
        expect(await airplanetripUpdatePage.getPageTitle()).to.eq('Create or edit a Airplanetrip');
        await airplanetripUpdatePage.cancel();
    });

    it('should create and save Airplanetrips', async () => {
        const nbButtonsBeforeCreate = await airplanetripComponentsPage.countDeleteButtons();

        await airplanetripComponentsPage.clickOnCreateButton();
        await promise.all([
            airplanetripUpdatePage.setFlightInput('flight'),
            airplanetripUpdatePage.setDepartureDateInput('2000-12-31'),
            airplanetripUpdatePage.setArrivalDateInput('2000-12-31'),
            airplanetripUpdatePage.setDepartureTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            airplanetripUpdatePage.setArrivalTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            airplanetripUpdatePage.setPriceInput('5'),
            airplanetripUpdatePage.departureAirportSelectLastOption(),
            airplanetripUpdatePage.arrivalAirportSelectLastOption(),
            airplanetripUpdatePage.airlineSelectLastOption()
        ]);
        expect(await airplanetripUpdatePage.getFlightInput()).to.eq('flight');
        expect(await airplanetripUpdatePage.getDepartureDateInput()).to.eq('2000-12-31');
        expect(await airplanetripUpdatePage.getArrivalDateInput()).to.eq('2000-12-31');
        expect(await airplanetripUpdatePage.getDepartureTimeInput()).to.contain('2001-01-01T02:30');
        expect(await airplanetripUpdatePage.getArrivalTimeInput()).to.contain('2001-01-01T02:30');
        expect(await airplanetripUpdatePage.getPriceInput()).to.eq('5');
        await airplanetripUpdatePage.save();
        expect(await airplanetripUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await airplanetripComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Airplanetrip', async () => {
        const nbButtonsBeforeDelete = await airplanetripComponentsPage.countDeleteButtons();
        await airplanetripComponentsPage.clickOnLastDeleteButton();

        airplanetripDeleteDialog = new AirplanetripDeleteDialog();
        expect(await airplanetripDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Airplanetrip?');
        await airplanetripDeleteDialog.clickOnConfirmButton();

        expect(await airplanetripComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
