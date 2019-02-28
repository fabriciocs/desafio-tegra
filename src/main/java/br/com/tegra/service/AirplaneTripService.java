package br.com.tegra.service;

import br.com.tegra.domain.AirplaneTrip;
import br.com.tegra.repository.AirplaneTripRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing AirplaneTrip.
 */
@Service
@Transactional
public class AirplaneTripService {

    private final Logger log = LoggerFactory.getLogger(AirplaneTripService.class);

    private final AirplaneTripRepository airplaneTripRepository;

    public AirplaneTripService(AirplaneTripRepository airplaneTripRepository) {
        this.airplaneTripRepository = airplaneTripRepository;
    }

    /**
     * Save a airplaneTrip.
     *
     * @param airplaneTrip the entity to save
     * @return the persisted entity
     */
    public AirplaneTrip save(AirplaneTrip airplaneTrip) {
        log.debug("Request to save AirplaneTrip : {}", airplaneTrip);
        return airplaneTripRepository.save(airplaneTrip);
    }

    /**
     * Get all the airplaneTrips.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AirplaneTrip> findAll(Pageable pageable) {
        log.debug("Request to get all AirplaneTrips");
        return airplaneTripRepository.findAll(pageable);
    }


    /**
     * Get one airplaneTrip by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AirplaneTrip> findOne(Long id) {
        log.debug("Request to get AirplaneTrip : {}", id);
        return airplaneTripRepository.findById(id);
    }

    /**
     * Delete the airplaneTrip by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AirplaneTrip : {}", id);        airplaneTripRepository.deleteById(id);
    }
}
