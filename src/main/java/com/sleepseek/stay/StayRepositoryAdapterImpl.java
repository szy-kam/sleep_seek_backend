package com.sleepseek.stay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

class StayRepositoryAdapterImpl implements StayRepositoryAdapter {
    private final StayRepository stayRepository;
    @PersistenceContext
    private EntityManager entityManager;

    private static final String cityParam = "city";
    private static final String countryParam = "country";
    private static final String nameParam = "name";
    private static final String priceFromParam = "priceFrom";
    private static final String priceToParam = "priceTo";
    private static final String dateFromParam = "dateFrom";
    private static final String dateToParam = "dateTo";
    private static final String categoryParam = "category";

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
        StringBuilder jpqlQuery = createQuery(parameters);
        TypedQuery<Stay> query = entityManager.createQuery(jpqlQuery.toString(), Stay.class);
        applyParameters(query, parameters);
        query.setFirstResult(parameters.getPageNumber() * parameters.getPageSize());
        query.setMaxResults(parameters.getPageSize());


        return query.getResultList();
    }

    private void applyParameters(TypedQuery<Stay> query, StaySearchParameters parameters) {
        if (!isNull(parameters.getName())) {
            query.setParameter(nameParam, parameters.getName());
        }
        if (!isNull(parameters.getCity())) {
            query.setParameter(cityParam, parameters.getCity());
        }
        if (!isNull(parameters.getCountry())) {
            query.setParameter(countryParam, parameters.getCountry());
        }
        if (!isNull(parameters.getPriceFrom())) {
            query.setParameter(priceFromParam, parameters.getPriceFrom());
        }
        if (!isNull(parameters.getPriceTo())) {
            query.setParameter(priceToParam, parameters.getPriceTo());
        }
        if (!isNull(parameters.getCategory())) {
            query.setParameter(categoryParam, parameters.getCategory());
        }

    }

    private StringBuilder createQuery(StaySearchParameters parameters) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT DISTINCT s FROM Stay s INNER JOIN s.address a JOIN s.user u ");
        if (shouldAppendWhere(parameters)) {
            query.append("WHERE ");
            boolean shouldAppendAnd = false;
            shouldAppendAnd = addName(query, parameters.getName(), shouldAppendAnd);
            shouldAppendAnd = addCityParam(query, parameters.getCity(), shouldAppendAnd);
            shouldAppendAnd = addCountryParam(query, parameters.getCountry(), shouldAppendAnd);
            shouldAppendAnd = addCategoryParam(query, parameters.getCategory(), shouldAppendAnd);
            shouldAppendAnd = addPriceFromParam(query, parameters.getPriceFrom(), shouldAppendAnd);
            shouldAppendAnd = addPriceToParam(query, parameters.getPriceTo(), shouldAppendAnd);
        }
        return query;
    }

    private boolean addPriceToParam(StringBuilder query, Long priceTo, boolean shouldAppendAnd) {
        if (!isNull(priceTo)) {
            if (shouldAppendAnd) {
                query.append(" AND ");
            }
            query.append("s.minPrice < :" + priceToParam + " ");
            return true;
        }
        return shouldAppendAnd;
    }

    private boolean addPriceFromParam(StringBuilder query, Long priceFrom, boolean shouldAppendAnd) {
        if (!isNull(priceFrom)) {
            if (shouldAppendAnd) {
                query.append(" AND ");
            }
            query.append("s.minPrice < :" + priceFromParam + " ");
            return true;
        }
        return shouldAppendAnd;
    }

    private boolean addCategoryParam(StringBuilder query, String category, boolean shouldAppendAnd) {
        if (!isNull(category)) {
            if (shouldAppendAnd) {
                query.append(" AND ");
            }
            query.append("s.category LIKE %:" + categoryParam + "% ");
            return true;
        }
        return shouldAppendAnd;
    }

    private boolean addCountryParam(StringBuilder query, String country, boolean shouldAppendAnd) {
        if (!isNull(country)) {
            if (shouldAppendAnd) {
                query.append(" AND ");
            }
            query.append("a.country LIKE %:" + countryParam + "% ");
            return true;
        }
        return shouldAppendAnd;
    }

    private boolean addCityParam(StringBuilder query, String city, boolean shouldAppendAnd) {
        if (!isNull(city)) {
            if (shouldAppendAnd) {
                query.append(" AND ");
            }
            query.append("a.city LIKE %:" + cityParam + "% ");
            return true;
        }
        return shouldAppendAnd;
    }


    private boolean addName(StringBuilder query, String name, boolean shouldAppendAnd) {
        if (!isNull(name)) {
            if (shouldAppendAnd) {
                query.append(" AND ");
            }
            query.append("s.name LIKE %:" + nameParam + "% ");
            return true;
        }
        return shouldAppendAnd;
    }

    private boolean shouldAppendWhere(StaySearchParameters parameters) {
        return !(isNull(parameters.getName())
                && isNull(parameters.getCity())
                && isNull(parameters.getCountry())
                && isNull(parameters.getCategory())
                && isNull(parameters.getProperty())
                && isNull(parameters.getPriceFrom())
                && isNull(parameters.getPriceTo())
                && isNull(parameters.getLongitude())
                && isNull(parameters.getLatitude())
                && isNull(parameters.getDistance())
                && isNull(parameters.getDateFrom())
                && isNull(parameters.getDateTo()));
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
