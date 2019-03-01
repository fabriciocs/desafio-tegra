package br.com.tegra.repository;

import br.com.tegra.domain.AirplaneTrip;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Airplanetrip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AirplaneTripRepository extends JpaRepository<AirplaneTrip, Long> {

    Optional<AirplaneTrip> findByFlight(String flight);
}
