package br.com.tegra.repository;

import br.com.tegra.domain.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Repository
public class CustomAirplaneTripRepositoryImpl extends QuerydslRepositorySupport implements CustomAirplaneTripRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final QAirplaneTrip airplaneTrip;

    public CustomAirplaneTripRepositoryImpl() {
        super(AirplaneTrip.class);
        this.airplaneTrip = QAirplaneTrip.airplaneTrip;
    }


    private JPAQuery<AirplaneTrip> query() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(airplaneTrip);
    }

    public JPAQuery<AirplaneTrip> findAllByDepartureAirport(String departureAirport, LocalDate flighDate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(airplaneTrip)
            .where(
                airplaneTrip.departureAirport.airport.eq(departureAirport)
                    .and(airplaneTrip.departureDate.goe(flighDate))
            );
    }

    public JPAQuery<AirplaneTrip> findAllByArrivalAirport(String arrivalAirport, LocalDate flighDate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(airplaneTrip)
            .where(
                airplaneTrip.arrivalAirport.airport.eq(arrivalAirport)
            );
    }

    private Airport getAirportId(String airport) {
        QAirport ap = QAirport.airport1;
        JPAQueryFactory qf = new JPAQueryFactory(entityManager);
        return qf.selectFrom(ap)
            .where(ap.airport.eq(airport)).fetchFirst();

    }

    @Override
    public Page<AirplaneTripResponse> findAllByAirportsAndDate(String departureAirport, String arrivalAirport, LocalDate flighDate, int flightRange, Pageable pageable) {
        int twelveHoursInSec = 43200;
        Airport arrival = getAirportId(arrivalAirport);
        Airport departure = getAirportId(departureAirport);
        QAirplaneTrip baseTrip = new QAirplaneTrip("trip0");
        JPAQuery<AirplaneTrip> q = new JPAQueryFactory(entityManager).selectFrom(baseTrip);
        QAirplaneTrip trip = null;
        QAirplaneTrip lastTrip = null;
        BooleanExpression trueCondition = null;
        JPAQuery<Tuple> queryT = null;
        List<QAirplaneTrip> airplaneTripList = new ArrayList<>();
        airplaneTripList.add(baseTrip);

        for (int i = 0; i < flightRange; i++) {
            lastTrip = new QAirplaneTrip("trip" + i);
            trip = new QAirplaneTrip("trip" + (i + 1));
            //airplaneTripList.add(lastTrip);
            airplaneTripList.add(trip);
            if (null == trueCondition) {
                trueCondition = lastTrip.arrivalAirport
                    .id.eq(arrival.getId())
                    .and(trip.isNull());
            } else {
                trueCondition = trueCondition.or(
                    lastTrip.arrivalAirport.id.eq(arrival.getId())
                        .and(trip.isNull()));
            }

            q = q.leftJoin(trip)
                .on(lastTrip.arrivalAirport.id.eq(trip.departureAirport.id)
                    .and(Expressions.numberTemplate(Long.class,
                        "TIMESTAMPDIFF(HOUR, {0}, {1})",
                        lastTrip.arrivalTime,
                        trip.departureTime).between(1, 12))
                    .and(trip.arrivalAirport.id.eq(arrival.getId()))
                    .and(lastTrip.airline.eq(trip.airline)));
        }

        QAirplaneTrip[] paramArray = airplaneTripList.toArray(new QAirplaneTrip[]{});

        queryT = q.where(baseTrip.departureAirport.id.eq(departure.getId())
            .and(baseTrip.departureDate.eq(flighDate))
            .and(trueCondition))
            .orderBy(getOrderSpecifiers(pageable))
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .distinct()
            .select(paramArray);

        long total = queryT.fetchCount();


        List<AirplaneTripResponse> content = queryT.select(Projections.constructor(AirplaneTripResponse.class, paramArray)).fetch();
        content.forEach(System.out::println);
        return new PageImpl<>(content, pageable, total);
    }

    private OrderSpecifier[] getOrderSpecifiers(@NotNull Pageable pageable) {

        // orderVariable must match the variable of FROM

        return pageable.getSort().stream()
            .map(order -> new OrderSpecifier(
                Order.valueOf(order.getDirection().toString()),
                new PathBuilder(AirplaneTrip.class, "airplaneTrip")
                    .get(order.getProperty()))
            )
            .toArray(OrderSpecifier[]::new);
    }
}
