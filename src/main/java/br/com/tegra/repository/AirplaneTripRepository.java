package br.com.tegra.repository;

import br.com.tegra.domain.AirplaneTrip;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AirplaneTrip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AirplaneTripRepository extends JpaRepository<AirplaneTrip, Long> {

}
