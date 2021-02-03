package com.sleepseek.reservation;

import com.sleepseek.accomodation.Accommodation;
import com.sleepseek.accomodation.AccommodationTemplate;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ReservationRepositoryAdapterImpl implements ReservationRepositoryAdapter {
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
        return accommodationTemplate.getQuantity() > overlappingRooms;
    }

    @Override
    public Accommodation getReservable(Long accommodationTemplateId, LocalDate dateFrom, LocalDate dateTo) {
        Query query1 = entityManager.createQuery(
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
        List<Object[]> result = query1.getResultList();
        for (Object[] r : result) {
            Accommodation a = (Accommodation) r[0];
            Long count = (Long) r[1];
            if (count == 0) {
                return a;
            }
        }

        return null;
    }
}
