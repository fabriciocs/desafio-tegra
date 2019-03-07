package br.com.tegra.service;

import br.com.tegra.domain.*;
import br.com.tegra.domain.enumeration.ImportStatus;
import br.com.tegra.repository.AirplaneTripImportRepository;
import br.com.tegra.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing AirplaneTripImport.
 */
@Service
@Transactional
public class AirplaneTripImportService {

    private final Logger log = LoggerFactory.getLogger(AirplaneTripImportService.class);

    private final AirplaneTripImportRepository airplaneTripImportRepository;
    private final AirlineService airlineService;
    private final AirplaneTripService airplaneTripService;

    public AirplaneTripImportService(AirplaneTripImportRepository airplaneTripImportRepository,
                                     AirlineService airlineService,
                                     AirplaneTripService airplaneTripService) {
        this.airplaneTripImportRepository = airplaneTripImportRepository;
        this.airlineService = airlineService;
        this.airplaneTripService = airplaneTripService;
    }

    /**
     * Save a airplaneTripImport.
     *
     * @param airplaneTripImport the entity to save
     * @return the persisted entity
     */
    public AirplaneTripImport save(AirplaneTripImport airplaneTripImport) {
        log.debug("Request to save AirplaneTripImport : {}", airplaneTripImport);
        String airline = airplaneTripImport.getAirline();
        if (!airlineService.findByName(airline).isPresent()) {
            airlineService.save(new Airline().name(airline));
        }
        return airplaneTripImportRepository.save(airplaneTripImport);
    }

    /**
     * Get all the airplaneTripImports.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AirplaneTripImport> findAll(Pageable pageable) {
        log.debug("Request to get all AirplaneTripImports");
        return airplaneTripImportRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Collection<AirplaneTripImport> findAllWaiting() {
        log.debug("Request to get all AirplaneTripImports");
        return airplaneTripImportRepository.findAllByStatusIs(ImportStatus.WAITING);
    }


    /**
     * Get one airplaneTripImport by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AirplaneTripImport> findOne(Long id) {
        log.debug("Request to get AirplaneTripImport : {}", id);
        return airplaneTripImportRepository.findById(id);
    }

    /**
     * Delete the airplaneTripImport by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AirplaneTripImport : {}", id);
        airplaneTripImportRepository.deleteById(id);
    }

    public AirplaneTripImport setStatus(AirplaneTripImport airplaneTripImport, ImportStatus status) {
        return save(airplaneTripImport.status(status));
    }


    public Optional<AirplaneTripImport> importTrips(AirplaneTripImport airplaneTripImport) {
        try {
            airplaneTripImport = setStatus(airplaneTripImport, ImportStatus.PROCESSING);
            boolean hasWarning = false;
            Set<AirplaneTrip> airplaneTrips = airplaneTripService.loadAll(airplaneTripImport);
            for (AirplaneTrip airplaneTrip : airplaneTrips) {
                if (airplaneTripService.exists(airplaneTrip)) {
                    hasWarning = true;
                } else {
                    airplaneTripService.save(airplaneTrip);
                }
            }
            if (hasWarning) {
                airplaneTripImport = setStatus(airplaneTripImport, ImportStatus.WARNING);
            } else {
                airplaneTripImport = setStatus(airplaneTripImport, ImportStatus.SUCCESS);
            }

        } catch (Exception ex) {
            airplaneTripImport = setStatus(airplaneTripImport, ImportStatus.FAIL);
            //throw  ex;
        }
        return Optional.of(airplaneTripImport);
    }
}
