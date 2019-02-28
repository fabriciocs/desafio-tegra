/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AirlineComponentsPage, AirlineDeleteDialog, AirlineUpdatePage } from './airline.page-object';

const expect = chai.expect;

describe('Airline e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let airlineUpdatePage: AirlineUpdatePage;
    let airlineComponentsPage: AirlineComponentsPage;
    let airlineDeleteDialog: AirlineDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Airlines', async () => {
        await navBarPage.goToEntity('airline');
        airlineComponentsPage = new AirlineComponentsPage();
        await browser.wait(ec.visibilityOf(airlineComponentsPage.title), 5000);
        expect(await airlineComponentsPage.getTitle()).to.eq('Airlines');
    });

    it('should load create Airline page', async () => {
        await airlineComponentsPage.clickOnCreateButton();
        airlineUpdatePage = new AirlineUpdatePage();
        expect(await airlineUpdatePage.getPageTitle()).to.eq('Create or edit a Airline');
        await airlineUpdatePage.cancel();
    });

    it('should create and save Airlines', async () => {
        const nbButtonsBeforeCreate = await airlineComponentsPage.countDeleteButtons();

        await airlineComponentsPage.clickOnCreateButton();
        await promise.all([airlineUpdatePage.setNameInput('name')]);
        expect(await airlineUpdatePage.getNameInput()).to.eq('name');
        await airlineUpdatePage.save();
        expect(await airlineUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await airlineComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Airline', async () => {
        const nbButtonsBeforeDelete = await airlineComponentsPage.countDeleteButtons();
        await airlineComponentsPage.clickOnLastDeleteButton();

        airlineDeleteDialog = new AirlineDeleteDialog();
        expect(await airlineDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Airline?');
        await airlineDeleteDialog.clickOnConfirmButton();

        expect(await airlineComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
