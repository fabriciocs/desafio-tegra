package br.com.tegra.domain;


import com.querydsl.core.annotations.QueryProjection;
import io.jsonwebtoken.lang.Collections;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AirplaneTripResponse implements Serializable {

    private static final long serialVersionUID = 1L;


    private String flight;


    private LocalDate departureDate;


    private LocalDate arrivalDate;

    private Instant departureTime;


    private Instant arrivalTime;

    private BigDecimal price;

    private String departureAirport;

    private String arrivalAirport;

    private String airline;

    private List<AirplaneTripResponse> trips = new ArrayList<>();

    public AirplaneTripResponse() {

    }

    public AirplaneTripResponse(AirplaneTrip... trips) {

        AirplaneTrip first = trips[0];
        AirplaneTrip last = trips[trips.length - 1];

        if(first != null) {
            setAirline(first.getAirline().getName());
            setDepartureAirport(first.getDepartureAirport().getAirport());
            setDepartureDate(first.getDepartureDate());
            setDepartureTime(first.getDepartureTime());
        }
        if(last != null) {
            //setArrivalAirport(last.getArrivalAirport().getAirport());
            setArrivalDate(last.getArrivalDate());
            setArrivalTime(last.getArrivalTime());
        }else{
            //setArrivalAirport(first.getArrivalAirport().getAirport());
            setArrivalDate(first.getArrivalDate());
            setArrivalTime(first.getArrivalTime());
        }

        Arrays.asList(trips).forEach(t -> {
            if (t != null) {
                setArrivalDate(t.getArrivalDate());
                setArrivalTime(t.getArrivalTime());
                setArrivalAirport(t.getArrivalAirport().getAirport());
                this.trips.add(new AirplaneTripResponse()
                    .flight(t.getFlight())
                    .airline(t.getAirline().getName())
                    .price(t.getPrice())
                    .departureDate(t.getDepartureDate())
                    .departureTime(t.getDepartureTime())
                    .departureAirport(t.getDepartureAirport().getAirport())
                    .arrivalDate(t.getArrivalDate())
                    .arrivalTime(t.getArrivalTime())
                    .arrivalAirport(t.getArrivalAirport().getAirport()));
            }
        });
    }

    public AirplaneTripResponse departureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public List<AirplaneTripResponse> getTrips() {
        return trips;
    }

    public AirplaneTripResponse setTrips(List<AirplaneTripResponse> trips) {
        this.trips = trips;
        return this;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public AirplaneTripResponse arrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public AirplaneTripResponse departureTime(Instant departureTime) {
        this.departureTime = departureTime;
        return this;
    }


    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }


    public String getFlight() {
        return flight;
    }

    public AirplaneTripResponse flight(String flight) {
        this.flight = flight;
        return this;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public AirplaneTripResponse arrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AirplaneTripResponse price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public AirplaneTripResponse departureAirport(String airport) {
        this.departureAirport = airport;
        return this;
    }

    public void setDepartureAirport(String airport) {
        this.departureAirport = airport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public AirplaneTripResponse arrivalAirport(String airport) {
        this.arrivalAirport = airport;
        return this;
    }

    public void setArrivalAirport(String airport) {
        this.arrivalAirport = airport;
    }

    public String getAirline() {
        return airline;
    }

    public AirplaneTripResponse airline(String airline) {
        this.airline = airline;
        return this;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    @Override
    public String toString() {
        return "AirplanetripResponse{" +
            " flight='" + getFlight() + "'" +
            ", departureDate='" + getDepartureDate() + "'" +
            ", arrivalDate='" + getArrivalDate() + "'" +
            ", departureTime='" + getDepartureTime() + "'" +
            ", arrivalTime='" + getArrivalTime() + "'" +
            ", price=" + getPrice() +
            ", trips=" + getTrips() +
            "}";
    }
}
