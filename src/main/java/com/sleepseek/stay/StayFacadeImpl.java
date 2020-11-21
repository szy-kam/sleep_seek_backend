package com.sleepseek.stay;

import com.sleepseek.stay.DTO.StayDTO;
import com.sleepseek.stay.exception.StayAlreadyExistsException;
import com.sleepseek.stay.exception.StayNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

class StayFacadeImpl implements StayFacade {

    private final StayRepository stayRepository;

    StayFacadeImpl(StayRepository stayRepository) {
        this.stayRepository = stayRepository;
    }

    @Override
    public void addStay(StayDTO stayDTO) {
        if (stayDTO.getId() == null || !stayRepository.existsById(stayDTO.getId())) {
            stayRepository.save(Stay.builder()
                    .name(stayDTO.getName())
                    .contactInfo(stayDTO.getContactInfo())
                    .description(stayDTO.getDescription())
                    .mainPhoto(stayDTO.getMainPhoto())
                    .price(stayDTO.getPrice())
                    .userId(stayDTO.getUserId())
                    .address(Address.builder()
                            .city(stayDTO.getAddress().getCity())
                            .zipCode(stayDTO.getAddress().getZipCode())
                            .street(stayDTO.getAddress().getStreet()).build())
                    .build());
        } else {
            throw new StayAlreadyExistsException(stayDTO.getId());
        }
    }

    @Override
    public void updateStay(StayDTO stayDTO) {
        if (stayExists(stayDTO.getId())) {
            Stay stay = stayRepository.findById(stayDTO.getId()).orElseThrow();
            stay.setName(stayDTO.getName());
            stay.setContactInfo(stayDTO.getContactInfo());
            stay.setDescription(stayDTO.getDescription());
            stay.setMainPhoto(stayDTO.getMainPhoto());
            stay.setPrice(stayDTO.getPrice());
            stay.setUserId(stayDTO.getUserId());
            Address address = stay.getAddress();
            address.setCity(stayDTO.getAddress().getCity());
            address.setStreet(stayDTO.getAddress().getStreet());
            address.setZipCode(stayDTO.getAddress().getZipCode());
            stayRepository.save(stay);
        }else{
            throw new StayNotFoundException(stayDTO.getId());
        }
    }

    @Override
    public StayDTO getStay(Long id) {
        if (!stayExists(id)) {
            throw new StayNotFoundException(id);
        }
        return stayRepository.findById(id).map(StayMapper::toDto).orElseThrow();
    }

    @Override
    public boolean stayExists(Long id) {
        return stayRepository.existsById(id);
    }

    @Override
    public List<StayDTO> getStays(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Stay> stays = stayRepository.findAll(pageable);
        return stays.get().map(StayMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteStay(Long id) {
        if (!stayExists(id)) {
            throw new StayNotFoundException(id);
        }
        stayRepository.deleteById(id);
    }
}
