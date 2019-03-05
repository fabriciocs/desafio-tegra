package br.com.tegra.repository;

import br.com.tegra.domain.AirplaneTrip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


/**
 * Spring Data  repository for the Airplanetrip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AirplaneTripRepository extends JpaRepository<AirplaneTrip, Long>, QuerydslPredicateExecutor<AirplaneTrip>, CustomAirplaneTripRepository {

    Optional<AirplaneTrip> findByFlight(String flight);
    Optional<AirplaneTrip> findByFlightAndAirlineName(String flight, String airlineName);

    @Query("select a from AirplaneTrip a " +
        "left join a.departureAirport d " +
        "left  join a.arrivalAirport ar " +
        "where d.airport = :departureAirport " +
        "and ar.airport = :arrivalAirport " +
        "and :flightDate between a.departureDate and a.arrivalDate " +
        "order by a.departureTime ")
    Page<AirplaneTrip> findAllByAirportsAndDateDirect(@Param("departureAirport") String departureAirport,
                                                @Param("arrivalAirport") String arrivalAirport,
                                                @Param("flightDate") LocalDate flightDate,
                                                Pageable pageable);


}
