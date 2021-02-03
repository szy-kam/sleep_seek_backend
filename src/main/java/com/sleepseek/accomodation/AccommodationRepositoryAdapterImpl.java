package com.sleepseek.accomodation;

import com.sleepseek.reservation.Reservation;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class AccommodationRepositoryAdapterImpl implements AccommodationRepositoryAdapter {
    private final AccommodationRepository repository;
    @PersistenceContext
    private EntityManager entityManager;

    AccommodationRepositoryAdapterImpl(AccommodationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Accommodation> findAllByAccommodationTemplate_Id(Long id, Pageable of) {
        return repository.findAllByAccommodationTemplate_Id(id, of);
    }

    @Override
    public List<Accommodation> findAllReservable(Long accommodationTemplateId, LocalDate from, LocalDate to) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Accommodation> query = builder.createQuery(Accommodation.class);
        Root<Accommodation> accommodations = query.from(Accommodation.class);
        Join<Accommodation, Reservation> reservations = accommodations.join("reservations");
        reservations.on(
                builder.and(
/*                        builder.or(
                                builder.between(reservations.get("dateFrom"), from, to),
                                builder.between(reservations.get("dateTo"), from, to)
                        )*/
                )
        );
        query.where(builder.equal(accommodations.get("accommodationTemplate"), accommodationTemplateId));

        Query query1 = entityManager.createQuery(
                "SELECT a.accommodationTemplate, COUNT (r) " +
                        "FROM Accommodation a " +
                        "LEFT JOIN Reservation r ON r.accommodation.id = a.id AND (r.dateFrom <= :dateTo AND :dateFrom <= r.dateTo) " +
                        "WHERE  " +
                        "a.accommodationTemplate.id = :accommodationTemplate "+
                        "GROUP BY a.accommodationTemplate ");
        query1.setParameter("dateTo", to);
        query1.setParameter("dateFrom", from);
        query1.setParameter("accommodationTemplate", accommodationTemplateId);
        List<Object[]> result = query1.getResultList();
        for (Object[] o : result) {
            AccommodationTemplate a = (AccommodationTemplate) o[0];
            System.out.println(a.getPrefix() + a.getId() + " " + o[1].toString());
            System.out.println((Long) o[1] > 0 );
        }
        return entityManager.createQuery(query.select(accommodations)).getResultList();

    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Accommodation> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Accommodation getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Accommodation save(Accommodation accommodation) {
        return repository.save(accommodation);
    }
}
