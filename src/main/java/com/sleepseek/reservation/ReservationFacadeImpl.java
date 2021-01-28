package com.sleepseek.reservation;

import com.google.common.collect.Sets;
import com.sleepseek.accomodation.Accommodation;
import com.sleepseek.accomodation.AccommodationFacade;
import com.sleepseek.accomodation.exception.AccommodationNotFoundException;
import com.sleepseek.reservation.DTO.ReservationDTO;
import com.sleepseek.reservation.exception.ReservationNotFoundException;
import com.sleepseek.reservation.exception.ReservationValidationException;
import com.sleepseek.user.UserFacade;
import com.sleepseek.user.exception.UserNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sleepseek.reservation.ReservationErrorCodes.*;
import static java.util.Objects.isNull;

public class ReservationFacadeImpl implements ReservationFacade {
    private final ReservationRepository reservationRepository;
    private final AccommodationFacade accommodationFacade;
    private final UserFacade userFacade;

    public ReservationFacadeImpl(ReservationRepository reservationRepository, AccommodationFacade accommodationFacade, UserFacade userFacade) {
        this.reservationRepository = reservationRepository;
        this.accommodationFacade = accommodationFacade;
        this.userFacade = userFacade;
    }

    @Override
    public void addReservation(ReservationDTO reservation) {
        validateReservation(reservation, false);
        Reservation newReservation = Reservation.builder()
                .completed(false)
                .confirmed(false)
                .dateFrom(LocalDate.parse(reservation.getDateFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .dateTo(LocalDate.parse(reservation.getDateTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .customer(Customer.builder()
                        .phoneNumber(reservation.getCustomer().getPhoneNumber())
                        .user(userFacade.getUserByUsername(reservation.getCustomer().getUsername()))
                        .fullName(reservation.getCustomer().getFullName())
                        .build())
                .build();
        Accommodation accommodation = accommodationFacade.loadById(reservation.getAccommodationId());
        accommodationFacade.addReservation(accommodation, newReservation);
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
        validateReservation(reservationDTO, true);
        Reservation reservation = reservationRepository.getOne(reservationDTO.getId());
        reservation.setCompleted(reservationDTO.getCompleted());
        reservation.setConfirmed(reservationDTO.getConfirmed());
        reservation.setDateFrom(LocalDate.parse(reservationDTO.getDateFrom()));
        reservation.setDateTo(LocalDate.parse(reservationDTO.getDateTo()));
        reservation.setCustomer(Customer.builder()
                .phoneNumber(reservationDTO.getCustomer().getPhoneNumber())
                .user(userFacade.getUserByUsername(reservationDTO.getCustomer().getUsername()))
                .fullName(reservationDTO.getCustomer().getFullName())
                .build());
        reservationRepository.save(reservation);
    }

    @Override
    public List<ReservationDTO> getReservationsByAccommodationId(Long accommodationId) {
        Accommodation accommodation = accommodationFacade.loadById(accommodationId);
        return accommodation.getReservations().stream().map(ReservationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ReservationDTO getReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ReservationNotFoundException(id);
        }
        return ReservationMapper.toDto(reservationRepository.getOne(id));
    }

    @Override
    public List<ReservationDTO> getReservationsByUsername(String username) {
        if (!userFacade.userExists(username)) {
            throw new UserNotFoundException(username);
        }
        return reservationRepository.findAllByCustomer_User_Username(username).stream().map(ReservationMapper::toDto).collect(Collectors.toList());
    }

    private void validateReservation(ReservationDTO reservationDTO, boolean checkId) {
        Set<ReservationErrorCodes> errors = Sets.newHashSet();
        if (checkId) {
            checkId(reservationDTO.getId()).ifPresent(errors::add);
        }
        checkDateFrom(reservationDTO.getDateFrom()).ifPresent(errors::add);
        checkDateTo(reservationDTO.getDateTo()).ifPresent(errors::add);
        checkAccommodation(reservationDTO.getAccommodationId()).ifPresent(errors::add);
        checkCustomer(reservationDTO.getCustomer()).ifPresent(errors::add);
        if (!errors.isEmpty()) {
            throw new ReservationValidationException(errors);
        }
    }

    private Optional<ReservationErrorCodes> checkAccommodation(Long accommodationId) {
        if (isNull(accommodationId)) {
            return Optional.of(ACCOMMODATION_NULL);
        }
        if (!accommodationFacade.existsById(accommodationId)) {
            throw new AccommodationNotFoundException(accommodationId);
        }
        return Optional.empty();
    }

    private Optional<ReservationErrorCodes> checkCustomer(ReservationDTO.CustomerDTO customer) {
        if (isNull(customer)) {
            return Optional.of(CUSTOMER_NULL);
        }
        if (isNull(customer.getUsername())) {
            return Optional.of(CUSTOMER_NULL);
        }
        if (!userFacade.userExists(customer.getUsername())) {
            throw new UserNotFoundException(customer.getUsername());
        }
        return Optional.empty();
    }

    private Optional<ReservationErrorCodes> checkDateFrom(String dateFrom) {
        if (isNull(dateFrom)) {
            return Optional.of(DATE_FROM_NULL);
        }
        return Optional.empty();
    }

    private Optional<ReservationErrorCodes> checkDateTo(String dateTo) {
        if (isNull(dateTo)) {
            return Optional.of(DATE_TO_NULL);
        }
        return Optional.empty();
    }

    private Optional<ReservationErrorCodes> checkId(Long id) {
        if (isNull(id)) {
            return Optional.of(ID_NULL);
        }
        return Optional.empty();
    }

}
