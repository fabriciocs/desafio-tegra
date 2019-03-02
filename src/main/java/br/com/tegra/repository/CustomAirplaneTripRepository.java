package br.com.tegra.repository;

import br.com.tegra.domain.AirplaneTrip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface CustomAirplaneTripRepository{
    Page<AirplaneTrip> findAllByAirportsAndDate(String departureAirport, String arrivalAirport, LocalDate flighDate, int flighRange, Pageable pageable);

}
