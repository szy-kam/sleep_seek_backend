package com.sleepseek.reservation;

import com.sleepseek.accomodation.Accommodation;
import com.sleepseek.accomodation.AccommodationFacade;
import com.sleepseek.accomodation.exception.AccommodationNotFoundException;
import com.sleepseek.reservation.DTO.ReservationDTO;
import com.sleepseek.reservation.exception.ReservationConflictException;
import com.sleepseek.reservation.exception.ReservationNotFoundException;
import com.sleepseek.stay.StayFacade;
import com.sleepseek.stay.exception.StayNotFoundException;
import com.sleepseek.user.UserFacade;
import com.sleepseek.user.exception.UserNotFoundException;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationFacadeImpl implements ReservationFacade {
    private final ReservationRepositoryAdapter reservationRepository;
    private final AccommodationFacade accommodationFacade;
    private final UserFacade userFacade;
    private final StayFacade stayFacade;

    public ReservationFacadeImpl(ReservationRepositoryAdapter reservationRepository, AccommodationFacade accommodationFacade, StayFacade stayFacade, UserFacade userFacade) {
        this.reservationRepository = reservationRepository;
        this.accommodationFacade = accommodationFacade;
        this.userFacade = userFacade;
        this.stayFacade = stayFacade;
    }

    @Override
    @Transactional
    public void addReservation(Long accommodationTemplateId, ReservationDTO reservation) {
        new ReservationValidation().validateReservation(reservation, false);
        if(!accommodationFacade.accommodationTemplateExistsById(accommodationTemplateId)){
            throw new AccommodationNotFoundException(accommodationTemplateId);
        }
        ReservationStatus status;
        Accommodation accommodation = null;
        if (reservationRepository.isReservable(accommodationTemplateId, LocalDate.parse(reservation.getDateFrom()), LocalDate.parse(reservation.getDateTo()))) {
            status = ReservationStatus.PENDING;
            accommodation = reservationRepository.getReservable(accommodationTemplateId, LocalDate.parse(reservation.getDateFrom()), LocalDate.parse(reservation.getDateTo()));
        } else {
            throw new ReservationConflictException(reservation.getDateFrom(), reservation.getDateTo());
        }

        Reservation newReservation = Reservation.builder()
                .dateFrom(LocalDate.parse(reservation.getDateFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .dateTo(LocalDate.parse(reservation.getDateTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .status(status)
                .customer(Customer.builder()
                        .phoneNumber(reservation.getCustomer().getPhoneNumber())
                        .user(userFacade.getUserByUsername(reservation.getCustomer().getUsername()))
                        .fullName(reservation.getCustomer().getFullName())
                        .build())
                .build();
        if (accommodation == null) {
            reservationRepository.save(newReservation);
        } else {
            accommodationFacade.addReservation(accommodation, newReservation);
        }
    }

    @Override
    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ReservationNotFoundException(id);
        }
        reservationRepository.deleteById(id);
    }

    @Override
    public void updateReservation(ReservationDTO reservationDTO) {
        new ReservationValidation().validateReservation(reservationDTO, true);
        Reservation reservation = reservationRepository.getOne(reservationDTO.getId());
        reservation.setStatus(ReservationStatus.valueOf(reservationDTO.getStatus()));
        reservation.setCustomer(Customer.builder()
                .phoneNumber(reservationDTO.getCustomer().getPhoneNumber())
                .user(userFacade.getUserByUsername(reservationDTO.getCustomer().getUsername()))
                .fullName(reservationDTO.getCustomer().getFullName())
                .build());
        reservationRepository.save(reservation);
    }

    @Override
    public List<ReservationDTO> getReservationsByAccommodationId(Long accommodationId, PageRequest of) {
        if (accommodationFacade.accommodationTemplateExistsById(accommodationId)) {
            throw new AccommodationNotFoundException(accommodationId);
        }
        return reservationRepository.findAllByAccommodation_AccommodationTemplate_Id(accommodationId, of).stream().map(ReservationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getReservationsByStayId(Long stayId, PageRequest of) {
        if (!stayFacade.stayExists(stayId)) {
            throw new StayNotFoundException(stayId);
        }
        return reservationRepository.findAllByAccommodation_AccommodationTemplate_Stay_Id(stayId, of).stream().map(ReservationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ReservationDTO getReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ReservationNotFoundException(id);
        }
        return ReservationMapper.toDto(reservationRepository.getOne(id));
    }

    @Override
    public List<ReservationDTO> getReservationsByUsername(String username, PageRequest of) {
        if (!userFacade.userExists(username)) {
            throw new UserNotFoundException(username);
        }
        return reservationRepository.findAllByCustomer_User_Username(username, of).stream().map(ReservationMapper::toDto).collect(Collectors.toList());
    }


}
