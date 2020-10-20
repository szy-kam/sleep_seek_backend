package com.sleepseek.stay;

import com.sleepseek.stay.DTO.StayDTO;

import java.util.List;
import java.util.stream.Collectors;

class StayFacadeImpl implements StayFacade {

    private final StayRepository stayRepository;

    StayFacadeImpl(StayRepository stayRepository) {
        this.stayRepository = stayRepository;
    }

    @Override
    public void addStay(StayDTO stayDTO) {
        if (!stayRepository.existsById(stayDTO.getId())) {

            stayRepository.save(Stay.builder()
                    .name(stayDTO.getName())
                    .contactInfo(stayDTO.getContactInfo())
                    .description(stayDTO.getDescription())
                    .mainPhoto(stayDTO.getMainPhoto())
                    .price(stayDTO.getPrice())
                    .properties(stayDTO.getProperties())
                    .userId(stayDTO.getUserId())
                    .address(Address.builder()
                            .city(stayDTO.getAddress().getCity())
                            .zipCode(stayDTO.getAddress().getZipCode())
                            .address(stayDTO.getAddress().getAddress()).build())
                    .build());
        }
    }

    @Override
    public StayDTO getStay(Long id) {
        return stayRepository.findById(id).map(StayMapper::toDto).orElseThrow();
    }

    @Override
    public boolean stayExists(Long id) {
        return stayRepository.existsById(id);
    }

    @Override
    public List<StayDTO> getStays(Integer from, Integer to) {
        List<Stay> stays = stayRepository.findInRange(from, to);
        return stays.stream().map(StayMapper::toDto).collect(Collectors.toList());
    }
}
