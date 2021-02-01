package com.sleepseek.stay;

import com.sleepseek.accomodation.AccommodationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

class StayRepositoryAdapterImpl implements StayRepositoryAdapter {
    private final StayRepository stayRepository;
    @PersistenceContext
    private EntityManager entityManager;

    StayRepositoryAdapterImpl(StayRepository stayRepository) {
        this.stayRepository = stayRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return stayRepository.existsById(id);
    }

    @Override
    @Transactional
    public List<Stay> findAllByParameters(StaySearchParameters parameters) {
        new StaySearchParametersValidator().validateSearchParameters(parameters);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stay> query = builder.createQuery(Stay.class);
        Root<Stay> stays = query.from(Stay.class);
        //Root<Review> reviews = query.from(Review.class);
        //Join<Stay, User> users = stays.join("user");
        Join<Stay, Address> address = stays.join("address");
        Join<Stay, AccommodationTemplate> accommodations = stays.join("accommodationTemplates", JoinType.LEFT);
        //Join<Reservation, Accommodation> reservations = accommodations.join("reservations");


        List<Predicate> conditions = new ArrayList<>();
        if (!isNull(parameters.getName())) {
            conditions.add(builder.like(stays.get("name"), containsValue(parameters.getName())));
        }

        if (!isNull(parameters.getCountry())) {
            conditions.add(builder.like(address.get("country"), containsValue(parameters.getCountry())));
        }
        if (!isNull(parameters.getCategory())) {
            conditions.add(builder.equal(stays.get("category"), StayCategory.valueOf(parameters.getCategory())));
        }
        if (!isNull(parameters.getPriceFrom())) {
            conditions.add(builder.greaterThanOrEqualTo(stays.get("minPrice"), parameters.getPriceFrom()));
        }
        if (!isNull(parameters.getPriceTo())) {
            conditions.add(builder.lessThanOrEqualTo(stays.get("minPrice"), parameters.getPriceTo()));
        }
        if (!isNull(parameters.getProperty())) {
            parameters.getProperty().stream().map(StayProperty::valueOf).forEach(stayProperty -> conditions.add(builder.isMember(stayProperty, stays.get("properties"))));
        }
        if (!isNull(parameters.getSleepersCapacity())) {
            conditions.add(builder.equal(accommodations.get("sleepersCapacity"), parameters.getSleepersCapacity()));
        }
        if (!isNull(parameters.getCity()) && !isNull(parameters.getSouthWestLatitude()) && !isNull(parameters.getSouthWestLongitude()) && !isNull(parameters.getNorthEastLongitude()) && !isNull(parameters.getNorthEastLatitude())) {
            conditions.add(builder.or(builder.and(
                    builder.between(address.get("longitude"), parameters.getSouthWestLongitude(), parameters.getNorthEastLongitude()),
                    builder.between(address.get("latitude"), parameters.getSouthWestLatitude(), parameters.getNorthEastLatitude())
            ), builder.like(address.get("city"), containsValue(parameters.getCity()))));
        }
        if (!isNull(parameters.getCity())) {
            conditions.add(builder.like(address.get("city"), containsValue(parameters.getCity())));
        }
        if (!isNull(parameters.getSouthWestLatitude()) && !isNull(parameters.getSouthWestLongitude()) && !isNull(parameters.getNorthEastLongitude()) && !isNull(parameters.getNorthEastLatitude())) {
            conditions.add(builder.between(address.get("longitude"), parameters.getSouthWestLongitude(), parameters.getNorthEastLongitude()));
            conditions.add(builder.between(address.get("latitude"), parameters.getSouthWestLatitude(), parameters.getNorthEastLatitude()));
        }
        if (!isNull(parameters.getOrder()) && !isNull(parameters.getOrderBy())) {

            String attribute = parameters.getOrderBy();
            Expression<Stay> expression;
            switch (attribute) {
                case "name":
                    expression = stays.get("name");
                    break;
                case "city":
                    expression = address.get("city");
                    break;
                case "avgRate":
                    expression = null;
                    break;
                default:
                    expression = stays.get("createdAt");
                    break;
            }
            if (parameters.getOrder().equals("ASC")) {
                query.orderBy(builder.asc(expression));
            } else {
                query.orderBy(builder.desc(expression));
            }
        } else {
            query.orderBy(builder.desc(stays.get("createdAt")));
        }
        //query.distinct(true);
        //query.where(conditions.toArray(Predicate[]::new));
        TypedQuery<Stay> typedQuery = entityManager.createQuery(query.select(stays));
       /* Query queryTotal = entityManager.createQuery
                ("Select count(s.id) from Stay s");
        long maxRows = (long) queryTotal.getSingleResult();
        if (maxRows < (long) parameters.getPageNumber() * (long) parameters.getPageSize()) {
            return Lists.newArrayList();
        }*/
        typedQuery.setFirstResult(parameters.getPageNumber() * parameters.getPageSize());
        typedQuery.setMaxResults(parameters.getPageSize());

        return typedQuery.getResultList();
    }

    private String containsValue(String value) {
        return '%' + value + '%';
    }

    @Override
    public Page<Stay> findAllByUser_Username(String username, Pageable pageable) {
        return stayRepository.findAllByUser_Username(username, pageable);
    }

    @Override
    public Stay save(Stay stay) {
        return stayRepository.save(stay);
    }

    @Override
    public Optional<Stay> findById(Long id) {
        return stayRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        stayRepository.deleteById(id);
    }
}
