package br.com.tegra.web.rest;

import br.com.tegra.TesteApp;

import br.com.tegra.domain.AirplaneTrip;
import br.com.tegra.repository.AirplaneTripRepository;
import br.com.tegra.service.AirplaneTripService;
import br.com.tegra.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static br.com.tegra.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AirplaneTripResource REST controller.
 *
 * @see AirplaneTripResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesteApp.class)
public class AirplaneTripResourceIntTest {

    private static final String DEFAULT_FLIGHT = "AAAAAAAA";
    private static final String UPDATED_FLIGHT = "BBBBBBBB";

    private static final LocalDate DEFAULT_DEPARTURE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEPARTURE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ARRIVAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ARRIVAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_DEPARTURE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEPARTURE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ARRIVAL_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ARRIVAL_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0.00);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);

    @Autowired
    private AirplaneTripRepository airplaneTripRepository;

    @Autowired
    private AirplaneTripService airplaneTripService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAirplaneTripMockMvc;

    private AirplaneTrip airplaneTrip;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AirplaneTripResource airplaneTripResource = new AirplaneTripResource(airplaneTripService);
        this.restAirplaneTripMockMvc = MockMvcBuilders.standaloneSetup(airplaneTripResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AirplaneTrip createEntity(EntityManager em) {
        AirplaneTrip airplaneTrip = new AirplaneTrip()
            .flight(DEFAULT_FLIGHT)
            .departureDate(DEFAULT_DEPARTURE_DATE)
            .arrivalDate(DEFAULT_ARRIVAL_DATE)
            .departureTime(DEFAULT_DEPARTURE_TIME)
            .arrivalTime(DEFAULT_ARRIVAL_TIME)
            .price(DEFAULT_PRICE);
        return airplaneTrip;
    }

    @Before
    public void initTest() {
        airplaneTrip = createEntity(em);
    }

    @Test
    @Transactional
    public void createAirplaneTrip() throws Exception {
        int databaseSizeBeforeCreate = airplaneTripRepository.findAll().size();

        // Create the AirplaneTrip
        restAirplaneTripMockMvc.perform(post("/api/airplane-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplaneTrip)))
            .andExpect(status().isCreated());

        // Validate the AirplaneTrip in the database
        List<AirplaneTrip> airplaneTripList = airplaneTripRepository.findAll();
        assertThat(airplaneTripList).hasSize(databaseSizeBeforeCreate + 1);
        AirplaneTrip testAirplaneTrip = airplaneTripList.get(airplaneTripList.size() - 1);
        assertThat(testAirplaneTrip.getFlight()).isEqualTo(DEFAULT_FLIGHT);
        assertThat(testAirplaneTrip.getDepartureDate()).isEqualTo(DEFAULT_DEPARTURE_DATE);
        assertThat(testAirplaneTrip.getArrivalDate()).isEqualTo(DEFAULT_ARRIVAL_DATE);
        assertThat(testAirplaneTrip.getDepartureTime()).isEqualTo(DEFAULT_DEPARTURE_TIME);
        assertThat(testAirplaneTrip.getArrivalTime()).isEqualTo(DEFAULT_ARRIVAL_TIME);
        assertThat(testAirplaneTrip.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createAirplaneTripWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = airplaneTripRepository.findAll().size();

        // Create the AirplaneTrip with an existing ID
        airplaneTrip.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAirplaneTripMockMvc.perform(post("/api/airplane-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplaneTrip)))
            .andExpect(status().isBadRequest());

        // Validate the AirplaneTrip in the database
        List<AirplaneTrip> airplaneTripList = airplaneTripRepository.findAll();
        assertThat(airplaneTripList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFlightIsRequired() throws Exception {
        int databaseSizeBeforeTest = airplaneTripRepository.findAll().size();
        // set the field null
        airplaneTrip.setFlight(null);

        // Create the AirplaneTrip, which fails.

        restAirplaneTripMockMvc.perform(post("/api/airplane-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplaneTrip)))
            .andExpect(status().isBadRequest());

        List<AirplaneTrip> airplaneTripList = airplaneTripRepository.findAll();
        assertThat(airplaneTripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = airplaneTripRepository.findAll().size();
        // set the field null
        airplaneTrip.setPrice(null);

        // Create the AirplaneTrip, which fails.

        restAirplaneTripMockMvc.perform(post("/api/airplane-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplaneTrip)))
            .andExpect(status().isBadRequest());

        List<AirplaneTrip> airplaneTripList = airplaneTripRepository.findAll();
        assertThat(airplaneTripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAirplaneTrips() throws Exception {
        // Initialize the database
        airplaneTripRepository.saveAndFlush(airplaneTrip);

        // Get all the airplaneTripList
        restAirplaneTripMockMvc.perform(get("/api/airplane-trips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airplaneTrip.getId().intValue())))
            .andExpect(jsonPath("$.[*].flight").value(hasItem(DEFAULT_FLIGHT.toString())))
            .andExpect(jsonPath("$.[*].departureDate").value(hasItem(DEFAULT_DEPARTURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].arrivalDate").value(hasItem(DEFAULT_ARRIVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].departureTime").value(hasItem(DEFAULT_DEPARTURE_TIME.toString())))
            .andExpect(jsonPath("$.[*].arrivalTime").value(hasItem(DEFAULT_ARRIVAL_TIME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getAirplaneTrip() throws Exception {
        // Initialize the database
        airplaneTripRepository.saveAndFlush(airplaneTrip);

        // Get the airplaneTrip
        restAirplaneTripMockMvc.perform(get("/api/airplane-trips/{id}", airplaneTrip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(airplaneTrip.getId().intValue()))
            .andExpect(jsonPath("$.flight").value(DEFAULT_FLIGHT.toString()))
            .andExpect(jsonPath("$.departureDate").value(DEFAULT_DEPARTURE_DATE.toString()))
            .andExpect(jsonPath("$.arrivalDate").value(DEFAULT_ARRIVAL_DATE.toString()))
            .andExpect(jsonPath("$.departureTime").value(DEFAULT_DEPARTURE_TIME.toString()))
            .andExpect(jsonPath("$.arrivalTime").value(DEFAULT_ARRIVAL_TIME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAirplaneTrip() throws Exception {
        // Get the airplaneTrip
        restAirplaneTripMockMvc.perform(get("/api/airplane-trips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAirplaneTrip() throws Exception {
        // Initialize the database
        airplaneTripService.save(airplaneTrip);

        int databaseSizeBeforeUpdate = airplaneTripRepository.findAll().size();

        // Update the airplaneTrip
        AirplaneTrip updatedAirplaneTrip = airplaneTripRepository.findById(airplaneTrip.getId()).get();
        // Disconnect from session so that the updates on updatedAirplaneTrip are not directly saved in db
        em.detach(updatedAirplaneTrip);
        updatedAirplaneTrip
            .flight(UPDATED_FLIGHT)
            .departureDate(UPDATED_DEPARTURE_DATE)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .departureTime(UPDATED_DEPARTURE_TIME)
            .arrivalTime(UPDATED_ARRIVAL_TIME)
            .price(UPDATED_PRICE);

        restAirplaneTripMockMvc.perform(put("/api/airplane-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAirplaneTrip)))
            .andExpect(status().isOk());

        // Validate the AirplaneTrip in the database
        List<AirplaneTrip> airplaneTripList = airplaneTripRepository.findAll();
        assertThat(airplaneTripList).hasSize(databaseSizeBeforeUpdate);
        AirplaneTrip testAirplaneTrip = airplaneTripList.get(airplaneTripList.size() - 1);
        assertThat(testAirplaneTrip.getFlight()).isEqualTo(UPDATED_FLIGHT);
        assertThat(testAirplaneTrip.getDepartureDate()).isEqualTo(UPDATED_DEPARTURE_DATE);
        assertThat(testAirplaneTrip.getArrivalDate()).isEqualTo(UPDATED_ARRIVAL_DATE);
        assertThat(testAirplaneTrip.getDepartureTime()).isEqualTo(UPDATED_DEPARTURE_TIME);
        assertThat(testAirplaneTrip.getArrivalTime()).isEqualTo(UPDATED_ARRIVAL_TIME);
        assertThat(testAirplaneTrip.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingAirplaneTrip() throws Exception {
        int databaseSizeBeforeUpdate = airplaneTripRepository.findAll().size();

        // Create the AirplaneTrip

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAirplaneTripMockMvc.perform(put("/api/airplane-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplaneTrip)))
            .andExpect(status().isBadRequest());

        // Validate the AirplaneTrip in the database
        List<AirplaneTrip> airplaneTripList = airplaneTripRepository.findAll();
        assertThat(airplaneTripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAirplaneTrip() throws Exception {
        // Initialize the database
        airplaneTripService.save(airplaneTrip);

        int databaseSizeBeforeDelete = airplaneTripRepository.findAll().size();

        // Delete the airplaneTrip
        restAirplaneTripMockMvc.perform(delete("/api/airplane-trips/{id}", airplaneTrip.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AirplaneTrip> airplaneTripList = airplaneTripRepository.findAll();
        assertThat(airplaneTripList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AirplaneTrip.class);
        AirplaneTrip airplaneTrip1 = new AirplaneTrip();
        airplaneTrip1.setId(1L);
        AirplaneTrip airplaneTrip2 = new AirplaneTrip();
        airplaneTrip2.setId(airplaneTrip1.getId());
        assertThat(airplaneTrip1).isEqualTo(airplaneTrip2);
        airplaneTrip2.setId(2L);
        assertThat(airplaneTrip1).isNotEqualTo(airplaneTrip2);
        airplaneTrip1.setId(null);
        assertThat(airplaneTrip1).isNotEqualTo(airplaneTrip2);
    }
}
