package br.com.tegra.repository;

import br.com.tegra.domain.AirplaneTripResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface CustomAirplaneTripRepository{
    Page<AirplaneTripResponse> findAllByAirportsAndDate(String departureAirport, String arrivalAirport, LocalDate flighDate, int flightRange, Pageable pageable);

}
