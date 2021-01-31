package com.sleepseek.stay;

import com.sleepseek.accomodation.Accommodation;
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
import java.util.stream.Collectors;

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
        //Join<Stay, User> users = stays.join("user");
        Join<Stay, Address> address = stays.join("address");
        Join<Stay, Accommodation> accommodations = stays.join("accommodations");
        //Join<Reservation, Accommodation> reservations = accommodations.join("reservations");


        List<Predicate> conditions = new ArrayList<>();
        if (!isNull(parameters.getName())) {
            conditions.add(builder.like(stays.get("name"), containsValue(parameters.getName())));
        }
        if (!isNull(parameters.getCity())) {
            conditions.add(builder.like(address.get("city"), containsValue(parameters.getCity())));
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
            for (StayProperty property : parameters.getProperty().stream().map(StayProperty::valueOf).collect(Collectors.toList())) {
                conditions.add(builder.isMember(property, stays.get("properties")));
            }
        }
        if (!isNull(parameters.getSleepersCapacity())) {
            conditions.add(builder.equal(accommodations.get("sleepersCapacity"), parameters.getSleepersCapacity()));
        }


        if (!isNull(parameters.getSouthWestLatitude()) && !isNull(parameters.getSouthWestLongitude()) && !isNull(parameters.getNorthEastLongitude()) && !isNull(parameters.getNorthEastLatitude())) {
            conditions.add(builder.between(address.get("longitude"), parameters.getSouthWestLongitude(), parameters.getNorthEastLongitude()));
            conditions.add(builder.between(address.get("latitude"), parameters.getSouthWestLatitude(), parameters.getNorthEastLatitude()));
        }
        if(!isNull(parameters.getOrder()) && parameters.getOrder().equals("ASC")){
            query.orderBy();
        }
        query.where(conditions.toArray(Predicate[]::new));
        TypedQuery<Stay> typedQuery = entityManager.createQuery(query.select(stays));
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
