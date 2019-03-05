package br.com.tegra.repository;

import br.com.tegra.domain.Airline;
import br.com.tegra.domain.AirplaneTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Airline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long>, QuerydslPredicateExecutor<Airline> {

}
