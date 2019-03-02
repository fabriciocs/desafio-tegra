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

/**
 * A Airplanetrip.
 */
@Entity
@Table(name = "airplane_trip")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AirplaneTrip implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 6, max = 8)
    @Pattern(regexp = "^[A-Z0-9]+$")
    @Column(name = "flight", length = 8, nullable = false)
    private String flight;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "departure_time")
    private Instant departureTime;

    @Column(name = "arrival_time")
    private Instant arrivalTime;

    @NotNull
    @DecimalMin(value = "0.00")
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JsonIgnoreProperties("airplanetrips")
    private Airport departureAirport;

    @ManyToOne
    @JsonIgnoreProperties("airplanetrips")
    private Airport arrivalAirport;

    @ManyToOne
    @JsonIgnoreProperties("airplanetrips")
    private Airline airline;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlight() {
        return flight;
    }

    public AirplaneTrip flight(String flight) {
        this.flight = flight;
        return this;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public AirplaneTrip departureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public AirplaneTrip arrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public AirplaneTrip departureTime(Instant departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public AirplaneTrip arrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AirplaneTrip price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public AirplaneTrip departureAirport(Airport airport) {
        this.departureAirport = airport;
        return this;
    }

    public void setDepartureAirport(Airport airport) {
        this.departureAirport = airport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public AirplaneTrip arrivalAirport(Airport airport) {
        this.arrivalAirport = airport;
        return this;
    }

    public void setArrivalAirport(Airport airport) {
        this.arrivalAirport = airport;
    }

    public Airline getAirline() {
        return airline;
    }

    public AirplaneTrip airline(Airline airline) {
        this.airline = airline;
        return this;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AirplaneTrip airplanetrip = (AirplaneTrip) o;
        if (airplanetrip.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), airplanetrip.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Airplanetrip{" +
            "id=" + getId() +
            ", flight='" + getFlight() + "'" +
            ", departureDate='" + getDepartureDate() + "'" +
            ", arrivalDate='" + getArrivalDate() + "'" +
            ", departureTime='" + getDepartureTime() + "'" +
            ", arrivalTime='" + getArrivalTime() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
