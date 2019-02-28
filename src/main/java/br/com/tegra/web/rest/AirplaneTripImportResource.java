package br.com.tegra.web.rest;
import br.com.tegra.domain.AirplaneTripImport;
import br.com.tegra.service.AirplaneTripImportService;
import br.com.tegra.web.rest.errors.BadRequestAlertException;
import br.com.tegra.web.rest.util.HeaderUtil;
import br.com.tegra.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AirplaneTripImport.
 */
@RestController
@RequestMapping("/api")
public class AirplaneTripImportResource {

    private final Logger log = LoggerFactory.getLogger(AirplaneTripImportResource.class);

    private static final String ENTITY_NAME = "airplaneTripImport";

    private final AirplaneTripImportService airplaneTripImportService;

    public AirplaneTripImportResource(AirplaneTripImportService airplaneTripImportService) {
        this.airplaneTripImportService = airplaneTripImportService;
    }

    /**
     * POST  /airplane-trip-imports : Create a new airplaneTripImport.
     *
     * @param airplaneTripImport the airplaneTripImport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new airplaneTripImport, or with status 400 (Bad Request) if the airplaneTripImport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/airplane-trip-imports")
    public ResponseEntity<AirplaneTripImport> createAirplaneTripImport(@RequestBody AirplaneTripImport airplaneTripImport) throws URISyntaxException {
        log.debug("REST request to save AirplaneTripImport : {}", airplaneTripImport);
        if (airplaneTripImport.getId() != null) {
            throw new BadRequestAlertException("A new airplaneTripImport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AirplaneTripImport result = airplaneTripImportService.save(airplaneTripImport);
        return ResponseEntity.created(new URI("/api/airplane-trip-imports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /airplane-trip-imports : Updates an existing airplaneTripImport.
     *
     * @param airplaneTripImport the airplaneTripImport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated airplaneTripImport,
     * or with status 400 (Bad Request) if the airplaneTripImport is not valid,
     * or with status 500 (Internal Server Error) if the airplaneTripImport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/airplane-trip-imports")
    public ResponseEntity<AirplaneTripImport> updateAirplaneTripImport(@RequestBody AirplaneTripImport airplaneTripImport) throws URISyntaxException {
        log.debug("REST request to update AirplaneTripImport : {}", airplaneTripImport);
        if (airplaneTripImport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AirplaneTripImport result = airplaneTripImportService.save(airplaneTripImport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, airplaneTripImport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /airplane-trip-imports : get all the airplaneTripImports.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of airplaneTripImports in body
     */
    @GetMapping("/airplane-trip-imports")
    public ResponseEntity<List<AirplaneTripImport>> getAllAirplaneTripImports(Pageable pageable) {
        log.debug("REST request to get a page of AirplaneTripImports");
        Page<AirplaneTripImport> page = airplaneTripImportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/airplane-trip-imports");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /airplane-trip-imports/:id : get the "id" airplaneTripImport.
     *
     * @param id the id of the airplaneTripImport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the airplaneTripImport, or with status 404 (Not Found)
     */
    @GetMapping("/airplane-trip-imports/{id}")
    public ResponseEntity<AirplaneTripImport> getAirplaneTripImport(@PathVariable Long id) {
        log.debug("REST request to get AirplaneTripImport : {}", id);
        Optional<AirplaneTripImport> airplaneTripImport = airplaneTripImportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(airplaneTripImport);
    }

    /**
     * DELETE  /airplane-trip-imports/:id : delete the "id" airplaneTripImport.
     *
     * @param id the id of the airplaneTripImport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/airplane-trip-imports/{id}")
    public ResponseEntity<Void> deleteAirplaneTripImport(@PathVariable Long id) {
        log.debug("REST request to delete AirplaneTripImport : {}", id);
        airplaneTripImportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
