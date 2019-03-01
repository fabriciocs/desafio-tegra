import { element, by, ElementFinder } from 'protractor';

export class AirplaneTripImportComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-airplane-trip-import div table .btn-danger'));
    title = element.all(by.css('jhi-airplane-trip-import div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class AirplaneTripImportUpdatePage {
    pageTitle = element(by.id('jhi-airplane-trip-import-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    fileInput = element(by.id('field_file'));
    airlineInput = element(by.id('field_airline'));
    dateTimeInput = element(by.id('field_dateTime'));
    mimeTypeInput = element(by.id('field_mimeType'));
    statusSelect = element(by.id('field_status'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setFileInput(file) {
        await this.fileInput.sendKeys(file);
    }

    async getFileInput() {
        return this.fileInput.getAttribute('value');
    }

    async setAirlineInput(airline) {
        await this.airlineInput.sendKeys(airline);
    }

    async getAirlineInput() {
        return this.airlineInput.getAttribute('value');
    }

    async setDateTimeInput(dateTime) {
        await this.dateTimeInput.sendKeys(dateTime);
    }

    async getDateTimeInput() {
        return this.dateTimeInput.getAttribute('value');
    }

    async setMimeTypeInput(mimeType) {
        await this.mimeTypeInput.sendKeys(mimeType);
    }

    async getMimeTypeInput() {
        return this.mimeTypeInput.getAttribute('value');
    }

    async setStatusSelect(status) {
        await this.statusSelect.sendKeys(status);
    }

    async getStatusSelect() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    async statusSelectLastOption() {
        await this.statusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class AirplaneTripImportDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-airplaneTripImport-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-airplaneTripImport'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
