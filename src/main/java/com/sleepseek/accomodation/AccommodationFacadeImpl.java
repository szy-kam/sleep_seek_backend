package com.sleepseek.accomodation;

import com.sleepseek.accomodation.DTO.AccommodationDTO;
import com.sleepseek.stay.StayFacade;

import java.util.List;
import java.util.stream.Collectors;

class AccommodationFacadeImpl implements AccommodationFacade {
    private final AccommodationRepository accommodationRepository;
    private final StayFacade stayFacade;

    AccommodationFacadeImpl(AccommodationRepository accommodationRepository, StayFacade stayFacade) {
        this.accommodationRepository = accommodationRepository;
        this.stayFacade = stayFacade;
    }

    @Override
    public AccommodationDTO addAccommodation(AccommodationDTO accommodationDTO) {
        Accommodation newAccommodation = accommodationRepository.save(Accommodation.builder()
                .price(accommodationDTO.getPrice())
                .quantity(accommodationDTO.getQuantity())
                .sleepersCapacity(accommodationDTO.getSleepersCapacity())
                .stay(stayFacade.loadStay(accommodationDTO.getStayId()))
                .build());

        return AccommodationMapper.toDTO(newAccommodation);
    }

    @Override
    public void deleteAccommodation(Long id) {
        accommodationRepository.deleteById(id);
    }

    @Override
    public List<AccommodationDTO> getAccommodationsByStay(Long stayId) {
        return accommodationRepository.findAccommodationsByStay(stayFacade.loadStay(stayId)).stream().map(AccommodationMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Accommodation loadById(Long id) {
        return accommodationRepository.findById(id).orElseThrow();
    }

    @Override
    public AccommodationDTO updateAccommodation(AccommodationDTO accommodationDTO) {
        return null;
    }
}
