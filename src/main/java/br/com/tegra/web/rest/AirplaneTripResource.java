package br.com.tegra.web.rest;

import br.com.tegra.domain.AirplaneTrip;
import br.com.tegra.domain.AirplaneTripResponse;
import br.com.tegra.service.AirplaneTripService;
import br.com.tegra.web.rest.errors.BadRequestAlertException;
import br.com.tegra.web.rest.util.HeaderUtil;
import br.com.tegra.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Airplanetrip.
 */
@RestController
@RequestMapping("/api")
public class AirplaneTripResource {

    private final Logger log = LoggerFactory.getLogger(AirplaneTripResource.class);

    private static final String ENTITY_NAME = "airplanetrip";

    private final AirplaneTripService airplanetripService;

    public AirplaneTripResource(AirplaneTripService airplanetripService) {
        this.airplanetripService = airplanetripService;
    }

    /**
     * POST  /airplanetrips : Create a new airplanetrip.
     *
     * @param airplanetrip the airplanetrip to create
     * @return the ResponseEntity with status 201 (Created) and with body the new airplanetrip, or with status 400 (Bad Request) if the airplanetrip has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/airplanetrips")
    public ResponseEntity<AirplaneTrip> createAirplanetrip(@Valid @RequestBody AirplaneTrip airplanetrip) throws URISyntaxException {
        log.debug("REST request to save Airplanetrip : {}", airplanetrip);
        if (airplanetrip.getId() != null) {
            throw new BadRequestAlertException("A new airplanetrip cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AirplaneTrip result = airplanetripService.save(airplanetrip);
        return ResponseEntity.created(new URI("/api/airplanetrips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * POST  /airplanetrips/import : Import All AirplaneTrips.
     *
     */
    @PostMapping("/airplanetrips/import")
    public ResponseEntity<Void> createAirplanetrip() throws IOException {
        log.debug("REST request to import all airline trips");
        airplanetripService.importAllFromGithub();
        return ResponseEntity.ok(null);
    }

    /**
     * PUT  /airplanetrips : Updates an existing airplanetrip.
     *
     * @param airplanetrip the airplanetrip to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated airplanetrip,
     * or with status 400 (Bad Request) if the airplanetrip is not valid,
     * or with status 500 (Internal Server Error) if the airplanetrip couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/airplanetrips")
    public ResponseEntity<AirplaneTrip> updateAirplanetrip(@Valid @RequestBody AirplaneTrip airplanetrip) throws URISyntaxException {
        log.debug("REST request to update Airplanetrip : {}", airplanetrip);
        if (airplanetrip.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AirplaneTrip result = airplanetripService.save(airplanetrip);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, airplanetrip.getId().toString()))
            .body(result);
    }

    /**
     * GET  /airplanetrips : get all the airplanetrips.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of airplanetrips in body
     */
    @GetMapping("/airplanetrips")
    public ResponseEntity<List<AirplaneTrip>> getAllAirplanetrips(Pageable pageable) {
        log.debug("REST request to get a page of Airplanetrips");
        Page<AirplaneTrip> page = airplanetripService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/airplanetrips");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /airplanetrips : get all the airplanetrips.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of airplanetrips in body
     */
    @GetMapping("/airplanetrips/{flightRange}/{departureAirport}/{arrivalAirport}/{tripDate}")
    public ResponseEntity<List<AirplaneTripResponse>> getAllAirplanetrips(Pageable pageable,
                                                                          @PathVariable() Integer flightRange,
                                                                          @PathVariable() String departureAirport,
                                                                          @PathVariable() String arrivalAirport,
                                                                          @PathVariable() @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate tripDate) {
        log.debug("REST request to get a page of Airplanetrips {}, {}, {}", departureAirport, arrivalAirport, tripDate);
        Page<AirplaneTripResponse> page = airplanetripService.findAllByAirportsAndDate(departureAirport, arrivalAirport, tripDate, flightRange.intValue(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/airplanetrips/%s/%s/%s/%s", flightRange, departureAirport, arrivalAirport, tripDate));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /airplanetrips/:id : get the "id" airplanetrip.
     *
     * @param id the id of the airplanetrip to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the airplanetrip, or with status 404 (Not Found)
     */
    @GetMapping("/airplanetrips/{id}")
    public ResponseEntity<AirplaneTrip> getAirplanetrip(@PathVariable Long id) {
        log.debug("REST request to get Airplanetrip : {}", id);
        Optional<AirplaneTrip> airplanetrip = airplanetripService.findOne(id);
        return ResponseUtil.wrapOrNotFound(airplanetrip);
    }

    /**
     * DELETE  /airplanetrips/:id : delete the "id" airplanetrip.
     *
     * @param id the id of the airplanetrip to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/airplanetrips/{id}")
    public ResponseEntity<Void> deleteAirplanetrip(@PathVariable Long id) {
        log.debug("REST request to delete Airplanetrip : {}", id);
        airplanetripService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
