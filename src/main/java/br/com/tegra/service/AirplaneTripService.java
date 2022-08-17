package br.com.tegra.service;

import br.com.tegra.domain.*;
import br.com.tegra.repository.AirplaneTripRepository;
import br.com.tegra.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Airplanetrip.
 */
@Service
@Transactional
public class AirplaneTripService {

    private final Logger log = LoggerFactory.getLogger(AirplaneTripService.class);

    private final AirplaneTripRepository airplaneTripRepository;
    private final AirlineService airlineService;
    private final HttpServletRequest request;
    private final AirportService airportService;


    private enum MimeTypes {
        CSV("text/csv"),
        JSON("application/json");

        private String mimeType;

        MimeTypes(String mimeType) {
            this.mimeType = mimeType;
        }

        public boolean is(String mimeType) {
            return this.mimeType.equals(mimeType);
        }

        @Override
        public String toString() {
            return "MimeTypes{" +
                "mimeType='" + mimeType + '\'' +
                '}';
        }
    }

    public AirplaneTripService(AirplaneTripRepository airplaneTripRepository,
                               AirlineService airlineService,
                               HttpServletRequest request, AirportService airportService) {
        this.airplaneTripRepository = airplaneTripRepository;
        this.airlineService = airlineService;
        this.request = request;
        this.airportService = airportService;
    }

    @Transactional(readOnly = true)
    public boolean exists(AirplaneTrip airplaneTrip) {

        QAirplaneTrip a = QAirplaneTrip.airplaneTrip;
        Predicate predicate = a.flight.eq(airplaneTrip.getFlight())
            .and(a.departureDate.eq(airplaneTrip.getDepartureDate()))
            .and(a.departureTime.eq(airplaneTrip.getDepartureTime()))
            .and(a.departureAirport.airport.eq(airplaneTrip.getDepartureAirport().getAirport()))
            .and(a.arrivalDate.eq(airplaneTrip.getArrivalDate()))
            .and(a.arrivalTime.eq(airplaneTrip.getArrivalTime()))
            .and(a.arrivalAirport.airport.eq(airplaneTrip.getArrivalAirport().getAirport()))
            .and(a.airline.name.eq(airplaneTrip.getAirline().getName()))
            .and(a.price.eq(airplaneTrip.getPrice()));

        Optional<AirplaneTrip> obj = airplaneTripRepository.findOne(predicate);
        return obj.isPresent();
    }


    /**
     * Save a airplanetrip.
     *
     * @param airplanetrip the entity to save
     * @return the persisted entity
     */
    public AirplaneTrip save(AirplaneTrip airplanetrip) {
        log.debug("Request to save Airplanetrip : {}", airplanetrip);
        return airplaneTripRepository.save(airplanetrip);
    }

