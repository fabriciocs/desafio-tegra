package br.com.tegra.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

public class AirplaneTripCsv implements Serializable {

    private static final long serialVersionUID = 1L;
    


    private String flight;


    private String departureDate;


    private String arrivalDate;

    private String departureTime;


    private String arrivalTime;

    private BigDecimal price;

    private String departureAirport;

    private String arrivalAirport;

    private Airline airline;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public String getFlight() {
        return flight;
    }

    public AirplaneTripCsv flight(String flight) {
        this.flight = flight;
        return this;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public AirplaneTripCsv departureDate(String departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public AirplaneTripCsv arrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public AirplaneTripCsv departureTime(String departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public AirplaneTripCsv arrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AirplaneTripCsv price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public AirplaneTripCsv departureAirport(String airport) {
        this.departureAirport = airport;
        return this;
    }

    public void setDepartureAirport(String airport) {
        this.departureAirport = airport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public AirplaneTripCsv arrivalAirport(String airport) {
        this.arrivalAirport = airport;
        return this;
    }

    public void setArrivalAirport(String airport) {
        this.arrivalAirport = airport;
    }

    public Airline getAirline() {
        return airline;
    }

    public AirplaneTripCsv airline(Airline airline) {
        this.airline = airline;
        return this;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove



    @Override
    public String toString() {
        return "Airplanetrip{" +
            " flight='" + getFlight() + "'" +
            ", departureDate='" + getDepartureDate() + "'" +
            ", arrivalDate='" + getArrivalDate() + "'" +
            ", departureTime='" + getDepartureTime() + "'" +
            ", arrivalTime='" + getArrivalTime() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
