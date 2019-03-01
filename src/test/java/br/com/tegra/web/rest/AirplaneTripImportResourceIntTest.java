package br.com.tegra.web.rest;

import br.com.tegra.TesteApp;

import br.com.tegra.domain.AirplaneTripImport;
import br.com.tegra.repository.AirplaneTripImportRepository;
import br.com.tegra.service.AirplaneTripImportService;
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
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.time.*;
import java.util.List;


import static br.com.tegra.web.rest.TestUtil.sameInstant;
import static br.com.tegra.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.tegra.domain.enumeration.ImportStatus;
/**
 * Test class for the AirplaneTripImportResource REST controller.
 *
 * @see AirplaneTripImportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesteApp.class)
public class AirplaneTripImportResourceIntTest {

    private static final String DEFAULT_FILE = "AAAAAAAAAA";
    private static final String UPDATED_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_AIRLINE = "AAAAAAAAAA";
    private static final String UPDATED_AIRLINE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC).toInstant();
    private static final Instant UPDATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0).toInstant();

    private static final String DEFAULT_MIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE = "BBBBBBBBBB";

    private static final ImportStatus DEFAULT_STATUS = ImportStatus.SUCCESS;
    private static final ImportStatus UPDATED_STATUS = ImportStatus.FAIL;

    @Autowired
    private AirplaneTripImportRepository airplaneTripImportRepository;

    @Autowired
    private AirplaneTripImportService airplaneTripImportService;

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

    private MockMvc restAirplaneTripImportMockMvc;

    private AirplaneTripImport airplaneTripImport;

    @Autowired
    private HttpServletRequest request;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AirplaneTripImportResource airplaneTripImportResource = new AirplaneTripImportResource(airplaneTripImportService, request);
        this.restAirplaneTripImportMockMvc = MockMvcBuilders.standaloneSetup(airplaneTripImportResource)
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
    public static AirplaneTripImport createEntity(EntityManager em) {
        AirplaneTripImport airplaneTripImport = new AirplaneTripImport()
            .file(DEFAULT_FILE)
            .airline(DEFAULT_AIRLINE)
            .dateTime(DEFAULT_DATE_TIME)
            .mimeType(DEFAULT_MIME_TYPE)
            .status(DEFAULT_STATUS);
        return airplaneTripImport;
    }

    @Before
    public void initTest() {
        airplaneTripImport = createEntity(em);
    }

    @Test
    @Transactional
    public void createAirplaneTripImport() throws Exception {
        int databaseSizeBeforeCreate = airplaneTripImportRepository.findAll().size();

        // Create the AirplaneTripImport
        restAirplaneTripImportMockMvc.perform(post("/api/airplane-trip-imports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplaneTripImport)))
            .andExpect(status().isCreated());

        // Validate the AirplaneTripImport in the database
        List<AirplaneTripImport> airplaneTripImportList = airplaneTripImportRepository.findAll();
        assertThat(airplaneTripImportList).hasSize(databaseSizeBeforeCreate + 1);
        AirplaneTripImport testAirplaneTripImport = airplaneTripImportList.get(airplaneTripImportList.size() - 1);
        assertThat(testAirplaneTripImport.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testAirplaneTripImport.getAirline()).isEqualTo(DEFAULT_AIRLINE);
        assertThat(testAirplaneTripImport.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testAirplaneTripImport.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
        assertThat(testAirplaneTripImport.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAirplaneTripImportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = airplaneTripImportRepository.findAll().size();

        // Create the AirplaneTripImport with an existing ID
        airplaneTripImport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAirplaneTripImportMockMvc.perform(post("/api/airplane-trip-imports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplaneTripImport)))
            .andExpect(status().isBadRequest());

        // Validate the AirplaneTripImport in the database
        List<AirplaneTripImport> airplaneTripImportList = airplaneTripImportRepository.findAll();
        assertThat(airplaneTripImportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAirplaneTripImports() throws Exception {
        // Initialize the database
        airplaneTripImportRepository.saveAndFlush(airplaneTripImport);

        // Get all the airplaneTripImportList
        restAirplaneTripImportMockMvc.perform(get("/api/airplane-trip-imports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airplaneTripImport.getId().intValue())))
            .andExpect(jsonPath("$.[*].file").value(hasItem(DEFAULT_FILE.toString())))
            .andExpect(jsonPath("$.[*].airline").value(hasItem(DEFAULT_AIRLINE.toString())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(sameInstant(DEFAULT_DATE_TIME))))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getAirplaneTripImport() throws Exception {
        // Initialize the database
        airplaneTripImportRepository.saveAndFlush(airplaneTripImport);

        // Get the airplaneTripImport
        restAirplaneTripImportMockMvc.perform(get("/api/airplane-trip-imports/{id}", airplaneTripImport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(airplaneTripImport.getId().intValue()))
            .andExpect(jsonPath("$.file").value(DEFAULT_FILE.toString()))
            .andExpect(jsonPath("$.airline").value(DEFAULT_AIRLINE.toString()))
            .andExpect(jsonPath("$.dateTime").value(sameInstant(DEFAULT_DATE_TIME)))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAirplaneTripImport() throws Exception {
        // Get the airplaneTripImport
        restAirplaneTripImportMockMvc.perform(get("/api/airplane-trip-imports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAirplaneTripImport() throws Exception {
        // Initialize the database
        airplaneTripImportService.save(airplaneTripImport);

        int databaseSizeBeforeUpdate = airplaneTripImportRepository.findAll().size();

        // Update the airplaneTripImport
        AirplaneTripImport updatedAirplaneTripImport = airplaneTripImportRepository.findById(airplaneTripImport.getId()).get();
        // Disconnect from session so that the updates on updatedAirplaneTripImport are not directly saved in db
        em.detach(updatedAirplaneTripImport);
        updatedAirplaneTripImport
            .file(UPDATED_FILE)
            .airline(UPDATED_AIRLINE)
            .dateTime(UPDATED_DATE_TIME)
            .mimeType(UPDATED_MIME_TYPE)
            .status(UPDATED_STATUS);

        restAirplaneTripImportMockMvc.perform(put("/api/airplane-trip-imports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAirplaneTripImport)))
            .andExpect(status().isOk());

        // Validate the AirplaneTripImport in the database
        List<AirplaneTripImport> airplaneTripImportList = airplaneTripImportRepository.findAll();
        assertThat(airplaneTripImportList).hasSize(databaseSizeBeforeUpdate);
        AirplaneTripImport testAirplaneTripImport = airplaneTripImportList.get(airplaneTripImportList.size() - 1);
        assertThat(testAirplaneTripImport.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testAirplaneTripImport.getAirline()).isEqualTo(UPDATED_AIRLINE);
        assertThat(testAirplaneTripImport.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testAirplaneTripImport.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
        assertThat(testAirplaneTripImport.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAirplaneTripImport() throws Exception {
        int databaseSizeBeforeUpdate = airplaneTripImportRepository.findAll().size();

        // Create the AirplaneTripImport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAirplaneTripImportMockMvc.perform(put("/api/airplane-trip-imports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplaneTripImport)))
            .andExpect(status().isBadRequest());

        // Validate the AirplaneTripImport in the database
        List<AirplaneTripImport> airplaneTripImportList = airplaneTripImportRepository.findAll();
        assertThat(airplaneTripImportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAirplaneTripImport() throws Exception {
        // Initialize the database
        airplaneTripImportService.save(airplaneTripImport);

        int databaseSizeBeforeDelete = airplaneTripImportRepository.findAll().size();

        // Delete the airplaneTripImport
        restAirplaneTripImportMockMvc.perform(delete("/api/airplane-trip-imports/{id}", airplaneTripImport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AirplaneTripImport> airplaneTripImportList = airplaneTripImportRepository.findAll();
        assertThat(airplaneTripImportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AirplaneTripImport.class);
        AirplaneTripImport airplaneTripImport1 = new AirplaneTripImport();
        airplaneTripImport1.setId(1L);
        AirplaneTripImport airplaneTripImport2 = new AirplaneTripImport();
        airplaneTripImport2.setId(airplaneTripImport1.getId());
        assertThat(airplaneTripImport1).isEqualTo(airplaneTripImport2);
        airplaneTripImport2.setId(2L);
        assertThat(airplaneTripImport1).isNotEqualTo(airplaneTripImport2);
        airplaneTripImport1.setId(null);
        assertThat(airplaneTripImport1).isNotEqualTo(airplaneTripImport2);
    }
}
