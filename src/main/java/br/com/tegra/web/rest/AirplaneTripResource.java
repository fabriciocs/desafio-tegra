package br.com.tegra.web.rest;
import br.com.tegra.domain.AirplaneTrip;
import br.com.tegra.service.AirplaneTripService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AirplaneTrip.
 */
@RestController
@RequestMapping("/api")
public class AirplaneTripResource {

    private final Logger log = LoggerFactory.getLogger(AirplaneTripResource.class);

    private static final String ENTITY_NAME = "airplaneTrip";

    private final AirplaneTripService airplaneTripService;

    public AirplaneTripResource(AirplaneTripService airplaneTripService) {
        this.airplaneTripService = airplaneTripService;
    }

    /**
     * POST  /airplane-trips : Create a new airplaneTrip.
     *
     * @param airplaneTrip the airplaneTrip to create
     * @return the ResponseEntity with status 201 (Created) and with body the new airplaneTrip, or with status 400 (Bad Request) if the airplaneTrip has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/airplane-trips")
    public ResponseEntity<AirplaneTrip> createAirplaneTrip(@Valid @RequestBody AirplaneTrip airplaneTrip) throws URISyntaxException {
        log.debug("REST request to save AirplaneTrip : {}", airplaneTrip);
        if (airplaneTrip.getId() != null) {
            throw new BadRequestAlertException("A new airplaneTrip cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AirplaneTrip result = airplaneTripService.save(airplaneTrip);
        return ResponseEntity.created(new URI("/api/airplane-trips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /airplane-trips : Updates an existing airplaneTrip.
     *
     * @param airplaneTrip the airplaneTrip to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated airplaneTrip,
     * or with status 400 (Bad Request) if the airplaneTrip is not valid,
     * or with status 500 (Internal Server Error) if the airplaneTrip couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/airplane-trips")
    public ResponseEntity<AirplaneTrip> updateAirplaneTrip(@Valid @RequestBody AirplaneTrip airplaneTrip) throws URISyntaxException {
        log.debug("REST request to update AirplaneTrip : {}", airplaneTrip);
        if (airplaneTrip.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AirplaneTrip result = airplaneTripService.save(airplaneTrip);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, airplaneTrip.getId().toString()))
            .body(result);
    }

    /**
     * GET  /airplane-trips : get all the airplaneTrips.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of airplaneTrips in body
     */
    @GetMapping("/airplane-trips")
    public ResponseEntity<List<AirplaneTrip>> getAllAirplaneTrips(Pageable pageable) {
        log.debug("REST request to get a page of AirplaneTrips");
        Page<AirplaneTrip> page = airplaneTripService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/airplane-trips");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /airplane-trips/:id : get the "id" airplaneTrip.
     *
     * @param id the id of the airplaneTrip to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the airplaneTrip, or with status 404 (Not Found)
     */
    @GetMapping("/airplane-trips/{id}")
    public ResponseEntity<AirplaneTrip> getAirplaneTrip(@PathVariable Long id) {
        log.debug("REST request to get AirplaneTrip : {}", id);
        Optional<AirplaneTrip> airplaneTrip = airplaneTripService.findOne(id);
        return ResponseUtil.wrapOrNotFound(airplaneTrip);
    }

    /**
     * DELETE  /airplane-trips/:id : delete the "id" airplaneTrip.
     *
     * @param id the id of the airplaneTrip to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/airplane-trips/{id}")
    public ResponseEntity<Void> deleteAirplaneTrip(@PathVariable Long id) {
        log.debug("REST request to delete AirplaneTrip : {}", id);
        airplaneTripService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
