package br.com.tegra.repository;

import br.com.tegra.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Airport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByName(String name);

    Optional<Airport> findByAirport(String airport);
}
