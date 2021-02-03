package com.sleepseek.accomodation;

import com.google.common.collect.Sets;
import com.sleepseek.accomodation.DTO.AccommodationDTO;
import com.sleepseek.accomodation.DTO.AccommodationTemplateDTO;
import com.sleepseek.accomodation.exception.AccommodationNotFoundException;
import com.sleepseek.accomodation.exception.AccommodationValidationException;
import com.sleepseek.reservation.Reservation;
import com.sleepseek.stay.Stay;
import com.sleepseek.stay.StayFacade;
import com.sleepseek.stay.exception.StayNotFoundException;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

class AccommodationFacadeImpl implements AccommodationFacade {
    private final AccommodationRepositoryAdapter accommodationRepository;
    private final StayFacade stayFacade;
    private final AccommodationTemplateRepository accommodationTemplateRepository;


    AccommodationFacadeImpl(AccommodationRepositoryAdapter accommodationRepository, AccommodationTemplateRepository accommodationTemplateRepository, StayFacade stayFacade) {
        this.accommodationRepository = accommodationRepository;
        this.stayFacade = stayFacade;
        this.accommodationTemplateRepository = accommodationTemplateRepository;

    }

    @Override
    public void addAccommodationTemplate(AccommodationTemplateDTO accommodationTemplateDTO) {
        validateAccommodation(accommodationTemplateDTO, false, true);
        Stay stay = stayFacade.loadStay(accommodationTemplateDTO.getStayId());
        AccommodationTemplate newAccommodationTemplate = AccommodationTemplate.builder()
                .price(accommodationTemplateDTO.getPrice())
                .quantity(accommodationTemplateDTO.getQuantity())
                .sleepersCapacity(accommodationTemplateDTO.getSleepersCapacity())
                .prefix(accommodationTemplateDTO.getPrefix())
                .properties(new HashSet<>())
                .accommodations(new ArrayList<>())
                .build();
        for (int i = 0; i < accommodationTemplateDTO.getQuantity(); i++) {
            newAccommodationTemplate.addAccommodation(Accommodation.builder()
                    .alias(accommodationTemplateDTO.getPrefix() + (i + 1))
                    .build());
        }
        accommodationTemplateDTO.getProperties().forEach(property -> newAccommodationTemplate.getProperties().add(AccommodationProperty.valueOf(property)));
        stayFacade.addAccommodation(stay, newAccommodationTemplate);

    }


    @Override
    public void deleteAccommodationTemplate(Long id) {
        if (!accommodationTemplateRepository.existsById(id)) {
            throw new AccommodationNotFoundException(id);
        }
        accommodationTemplateRepository.deleteById(id);
    }

