package br.com.tegra.service;

import br.com.tegra.domain.Airport;
import br.com.tegra.repository.AirportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Airport.
 */
@Service
@Transactional
public class AirportService {

    private final Logger log = LoggerFactory.getLogger(AirportService.class);

    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    /**
     * Save a airport.
     *
     * @param airport the entity to save
     * @return the persisted entity
     */
    public Airport save(Airport airport) {
        log.debug("Request to save Airport : {}", airport);
        return airportRepository.save(airport);
    }

    /**
     * Get all the airports.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Airport> findAll(Pageable pageable) {
        log.debug("Request to get all Airports");
        return airportRepository.findAll(pageable);
    }


    /**
     * Get one airport by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Airport> findOne(Long id) {
        log.debug("Request to get Airport : {}", id);
        return airportRepository.findById(id);
    }

    /**
     * Delete the airport by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Airport : {}", id);        airportRepository.deleteById(id);
    }
}
