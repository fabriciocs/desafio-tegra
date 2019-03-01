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
 * Test class for the AirplanetripResource REST controller.
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
    private AirplaneTripRepository airplanetripRepository;

    @Autowired
    private AirplaneTripService airplanetripService;

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

    private MockMvc restAirplanetripMockMvc;

    private AirplaneTrip airplanetrip;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AirplaneTripResource airplanetripResource = new AirplaneTripResource(airplanetripService);
        this.restAirplanetripMockMvc = MockMvcBuilders.standaloneSetup(airplanetripResource)
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
        AirplaneTrip airplanetrip = new AirplaneTrip()
            .flight(DEFAULT_FLIGHT)
            .departureDate(DEFAULT_DEPARTURE_DATE)
            .arrivalDate(DEFAULT_ARRIVAL_DATE)
            .departureTime(DEFAULT_DEPARTURE_TIME)
            .arrivalTime(DEFAULT_ARRIVAL_TIME)
            .price(DEFAULT_PRICE);
        return airplanetrip;
    }

    @Before
    public void initTest() {
        airplanetrip = createEntity(em);
    }

    @Test
    @Transactional
    public void createAirplanetrip() throws Exception {
        int databaseSizeBeforeCreate = airplanetripRepository.findAll().size();

        // Create the Airplanetrip
        restAirplanetripMockMvc.perform(post("/api/airplanetrips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplanetrip)))
            .andExpect(status().isCreated());

        // Validate the Airplanetrip in the database
        List<AirplaneTrip> airplanetripList = airplanetripRepository.findAll();
        assertThat(airplanetripList).hasSize(databaseSizeBeforeCreate + 1);
        AirplaneTrip testAirplanetrip = airplanetripList.get(airplanetripList.size() - 1);
        assertThat(testAirplanetrip.getFlight()).isEqualTo(DEFAULT_FLIGHT);
        assertThat(testAirplanetrip.getDepartureDate()).isEqualTo(DEFAULT_DEPARTURE_DATE);
        assertThat(testAirplanetrip.getArrivalDate()).isEqualTo(DEFAULT_ARRIVAL_DATE);
        assertThat(testAirplanetrip.getDepartureTime()).isEqualTo(DEFAULT_DEPARTURE_TIME);
        assertThat(testAirplanetrip.getArrivalTime()).isEqualTo(DEFAULT_ARRIVAL_TIME);
        assertThat(testAirplanetrip.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createAirplanetripWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = airplanetripRepository.findAll().size();

        // Create the Airplanetrip with an existing ID
        airplanetrip.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAirplanetripMockMvc.perform(post("/api/airplanetrips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplanetrip)))
            .andExpect(status().isBadRequest());

        // Validate the Airplanetrip in the database
        List<AirplaneTrip> airplanetripList = airplanetripRepository.findAll();
        assertThat(airplanetripList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFlightIsRequired() throws Exception {
        int databaseSizeBeforeTest = airplanetripRepository.findAll().size();
        // set the field null
        airplanetrip.setFlight(null);

        // Create the Airplanetrip, which fails.

        restAirplanetripMockMvc.perform(post("/api/airplanetrips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplanetrip)))
            .andExpect(status().isBadRequest());

        List<AirplaneTrip> airplanetripList = airplanetripRepository.findAll();
        assertThat(airplanetripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = airplanetripRepository.findAll().size();
        // set the field null
        airplanetrip.setPrice(null);

        // Create the Airplanetrip, which fails.

        restAirplanetripMockMvc.perform(post("/api/airplanetrips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplanetrip)))
            .andExpect(status().isBadRequest());

        List<AirplaneTrip> airplanetripList = airplanetripRepository.findAll();
        assertThat(airplanetripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAirplanetrips() throws Exception {
        // Initialize the database
        airplanetripRepository.saveAndFlush(airplanetrip);

        // Get all the airplanetripList
        restAirplanetripMockMvc.perform(get("/api/airplanetrips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airplanetrip.getId().intValue())))
            .andExpect(jsonPath("$.[*].flight").value(hasItem(DEFAULT_FLIGHT.toString())))
            .andExpect(jsonPath("$.[*].departureDate").value(hasItem(DEFAULT_DEPARTURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].arrivalDate").value(hasItem(DEFAULT_ARRIVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].departureTime").value(hasItem(DEFAULT_DEPARTURE_TIME.toString())))
            .andExpect(jsonPath("$.[*].arrivalTime").value(hasItem(DEFAULT_ARRIVAL_TIME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getAirplanetrip() throws Exception {
        // Initialize the database
        airplanetripRepository.saveAndFlush(airplanetrip);

        // Get the airplanetrip
        restAirplanetripMockMvc.perform(get("/api/airplanetrips/{id}", airplanetrip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(airplanetrip.getId().intValue()))
            .andExpect(jsonPath("$.flight").value(DEFAULT_FLIGHT.toString()))
            .andExpect(jsonPath("$.departureDate").value(DEFAULT_DEPARTURE_DATE.toString()))
            .andExpect(jsonPath("$.arrivalDate").value(DEFAULT_ARRIVAL_DATE.toString()))
            .andExpect(jsonPath("$.departureTime").value(DEFAULT_DEPARTURE_TIME.toString()))
            .andExpect(jsonPath("$.arrivalTime").value(DEFAULT_ARRIVAL_TIME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAirplanetrip() throws Exception {
        // Get the airplanetrip
        restAirplanetripMockMvc.perform(get("/api/airplanetrips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAirplanetrip() throws Exception {
        // Initialize the database
        airplanetripService.save(airplanetrip);

        int databaseSizeBeforeUpdate = airplanetripRepository.findAll().size();

        // Update the airplanetrip
        AirplaneTrip updatedAirplanetrip = airplanetripRepository.findById(airplanetrip.getId()).get();
        // Disconnect from session so that the updates on updatedAirplanetrip are not directly saved in db
        em.detach(updatedAirplanetrip);
        updatedAirplanetrip
            .flight(UPDATED_FLIGHT)
            .departureDate(UPDATED_DEPARTURE_DATE)
            .arrivalDate(UPDATED_ARRIVAL_DATE)
            .departureTime(UPDATED_DEPARTURE_TIME)
            .arrivalTime(UPDATED_ARRIVAL_TIME)
            .price(UPDATED_PRICE);

        restAirplanetripMockMvc.perform(put("/api/airplanetrips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAirplanetrip)))
            .andExpect(status().isOk());

        // Validate the Airplanetrip in the database
        List<AirplaneTrip> airplanetripList = airplanetripRepository.findAll();
        assertThat(airplanetripList).hasSize(databaseSizeBeforeUpdate);
        AirplaneTrip testAirplanetrip = airplanetripList.get(airplanetripList.size() - 1);
        assertThat(testAirplanetrip.getFlight()).isEqualTo(UPDATED_FLIGHT);
        assertThat(testAirplanetrip.getDepartureDate()).isEqualTo(UPDATED_DEPARTURE_DATE);
        assertThat(testAirplanetrip.getArrivalDate()).isEqualTo(UPDATED_ARRIVAL_DATE);
        assertThat(testAirplanetrip.getDepartureTime()).isEqualTo(UPDATED_DEPARTURE_TIME);
        assertThat(testAirplanetrip.getArrivalTime()).isEqualTo(UPDATED_ARRIVAL_TIME);
        assertThat(testAirplanetrip.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingAirplanetrip() throws Exception {
        int databaseSizeBeforeUpdate = airplanetripRepository.findAll().size();

        // Create the Airplanetrip

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAirplanetripMockMvc.perform(put("/api/airplanetrips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplanetrip)))
            .andExpect(status().isBadRequest());

        // Validate the Airplanetrip in the database
        List<AirplaneTrip> airplanetripList = airplanetripRepository.findAll();
        assertThat(airplanetripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAirplanetrip() throws Exception {
        // Initialize the database
        airplanetripService.save(airplanetrip);

        int databaseSizeBeforeDelete = airplanetripRepository.findAll().size();

        // Delete the airplanetrip
        restAirplanetripMockMvc.perform(delete("/api/airplanetrips/{id}", airplanetrip.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AirplaneTrip> airplanetripList = airplanetripRepository.findAll();
        assertThat(airplanetripList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AirplaneTrip.class);
        AirplaneTrip airplanetrip1 = new AirplaneTrip();
        airplanetrip1.setId(1L);
        AirplaneTrip airplanetrip2 = new AirplaneTrip();
        airplanetrip2.setId(airplanetrip1.getId());
        assertThat(airplanetrip1).isEqualTo(airplanetrip2);
        airplanetrip2.setId(2L);
        assertThat(airplanetrip1).isNotEqualTo(airplanetrip2);
        airplanetrip1.setId(null);
        assertThat(airplanetrip1).isNotEqualTo(airplanetrip2);
    }
}
