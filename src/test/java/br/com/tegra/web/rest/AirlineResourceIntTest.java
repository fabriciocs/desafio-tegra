package br.com.tegra.web.rest;

import br.com.tegra.TesteApp;

import br.com.tegra.domain.Airline;
import br.com.tegra.repository.AirlineRepository;
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
import java.util.List;


import static br.com.tegra.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AirlineResource REST controller.
 *
 * @see AirlineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesteApp.class)
public class AirlineResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AirlineRepository airlineRepository;

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

    private MockMvc restAirlineMockMvc;

    private Airline airline;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AirlineResource airlineResource = new AirlineResource(airlineRepository);
        this.restAirlineMockMvc = MockMvcBuilders.standaloneSetup(airlineResource)
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
    public static Airline createEntity(EntityManager em) {
        Airline airline = new Airline()
            .name(DEFAULT_NAME);
        return airline;
    }

    @Before
    public void initTest() {
        airline = createEntity(em);
    }

    @Test
    @Transactional
    public void createAirline() throws Exception {
        int databaseSizeBeforeCreate = airlineRepository.findAll().size();

        // Create the Airline
        restAirlineMockMvc.perform(post("/api/airlines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airline)))
            .andExpect(status().isCreated());

        // Validate the Airline in the database
        List<Airline> airlineList = airlineRepository.findAll();
        assertThat(airlineList).hasSize(databaseSizeBeforeCreate + 1);
        Airline testAirline = airlineList.get(airlineList.size() - 1);
        assertThat(testAirline.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAirlineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = airlineRepository.findAll().size();

        // Create the Airline with an existing ID
        airline.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAirlineMockMvc.perform(post("/api/airlines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airline)))
            .andExpect(status().isBadRequest());

        // Validate the Airline in the database
        List<Airline> airlineList = airlineRepository.findAll();
        assertThat(airlineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAirlines() throws Exception {
        // Initialize the database
        airlineRepository.saveAndFlush(airline);

        // Get all the airlineList
        restAirlineMockMvc.perform(get("/api/airlines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airline.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getAirline() throws Exception {
        // Initialize the database
        airlineRepository.saveAndFlush(airline);

        // Get the airline
        restAirlineMockMvc.perform(get("/api/airlines/{id}", airline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(airline.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAirline() throws Exception {
        // Get the airline
        restAirlineMockMvc.perform(get("/api/airlines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAirline() throws Exception {
        // Initialize the database
        airlineRepository.saveAndFlush(airline);

        int databaseSizeBeforeUpdate = airlineRepository.findAll().size();

        // Update the airline
        Airline updatedAirline = airlineRepository.findById(airline.getId()).get();
        // Disconnect from session so that the updates on updatedAirline are not directly saved in db
        em.detach(updatedAirline);
        updatedAirline
            .name(UPDATED_NAME);

        restAirlineMockMvc.perform(put("/api/airlines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAirline)))
            .andExpect(status().isOk());

        // Validate the Airline in the database
        List<Airline> airlineList = airlineRepository.findAll();
        assertThat(airlineList).hasSize(databaseSizeBeforeUpdate);
        Airline testAirline = airlineList.get(airlineList.size() - 1);
        assertThat(testAirline.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAirline() throws Exception {
        int databaseSizeBeforeUpdate = airlineRepository.findAll().size();

        // Create the Airline

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAirlineMockMvc.perform(put("/api/airlines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airline)))
            .andExpect(status().isBadRequest());

        // Validate the Airline in the database
        List<Airline> airlineList = airlineRepository.findAll();
        assertThat(airlineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAirline() throws Exception {
        // Initialize the database
        airlineRepository.saveAndFlush(airline);

        int databaseSizeBeforeDelete = airlineRepository.findAll().size();

        // Delete the airline
        restAirlineMockMvc.perform(delete("/api/airlines/{id}", airline.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Airline> airlineList = airlineRepository.findAll();
        assertThat(airlineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Airline.class);
        Airline airline1 = new Airline();
        airline1.setId(1L);
        Airline airline2 = new Airline();
        airline2.setId(airline1.getId());
        assertThat(airline1).isEqualTo(airline2);
        airline2.setId(2L);
        assertThat(airline1).isNotEqualTo(airline2);
        airline1.setId(null);
        assertThat(airline1).isNotEqualTo(airline2);
    }
}
