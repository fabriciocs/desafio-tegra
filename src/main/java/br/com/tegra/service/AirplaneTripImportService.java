package br.com.tegra.service;

import br.com.tegra.domain.AirplaneTripImport;
import br.com.tegra.repository.AirplaneTripImportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing AirplaneTripImport.
 */
@Service
@Transactional
public class AirplaneTripImportService {

    private final Logger log = LoggerFactory.getLogger(AirplaneTripImportService.class);

    private final AirplaneTripImportRepository airplaneTripImportRepository;

    public AirplaneTripImportService(AirplaneTripImportRepository airplaneTripImportRepository) {
        this.airplaneTripImportRepository = airplaneTripImportRepository;
    }

    /**
     * Save a airplaneTripImport.
     *
     * @param airplaneTripImport the entity to save
     * @return the persisted entity
     */
    public AirplaneTripImport save(AirplaneTripImport airplaneTripImport) {
        log.debug("Request to save AirplaneTripImport : {}", airplaneTripImport);
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
        log.debug("Request to delete AirplaneTripImport : {}", id);        airplaneTripImportRepository.deleteById(id);
    }
}
