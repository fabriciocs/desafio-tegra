package br.com.tegra.repository;

import br.com.tegra.domain.AirplaneTrip;
import br.com.tegra.domain.QAirplaneTrip;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;


@Repository
public class CustomAirplaneTripRepositoryImpl extends QuerydslRepositorySupport implements CustomAirplaneTripRepository{
    @PersistenceContext
    private EntityManager entityManager;

    public CustomAirplaneTripRepositoryImpl() {
        super(AirplaneTrip.class);
    }


    public JPAQuery<AirplaneTrip> findAllByDepartureAirport(String departureAirport, LocalDate flighDate){
        QAirplaneTrip airplaneTrip = QAirplaneTrip.airplaneTrip;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return  queryFactory.selectFrom(airplaneTrip)
            .where(
                airplaneTrip.departureAirport.airport.eq(departureAirport)
                    .and(airplaneTrip.departureDate.goe(flighDate))
            );
    }

    public JPAQuery<AirplaneTrip> findAllByArrivalAirport(String arrivalAirport, LocalDate flighDate){
        QAirplaneTrip airplaneTrip = QAirplaneTrip.airplaneTrip;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return  queryFactory.selectFrom(airplaneTrip)
            .where(
                airplaneTrip.arrivalAirport.airport.eq(arrivalAirport)
            );
    }
    @Override
    public Page<AirplaneTrip> findAllByAirportsAndDate(String departureAirport, String arrivalAirport, LocalDate flighDate, int flightRange, Pageable pageable) {
        QAirplaneTrip airplaneTrip = QAirplaneTrip.airplaneTrip;
        JPAQuery<AirplaneTrip> flights = findAllByDepartureAirport(departureAirport, flighDate);
        if(flightRange <= 0){
            flights = flights.where(airplaneTrip.arrivalAirport.airport.eq(arrivalAirport));
       }else{
            for (int i = 0; i < flightRange; i++) {

            }
        }
        return null;
    }
}