    /**
     * Get all the airplanetrips.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AirplaneTrip> findAll(Pageable pageable) {
        log.debug("Request to get all Airplanetrips");
        return airplaneTripRepository.findAll(pageable);
    }


    /**
     * Get one airplanetrip by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AirplaneTrip> findOne(Long id) {
        log.debug("Request to get Airplanetrip : {}", id);
        return airplaneTripRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<AirplaneTripResponse> findAllByAirportsAndDate(String departureAirport, String arrivalAirport, LocalDate flighDate, int flighRage, Pageable pageable) {
        return airplaneTripRepository
            .findAllByAirportsAndDate(
                departureAirport,
                arrivalAirport,
                flighDate,
                flighRage, pageable);
    }

    /**
     * Delete the airplanetrip by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Airplanetrip : {}", id);
        airplaneTripRepository.deleteById(id);
    }


    public Set<AirplaneTrip> loadAll(AirplaneTripImport airplaneTripImport) throws IOException {
        String fileName = airplaneTripImport.getFile();
        String airlineName = airplaneTripImport.getAirline();

        Optional<Airline> airline = airlineService.findByName(airlineName);
        if (!airline.isPresent()) {
            String defaultMessage = String.format("Airlaine \"%s\" does not existis", airplaneTripImport.getAirline());
            String entity = "Airline";
            throw new BadRequestAlertException(defaultMessage, entity, "not found");
        }
        if (MimeTypes.CSV.is(airplaneTripImport.getMimeType())) {
            return loadFromCsv(fileName, airline.get());
        } else {
            return loadFromJson(fileName, airline.get());
        }
    }

    private ObjectReader loadJsonReader() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readerFor(AirplaneTripJson.class);
    }

    public Set<AirplaneTrip> loadFromJson(String fileName, Airline airline) throws IOException {
        log.debug("Request to save AirplaneTrip from JSON File : {}", fileName);
        File file = getFile(fileName);
        MappingIterator<AirplaneTripJson> mappingIterator = loadJsonReader().readValues(file);
        return mappingIterator.readAll().stream().map(a -> convertToAirplaneTrip(a, airline)).collect(Collectors.toSet());

    }

    public Set<AirplaneTrip> loadFromJson(URL url, Airline airline) throws IOException {
        log.debug("Request to save AirplaneTrip from JSON File : {}", url);
        MappingIterator<AirplaneTripJson> mappingIterator = loadJsonReader().readValues(url);
        return mappingIterator.readAll().stream().map(a -> convertToAirplaneTrip(a, airline)).collect(Collectors.toSet());

    }

    private File getFile(String fileName) {
        return new File(request.getServletContext().getRealPath("/upload"), fileName);
    }

    public void importAllFromGithub() throws IOException {
        String airplanes99Url = "https://raw.githubusercontent.com/tegraoss/desafio-tegra/master/99planes.json";
        String uberAirUrl = "https://raw.githubusercontent.com/tegraoss/desafio-tegra/master/uberair.csv";
        String airplanes99 = "99Airplanes";
        String uberAir = "UberAir";
        Optional<Airline> airline99 = airlineService.findByName(airplanes99);
        if (!airline99.isPresent()) {
            airline99 = Optional.of(airlineService.save(new Airline().name(airplanes99)));
        }

        Optional<Airline> airlineUberAir = airlineService.findByName(uberAir);
        if (!airlineUberAir.isPresent()) {
            airlineUberAir = Optional.of(airlineService.save(new Airline().name(uberAir)));
        }

        airportService.loadAirportsFromUrl();
        Set<AirplaneTrip> airplaneTrips = loadFromJson(new URL(airplanes99Url), airline99.get());
        airplaneTrips.addAll(loadFromCsv(new URL(uberAirUrl), airlineUberAir.get()));
        for (AirplaneTrip airplaneTrip : airplaneTrips) {
            if (!exists(airplaneTrip)) {
                save(airplaneTrip);
            }
        }
    }

    public Set<AirplaneTrip> loadFromCsv(String fileName, Airline airline) throws IOException {
        log.debug("Request to load AirplaneTrip from CSV File : {}", fileName);
        try {

            File file = getFile(fileName);
            MappingIterator<AirplaneTripCsv> readValues = loadCsvReader().readValues(file);
            return readValues.readAll().stream().map(a -> convertToAirplaneTrip(a, airline)).collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file " + fileName, e);
            throw e;
        }
    }

    private ObjectReader loadCsvReader() throws IOException {
        CsvSchema bootstrapSchema = CsvSchema.builder()
            .addColumn(new CsvSchema.Column(0, "flight"))
            .addColumn(new CsvSchema.Column(1, "departureAirport"))
            .addColumn(new CsvSchema.Column(2, "arrivalAirport"))
            .addColumn(new CsvSchema.Column(3, "departureDate"))
            //.addColumn(new CsvSchema.Column(3, "arrivalDate"))
            .addColumn(new CsvSchema.Column(4, "departureTime"))
            .addColumn(new CsvSchema.Column(5, "arrivalTime"))
            .addColumn(new CsvSchema.Column(6, "price"))
            .build().withHeader();
        CsvMapper mapper = new CsvMapper();
        //File file = getFile(fileName);
        return
            mapper.readerFor(AirplaneTripCsv.class).with(bootstrapSchema);
    }

    public Set<AirplaneTrip> loadFromCsv(URL url, Airline airline) throws IOException {
        log.debug("Request to load AirplaneTrip from CSV File : {}", url);
        try {
            MappingIterator<AirplaneTripCsv> mapper = loadCsvReader().readValues(url);
            return mapper
                .readAll()
                .stream()
                .map(a -> convertToAirplaneTrip(a, airline))
                .collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("Error occurred while loading object list from URL " + url, e);
            throw e;
        }
    }

    private AirplaneTrip convertToAirplaneTrip(AirplaneTripCsv a, Airline airline) {
        String datePattern = "yyyy-MM-dd";
        LocalDate arrivalDate = LocalDate.parse(a.getDepartureDate(), DateTimeFormatter.ofPattern(datePattern));
        Instant arrivalTime = Instant.parse(a.getDepartureDate() + "T" + a.getArrivalTime() + ":00Z");

        LocalDate departureDate = LocalDate.parse(a.getDepartureDate(), DateTimeFormatter.ofPattern(datePattern));
        Instant departureTime = Instant.parse(a.getDepartureDate() + "T" + a.getDepartureTime() + ":00Z");

        return new AirplaneTrip()
            .arrivalDate(arrivalDate)
            .arrivalTime(arrivalTime)
            .departureDate(departureDate)
            .departureTime(departureTime)
            .flight(a.getFlight())
            .airline(airline)
            .price(a.getPrice())
            .arrivalAirport(airportService.findByAirport(a.getArrivalAirport()).get())
            .departureAirport(airportService.findByAirport(a.getDepartureAirport()).get());
    }

    private AirplaneTrip convertToAirplaneTrip(AirplaneTripJson a, Airline airline) {
        String datePattern = "yyyy-MM-dd";
        LocalDate arrivalDate = LocalDate.parse(a.getDataSaida(), DateTimeFormatter.ofPattern(datePattern));
        Instant arrivalTime = Instant.parse(a.getDataSaida() + "T" + a.getChegada() + ":00Z");

        LocalDate departureDate = LocalDate.parse(a.getDataSaida(), DateTimeFormatter.ofPattern(datePattern));
        Instant departureTime = Instant.parse(a.getDataSaida() + "T" + a.getSaida() + ":00Z");

        return new AirplaneTrip()
            .arrivalDate(arrivalDate)
            .arrivalTime(arrivalTime)
            .departureDate(departureDate)
            .departureTime(departureTime)
            .flight(a.getVoo())
            .airline(airline)
            .price(a.getValor())
            .arrivalAirport(airportService.findByAirport(a.getDestino()).get())
            .departureAirport(airportService.findByAirport(a.getOrigem()).get());
    }
}
