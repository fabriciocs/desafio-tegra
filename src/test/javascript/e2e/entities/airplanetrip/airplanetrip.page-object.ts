import { element, by, ElementFinder } from 'protractor';

export class AirplanetripComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-airplanetrip div table .btn-danger'));
    title = element.all(by.css('jhi-airplanetrip div h2#page-heading span')).first();

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

export class AirplanetripUpdatePage {
    pageTitle = element(by.id('jhi-airplanetrip-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    flightInput = element(by.id('field_flight'));
    departureDateInput = element(by.id('field_departureDate'));
    arrivalDateInput = element(by.id('field_arrivalDate'));
    departureTimeInput = element(by.id('field_departureTime'));
    arrivalTimeInput = element(by.id('field_arrivalTime'));
    priceInput = element(by.id('field_price'));
    departureAirportSelect = element(by.id('field_departureAirport'));
    arrivalAirportSelect = element(by.id('field_arrivalAirport'));
    airlineSelect = element(by.id('field_airline'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setFlightInput(flight) {
        await this.flightInput.sendKeys(flight);
    }

    async getFlightInput() {
        return this.flightInput.getAttribute('value');
    }

    async setDepartureDateInput(departureDate) {
        await this.departureDateInput.sendKeys(departureDate);
    }

    async getDepartureDateInput() {
        return this.departureDateInput.getAttribute('value');
    }

    async setArrivalDateInput(arrivalDate) {
        await this.arrivalDateInput.sendKeys(arrivalDate);
    }

    async getArrivalDateInput() {
        return this.arrivalDateInput.getAttribute('value');
    }

    async setDepartureTimeInput(departureTime) {
        await this.departureTimeInput.sendKeys(departureTime);
    }

    async getDepartureTimeInput() {
        return this.departureTimeInput.getAttribute('value');
    }

    async setArrivalTimeInput(arrivalTime) {
        await this.arrivalTimeInput.sendKeys(arrivalTime);
    }

    async getArrivalTimeInput() {
        return this.arrivalTimeInput.getAttribute('value');
    }

    async setPriceInput(price) {
        await this.priceInput.sendKeys(price);
    }

    async getPriceInput() {
        return this.priceInput.getAttribute('value');
    }

    async departureAirportSelectLastOption() {
        await this.departureAirportSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async departureAirportSelectOption(option) {
        await this.departureAirportSelect.sendKeys(option);
    }

    getDepartureAirportSelect(): ElementFinder {
        return this.departureAirportSelect;
    }

    async getDepartureAirportSelectedOption() {
        return this.departureAirportSelect.element(by.css('option:checked')).getText();
    }

    async arrivalAirportSelectLastOption() {
        await this.arrivalAirportSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async arrivalAirportSelectOption(option) {
        await this.arrivalAirportSelect.sendKeys(option);
    }

    getArrivalAirportSelect(): ElementFinder {
        return this.arrivalAirportSelect;
    }

    async getArrivalAirportSelectedOption() {
        return this.arrivalAirportSelect.element(by.css('option:checked')).getText();
    }

    async airlineSelectLastOption() {
        await this.airlineSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async airlineSelectOption(option) {
        await this.airlineSelect.sendKeys(option);
    }

    getAirlineSelect(): ElementFinder {
        return this.airlineSelect;
    }

    async getAirlineSelectedOption() {
        return this.airlineSelect.element(by.css('option:checked')).getText();
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

export class AirplanetripDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-airplanetrip-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-airplanetrip'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
