package com.sleepseek.accomodation;

import com.google.common.collect.Sets;
import com.sleepseek.accomodation.DTO.AccommodationDTO;
import com.sleepseek.accomodation.exception.AccommodationNotFoundException;
import com.sleepseek.accomodation.exception.AccommodationPropertyNotFound;
import com.sleepseek.accomodation.exception.AccommodationValidationException;
import com.sleepseek.stay.StayFacade;
import com.sleepseek.stay.exception.StayNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sleepseek.accomodation.AccommodationErrorCodes.*;
import static java.util.Objects.isNull;

class AccommodationFacadeImpl implements AccommodationFacade {
    private final AccommodationRepository accommodationRepository;
    private final AccommodationPropertyRepository propertyRepository;
    private final StayFacade stayFacade;

    AccommodationFacadeImpl(AccommodationRepository accommodationRepository, AccommodationPropertyRepository propertyRepository, StayFacade stayFacade) {
        this.accommodationRepository = accommodationRepository;
        this.propertyRepository = propertyRepository;
        this.stayFacade = stayFacade;
    }

    @Override
    public AccommodationDTO addAccommodation(AccommodationDTO accommodationDTO) {
        validateAccommodation(accommodationDTO, false);
        Accommodation newAccommodation = accommodationRepository.save(Accommodation.builder()
                .price(accommodationDTO.getPrice())
                .quantity(accommodationDTO.getQuantity())
                .sleepersCapacity(accommodationDTO.getSleepersCapacity())
                .stay(stayFacade.loadStay(accommodationDTO.getStayId()))
                .properties(propertyRepository.saveAll(accommodationDTO.getProperties().stream().map(property -> AccommodationProperty.builder().name(property).build()).collect(Collectors.toList())))
                .build());

        return AccommodationMapper.toDTO(accommodationRepository.getOne(newAccommodation.getId()));
    }

    private Optional<AccommodationErrorCodes> checkSleepersCapacity(Long sleepersCapacity) {
        if (isNull(sleepersCapacity)) {
            return Optional.of(SLEEPERS_CAP_NULL);
        }
        if (sleepersCapacity < 0) {
            return Optional.of(SLEEPERS_CAP_BOUNDARIES);
        }
        return Optional.empty();
    }

    private Optional<AccommodationErrorCodes> checkPrice(Long price) {
        if (isNull(price)) {
            return Optional.of(PRICE_NULL);
        }
        if (price < 0L) {
            return Optional.of(PRICE_OUT_OF_BOUNDS);
        }
        return Optional.empty();
    }

    private Optional<AccommodationErrorCodes> checkQuantity(Long quantity) {
        if (isNull(quantity)) {
            return Optional.of(QUANTITY_NULL);
        }
        if (quantity < 0) {
            return Optional.of(QUANTITY_BOUNDARIES);
        }
        return Optional.empty();

    }

    private Optional<AccommodationErrorCodes> checkStay(Long stayId) {
        if (isNull(stayId)) {
            return Optional.of(STAY_NULL);
        }
        return Optional.empty();
    }

    @Override
    public void deleteAccommodation(Long id) {
        if (!accommodationRepository.existsById(id)) {
            throw new AccommodationNotFoundException(id);
        }
        accommodationRepository.deleteById(id);
    }

    @Override
    public List<AccommodationDTO> getAccommodationsByStay(Long stayId) {
        return accommodationRepository.findAccommodationsByStay(stayFacade.loadStay(stayId)).stream().map(AccommodationMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Accommodation loadById(Long id) {
        return accommodationRepository.findById(id).orElseThrow(() -> new AccommodationNotFoundException(id));
    }

    @Override
    public AccommodationDTO updateAccommodation(AccommodationDTO accommodationDTO) {
        validateAccommodation(accommodationDTO, true);
        if (!accommodationRepository.existsById(accommodationDTO.getId())) {
            throw new AccommodationNotFoundException(accommodationDTO.getId());
        }
        Accommodation accommodation = loadById(accommodationDTO.getId());
        accommodation.setPrice(accommodation.getPrice());
        accommodation.setStay(stayFacade.loadStay(accommodationDTO.getStayId()));
        accommodation.setQuantity(accommodationDTO.getQuantity());
        accommodation.setSleepersCapacity(accommodationDTO.getSleepersCapacity());
        accommodation.setProperties(propertyRepository.saveAll(accommodationDTO.getProperties().stream().map(property -> AccommodationProperty.builder().name(property).build()).collect(Collectors.toList())));
        accommodationRepository.save(accommodation);
        return AccommodationMapper.toDTO(accommodation);
    }

    private void validateAccommodation(AccommodationDTO accommodationDTO, boolean shouldCheckId) {
        Set<AccommodationErrorCodes> errors = Sets.newHashSet();
        if (shouldCheckId) {
            checkId(accommodationDTO.getId()).ifPresent(errors::add);
        }
        checkStay(accommodationDTO.getStayId()).ifPresent(errors::add);
        checkQuantity(accommodationDTO.getQuantity()).ifPresent(errors::add);
        checkPrice(accommodationDTO.getPrice()).ifPresent(errors::add);
        checkSleepersCapacity(accommodationDTO.getSleepersCapacity()).ifPresent(errors::add);
        checkProperties(accommodationDTO.getProperties()).ifPresent(errors::add);
        if (!errors.isEmpty()) {
            throw new AccommodationValidationException(errors);
        }
        if (!stayFacade.stayExists(accommodationDTO.getStayId())) {
            throw new StayNotFoundException(accommodationDTO.getStayId());
        }
    }

    private Optional<AccommodationErrorCodes> checkProperties(List<String> properties) {
        if (isNull(properties)) {
            return Optional.of(PROPERTIES_NULL);
        }
        for (String property : properties) {
            try {
                AccommodationPropertyDefinition.valueOf(property);
            } catch (IllegalArgumentException exception) {
                throw new AccommodationPropertyNotFound(property);
            }
        }
        return Optional.empty();
    }

    private Optional<AccommodationErrorCodes> checkId(Long id) {
        if (isNull(id)) {
            return Optional.of(ID_NULL);
        }
        return Optional.empty();
    }
}