    @Override
    public List<AccommodationTemplateDTO> getAccommodationTemplatesByStay(Long stayId, PageRequest of) {
        return accommodationTemplateRepository.findAllByStayId(stayId, of).stream().map(AccommodationMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public AccommodationTemplate loadAccommodationTemplateById(Long id) {
        return accommodationTemplateRepository.findById(id).orElseThrow(() -> new AccommodationNotFoundException(id));
    }

    @Override
    public Accommodation loadAccommodationById(Long id) {
        return accommodationRepository.findById(id).orElseThrow(() -> new AccommodationNotFoundException(id));
    }

    @Override
    public void updateAccommodationTemplate(AccommodationTemplateDTO accommodationTemplateDTO) {
        validateAccommodation(accommodationTemplateDTO, true, false);
        if (!accommodationTemplateRepository.existsById(accommodationTemplateDTO.getId())) {
            throw new AccommodationNotFoundException(accommodationTemplateDTO.getId());
        }
        AccommodationTemplate accommodationTemplate = loadAccommodationTemplateById(accommodationTemplateDTO.getId());
        accommodationTemplate.setPrice(accommodationTemplateDTO.getPrice());
        accommodationTemplate.setQuantity(accommodationTemplateDTO.getQuantity());
        accommodationTemplate.setSleepersCapacity(accommodationTemplateDTO.getSleepersCapacity());
        accommodationTemplate.setProperties(new HashSet<>());
        accommodationTemplate.setPrefix(accommodationTemplateDTO.getPrefix());
        accommodationTemplateDTO.getProperties().forEach(property -> accommodationTemplate.getProperties().add(AccommodationProperty.valueOf(property)));

        List<Accommodation> accommodations = accommodationTemplate.getAccommodations();
        if (accommodations == null) {
            accommodations = new ArrayList<>();
        }
        if (accommodations.size() < accommodationTemplateDTO.getQuantity()) {
            for (int i = accommodations.size(); i <= accommodationTemplateDTO.getQuantity(); i++) {
                accommodationTemplate.addAccommodation(Accommodation.builder()
                        .alias(accommodationTemplateDTO.getPrefix() + i)
                        .build());
            }
        } else if (accommodations.size() > accommodationTemplateDTO.getQuantity()) {
            for (int i = accommodations.size() - 1; i >= accommodationTemplateDTO.getQuantity(); i--) {
                accommodationTemplate.removeAccommodation(accommodationTemplate.getAccommodations().get(i));
            }
        }
        accommodationTemplateRepository.save(accommodationTemplate);
    }


    @Override
    public AccommodationTemplateDTO getAccommodationTemplate(Long id) {
        if (!accommodationTemplateRepository.existsById(id)) {
            throw new AccommodationNotFoundException(id);
        }
        return AccommodationMapper.toDTO(accommodationTemplateRepository.getOne(id));
    }

    @Override
    public List<AccommodationDTO> getAccommodations(Long accommodationTemplateId, PageRequest of) {
        if (!accommodationTemplateRepository.existsById(accommodationTemplateId)) {
            throw new AccommodationNotFoundException(accommodationTemplateId);
        }
        return accommodationRepository.findAllByAccommodationTemplate_Id(accommodationTemplateId, of).stream().map(AccommodationMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public AccommodationDTO getAccommodation(Long id) {
        if (!accommodationRepository.existsById(id)) {
            throw new AccommodationNotFoundException(id);
        }
        return AccommodationMapper.toDTO(accommodationRepository.getOne(id));
    }

    @Override
    public void updateAccommodation(AccommodationDTO accommodationDTO) {
        if (!accommodationRepository.existsById(accommodationDTO.getId())) {
            throw new AccommodationNotFoundException(accommodationDTO.getId());
        }
        if (isNull(accommodationDTO.getAlias())) {
            throw new AccommodationValidationException(Sets.newHashSet(AccommodationErrorCodes.PREFIX_NULL));
        }
        Accommodation accommodation = accommodationRepository.getOne(accommodationDTO.getId());
        accommodation.setAlias(accommodationDTO.getAlias());
        accommodationRepository.save(accommodation);
    }


    @Override
    public boolean accommodationTemplateExistsById(Long id) {
        return accommodationTemplateRepository.existsById(id);
    }

    @Override
    public void addReservation(Accommodation accommodation, Reservation newReservation) {
        accommodation.addReservation(newReservation);
        accommodationRepository.save(accommodation);
    }

    @Override
    public List<AccommodationDTO> getAccommodationsByDate(Long accommodationTemplateId, PageRequest of, String dateFrom, String dateTo) {
        return accommodationRepository.findAllReservable(accommodationTemplateId, LocalDate.parse(dateFrom), LocalDate.parse(dateTo)).stream().map(AccommodationMapper::toDTO).collect(Collectors.toList());
    }

    private void validateAccommodation(AccommodationTemplateDTO accommodationTemplateDTO, boolean shouldCheckId, boolean shouldCheckStayId) {
        new AccommodationTemplateValidator().validate(accommodationTemplateDTO, shouldCheckId, shouldCheckStayId);
        if (shouldCheckStayId && !stayFacade.stayExists(accommodationTemplateDTO.getStayId())) {
            throw new StayNotFoundException(accommodationTemplateDTO.getStayId());
        }
    }


}
