package com.sleepseek.stay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

class StayRepositoryAdapterImpl implements StayRepositoryAdapter {
    private final StayRepository stayRepository;
    @PersistenceContext
    private EntityManager entityManager;

    private static final String cityParam = "city";
    private static final String countryParam = "country";
    private static final String usernameParam = "username";
    private static final String nameParam = "name";
    private static final String priceFromParam = "priceFrom";
    private static final String priceToParam = "priceTo";
    private static final String dateFromParam = "dateFrom";
    private static final String dateToParam = "dateTo";

    StayRepositoryAdapterImpl(StayRepository stayRepository) {
        this.stayRepository = stayRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return stayRepository.existsById(id);
    }

    @Override
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
        if (!isNull(parameters.getUsername())) {
            query.setParameter(usernameParam, parameters.getUsername());
        }
    }

    private StringBuilder createQuery(StaySearchParameters parameters) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT s FROM stay s JOIN users u ON s.user_id = u.id ");
        if (shouldAppendWhere(parameters)) {
            query.append("WHERE ");
            boolean shouldAppendAnd = false;
            shouldAppendAnd = addName(query, parameters.getName(), shouldAppendAnd);
            shouldAppendAnd = addUsername(query, parameters.getUsername(), shouldAppendAnd);
        }
        return query;
    }

    private boolean addUsername(StringBuilder query, String username, boolean shouldAppendAnd) {
        if (!isNull(username)) {
            if (shouldAppendAnd) {
                query.append(" AND ");
            }
            query.append("u.username LIKE :" + usernameParam + " ");
            return true;
        }
        return shouldAppendAnd;
    }

    private boolean addName(StringBuilder query, String name, boolean shouldAppendAnd) {
        if (!isNull(name)) {
            if (shouldAppendAnd) {
                query.append(" AND ");
            }
            query.append("s.name LIKE :" + nameParam + " ");
            return true;
        }
        return shouldAppendAnd;
    }

    private boolean shouldAppendWhere(StaySearchParameters parameters) {
        return isNull(parameters.getName())
                && isNull(parameters.getCity())
                && isNull(parameters.getCountry())
                && isNull(parameters.getCategory())
                && isNull(parameters.getProperty())
                && isNull(parameters.getPriceFrom())
                && isNull(parameters.getPriceTo())
                && isNull(parameters.getLongitude())
                && isNull(parameters.getLatitude())
                && isNull(parameters.getDistance())
                && isNull(parameters.getUsername())
                && isNull(parameters.getDateFrom())
                && isNull(parameters.getDateTo());
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
