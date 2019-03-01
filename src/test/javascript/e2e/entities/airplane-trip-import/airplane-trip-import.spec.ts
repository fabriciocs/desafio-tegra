/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    AirplaneTripImportComponentsPage,
    AirplaneTripImportDeleteDialog,
    AirplaneTripImportUpdatePage
} from './airplane-trip-import.page-object';

const expect = chai.expect;

describe('AirplaneTripImport e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let airplaneTripImportUpdatePage: AirplaneTripImportUpdatePage;
    let airplaneTripImportComponentsPage: AirplaneTripImportComponentsPage;
    let airplaneTripImportDeleteDialog: AirplaneTripImportDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load AirplaneTripImports', async () => {
        await navBarPage.goToEntity('airplane-trip-import');
        airplaneTripImportComponentsPage = new AirplaneTripImportComponentsPage();
        await browser.wait(ec.visibilityOf(airplaneTripImportComponentsPage.title), 5000);
        expect(await airplaneTripImportComponentsPage.getTitle()).to.eq('Airplane Trip Imports');
    });

    it('should load create AirplaneTripImport page', async () => {
        await airplaneTripImportComponentsPage.clickOnCreateButton();
        airplaneTripImportUpdatePage = new AirplaneTripImportUpdatePage();
        expect(await airplaneTripImportUpdatePage.getPageTitle()).to.eq('Create or edit a Airplane Trip Import');
        await airplaneTripImportUpdatePage.cancel();
    });

    it('should create and save AirplaneTripImports', async () => {
        const nbButtonsBeforeCreate = await airplaneTripImportComponentsPage.countDeleteButtons();

        await airplaneTripImportComponentsPage.clickOnCreateButton();
        await promise.all([
            airplaneTripImportUpdatePage.setFileInput('file'),
            airplaneTripImportUpdatePage.setAirlineInput('airline'),
            airplaneTripImportUpdatePage.setDateTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            airplaneTripImportUpdatePage.setMimeTypeInput('mimeType'),
            airplaneTripImportUpdatePage.statusSelectLastOption()
        ]);
        expect(await airplaneTripImportUpdatePage.getFileInput()).to.eq('file');
        expect(await airplaneTripImportUpdatePage.getAirlineInput()).to.eq('airline');
        expect(await airplaneTripImportUpdatePage.getDateTimeInput()).to.contain('2001-01-01T02:30');
        expect(await airplaneTripImportUpdatePage.getMimeTypeInput()).to.eq('mimeType');
        await airplaneTripImportUpdatePage.save();
        expect(await airplaneTripImportUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await airplaneTripImportComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last AirplaneTripImport', async () => {
        const nbButtonsBeforeDelete = await airplaneTripImportComponentsPage.countDeleteButtons();
        await airplaneTripImportComponentsPage.clickOnLastDeleteButton();

        airplaneTripImportDeleteDialog = new AirplaneTripImportDeleteDialog();
        expect(await airplaneTripImportDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Airplane Trip Import?');
        await airplaneTripImportDeleteDialog.clickOnConfirmButton();

        expect(await airplaneTripImportComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
