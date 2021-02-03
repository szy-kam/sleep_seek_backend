package com.sleepseek.reservation;

import com.sleepseek.accomodation.Accommodation;
import com.sleepseek.accomodation.AccommodationTemplate;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

class ReservationRepositoryAdapterImpl implements ReservationRepositoryAdapter {
    @PersistenceContext
    private EntityManager entityManager;

    private final ReservationRepository reservationRepository;

    public ReservationRepositoryAdapterImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findAllByCustomer_User_Username(String user, Pageable of) {
        return reservationRepository.findAllByCustomer_User_UsernameOrderByCreatedAtDesc(user, of);
    }

    @Override
    public List<Reservation> findAllByAccommodation_AccommodationTemplate_Id(Long accommodationTemplateId, Pageable of) {
        return reservationRepository.findAllByAccommodation_AccommodationTemplate_IdOrderByCreatedAtDesc(accommodationTemplateId, of);
    }

    @Override
    public List<Reservation> findAllByAccommodation_AccommodationTemplate_Stay_Id(Long stayId, Pageable of) {
        return reservationRepository.findAllByAccommodation_AccommodationTemplate_Stay_IdOrderByCreatedAtDesc(stayId, of);
    }

    @Override
    public Reservation save(Reservation newReservation) {
        return reservationRepository.save(newReservation);
    }

    @Override
    public boolean existsById(Long id) {
        return reservationRepository.existsById(id);
    }

    @Override
    public Reservation getOne(Long id) {
        return reservationRepository.getOne(id);
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public boolean isReservable(Long accommodationTemplateId, LocalDate dateFrom, LocalDate dateTo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Accommodation> accommodations = query.from(Accommodation.class);
        Join<Accommodation, Reservation> reservations = accommodations.join("reservations", JoinType.LEFT);
        reservations.on(cb.and(
                cb.lessThanOrEqualTo(reservations.get("dateFrom"), dateTo),
                cb.greaterThanOrEqualTo(reservations.get("dateTo"), dateFrom),
                reservations.get("status").in(Arrays.asList(ReservationStatus.PENDING, ReservationStatus.CONFIRMED))
        ));
        query.groupBy(accommodations.get("accommodationTemplate").get("id"));
        query.multiselect(accommodations.get("accommodationTemplate").get("id"), cb.count(reservations));
        query.where(cb.equal(accommodations.get("accommodationTemplate").get("id"), accommodationTemplateId));
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
        Object[] result = typedQuery.getSingleResult();
        Long id = (Long) result[0];
        Long overlappingRooms = (Long) result[1];
        CriteriaQuery<AccommodationTemplate> cq = cb.createQuery(AccommodationTemplate.class);
        Root<AccommodationTemplate> accommodations2 = cq.from(AccommodationTemplate.class);
        cq.select(accommodations2);
        cq.where(cb.equal(accommodations2.get("id"), id));
        AccommodationTemplate at =  entityManager.createQuery(cq).getSingleResult();
        /*
        Query query1 = entityManager.createQuery(
                "SELECT a.accommodationTemplate, COUNT (r) " +
                        "FROM Accommodation a " +
                        "LEFT JOIN Reservation r ON r.accommodation.id = a.id AND (r.dateFrom <= :dateTo AND :dateFrom <= r.dateTo) AND r.status IN :conflictingStatuses " +
                        "WHERE  " +
                        "a.accommodationTemplate.id = :accommodationTemplate " +
                        "GROUP BY a.accommodationTemplate ");
        query1.setParameter("dateTo", dateTo);
        query1.setParameter("dateFrom", dateFrom);
        query1.setParameter("accommodationTemplate", accommodationTemplateId);
        query1.setParameter("conflictingStatuses", Arrays.asList(ReservationStatus.PENDING, ReservationStatus.CONFIRMED));
        Object[] result = (Object[]) query1.getSingleResult();
        Long overlappingRooms = (Long) result[1];
        AccommodationTemplate accommodationTemplate = (AccommodationTemplate) result[0];
        */
        return at.getQuantity() > overlappingRooms;
    }

    @Override
    public Accommodation getReservable(Long accommodationTemplateId, LocalDate dateFrom, LocalDate dateTo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Accommodation> accommodations = query.from(Accommodation.class);
        Join<Accommodation, Reservation> reservations = accommodations.join("reservations", JoinType.LEFT);
        reservations.on(cb.and(
                cb.lessThanOrEqualTo(reservations.get("dateFrom"), dateTo),
                cb.greaterThanOrEqualTo(reservations.get("dateTo"), dateFrom),
                reservations.get("status").in(Arrays.asList(ReservationStatus.PENDING, ReservationStatus.CONFIRMED))
        ));
        query.groupBy(accommodations.get("id"));
        query.multiselect(accommodations.get("id"), cb.count(reservations));
        query.where(cb.equal(accommodations.get("accommodationTemplate").get("id"), accommodationTemplateId));
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
        /*Query query1 = entityManager.createQuery(
                "SELECT a, COUNT (r) " +
                        "FROM Accommodation a " +
                        "LEFT JOIN Reservation r ON r.accommodation.id = a.id AND (r.dateFrom <= :dateTo AND :dateFrom <= r.dateTo) AND r.status IN :conflictingStatuses " +
                        "WHERE  " +
                        "a.accommodationTemplate.id = :accommodationTemplate " +
                        "GROUP BY a "
        );
        query1.setParameter("dateTo", dateTo);
        query1.setParameter("dateFrom", dateFrom);
        query1.setParameter("accommodationTemplate", accommodationTemplateId);
        query1.setParameter("conflictingStatuses", Arrays.asList(ReservationStatus.PENDING, ReservationStatus.CONFIRMED));

         */
        List<Object[]> result = typedQuery.getResultList();
        for (Object[] r : result) {
            Long id = (Long) r[0];
            Long count = (Long) r[1];
            if (count == 0) {
                CriteriaQuery<Accommodation> cq = cb.createQuery(Accommodation.class);
                Root<Accommodation> accommodations2 = cq.from(Accommodation.class);
                cq.select(accommodations2);
                cq.where(cb.equal(accommodations2.get("id"), id));
                return entityManager.createQuery(cq).getSingleResult();
            }
        }

        return null;
    }
}
