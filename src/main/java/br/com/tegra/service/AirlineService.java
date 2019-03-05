package br.com.tegra.service;

import br.com.tegra.domain.Airline;
import br.com.tegra.domain.QAirline;
import br.com.tegra.domain.QAirplaneTrip;
import br.com.tegra.repository.AirlineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Airline.
 */
@Service
@Transactional
public class AirlineService {

    private final Logger log = LoggerFactory.getLogger(AirlineService.class);

    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    /**
     * Save a airline.
     *
     * @param airline the entity to save
     * @return the persisted entity
     */
    public Airline save(Airline airline) {
        log.debug("Request to save Airline : {}", airline);
        return airlineRepository.save(airline);
    }

    /**
     * Get all the airlines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Airline> findAll(Pageable pageable) {
        log.debug("Request to get all Airlines");
        return airlineRepository.findAll(pageable);
    }


    /**
     * Get one airline by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Airline> findOne(Long id) {
        log.debug("Request to get Airline : {}", id);
        return airlineRepository.findById(id);
    }

    /**
     * Delete the airline by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Airline : {}", id);
        airlineRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Airline> findByName(String name) {

        return airlineRepository.findOne(QAirline.airline.name.eq(name));
    }
}
