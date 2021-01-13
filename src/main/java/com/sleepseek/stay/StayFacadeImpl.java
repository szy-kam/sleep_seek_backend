package com.sleepseek.stay;

import com.sleepseek.image.ImageRepository;
import com.sleepseek.stay.DTO.StayDTO;
import com.sleepseek.stay.exception.StayAlreadyExistsException;
import com.sleepseek.stay.exception.StayNotFoundException;
import com.sleepseek.user.UserRepository;
import com.sleepseek.user.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

class StayFacadeImpl implements StayFacade {

    private final StayRepository stayRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    StayFacadeImpl(StayRepository stayRepository, ImageRepository imageRepository, UserRepository userRepository) {
        this.stayRepository = stayRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public StayDTO addStay(StayDTO stayDTO) {
        if (stayDTO.getId() == null || !stayRepository.existsById(stayDTO.getId())) {
            Stay newStay = stayRepository.save(Stay.builder()
                    .name(stayDTO.getName())
                    .email(stayDTO.getEmail())
                    .description(stayDTO.getDescription())
                    .mainPhoto(stayDTO.getMainPhoto())
                    .phoneNumber(stayDTO.getPhoneNumber())
                    .minPrice(stayDTO.getMinPrice())
                    .category(stayDTO.getCategory())
                    .user(userRepository.findByUsername(stayDTO.getUsername()).orElseThrow(()-> new UserNotFoundException(stayDTO.getUsername())))
                    .photos(stayDTO.getPhotos().stream().map(url ->
                            imageRepository.findByUrl(url).stream().filter(image ->
                                    image.getStay().getName().equals(stayDTO.getName())
                            ).findAny().orElseThrow()
                    ).collect(Collectors.toList()))
                    .address(Address.builder()
                            .city(stayDTO.getAddress().getCity())
                            .zipCode(stayDTO.getAddress().getZipCode())
                            .street(stayDTO.getAddress().getStreet())
                            .country(stayDTO.getAddress().getCountry())
                            .longitude(stayDTO.getAddress().getLongitude())
                            .latitude(stayDTO.getAddress().getLatitude())
                            .build())
                    .build());
            return StayMapper.toDto(newStay);

        } else {
            throw new StayAlreadyExistsException(stayDTO.getId());
        }
    }

    @Override
    public void updateStay(StayDTO stayDTO) {
        if (stayExists(stayDTO.getId())) {
            Stay stay = stayRepository.findById(stayDTO.getId()).orElseThrow();
            stay.setName(stayDTO.getName());
            stay.setEmail(stayDTO.getPhoneNumber());
            stay.setDescription(stayDTO.getDescription());
            stay.setMainPhoto(stayDTO.getMainPhoto());
            stay.setMinPrice(stayDTO.getMinPrice());
            stay.setUser(userRepository.findByUsername(stayDTO.getUsername()).orElseThrow());
            stay.setEmail(stayDTO.getEmail());
            stay.setPhoneNumber(stayDTO.getPhoneNumber());
            stay.setCategory(stayDTO.getCategory());
            stay.setPhotos(stayDTO.getPhotos().stream().map(url ->
                    imageRepository.findByUrl(url).stream().filter(image ->
                            image.getStay().getName().equals(stayDTO.getName())
                    ).findAny().orElseThrow()
            ).collect(Collectors.toList()));
            Address address = stay.getAddress();
            address.setCity(stayDTO.getAddress().getCity());
            address.setStreet(stayDTO.getAddress().getStreet());
            address.setZipCode(stayDTO.getAddress().getZipCode());
            address.setCountry(stayDTO.getAddress().getCountry());
            address.setLatitude(stayDTO.getAddress().getLatitude());
            address.setLongitude(stayDTO.getAddress().getLongitude());
            stayRepository.save(stay);
        } else {
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
    public Stay loadStay(Long id) {
        return stayRepository.findById(id).orElseThrow(()-> new StayNotFoundException(id));
    }

    @Override
    public boolean stayExists(Long id) {
        return stayRepository.existsById(id);
    }

    @Override
    public List<StayDTO> getStays(StaySearchParameters searchParameters) {

        Pageable pageable = PageRequest.of(searchParameters.getPageNumber(), searchParameters.getPageSize());
        Page<Stay> stays;
        if (searchParameters.getUsername() != null) {
            stays = stayRepository.findAllByUser(userRepository.findByUsername(searchParameters.getUsername()).orElseThrow(), pageable);
        } else {
            stays = stayRepository.findAll(pageable);
        }
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
