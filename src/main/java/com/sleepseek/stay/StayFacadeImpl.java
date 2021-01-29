package com.sleepseek.stay;

import com.sleepseek.accomodation.Accommodation;
import com.sleepseek.image.ImageFacade;
import com.sleepseek.review.Review;
import com.sleepseek.stay.DTO.StayDTO;
import com.sleepseek.stay.exception.StayNotFoundException;
import com.sleepseek.user.UserFacade;
import com.sleepseek.user.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

class StayFacadeImpl implements StayFacade {

    private final StayRepositoryAdapter stayRepository;
    private final ImageFacade imageFacade;
    private final UserFacade userFacade;

    StayFacadeImpl(StayRepositoryAdapter stayRepository, ImageFacade imageFacade, UserFacade userFacade) {
        this.stayRepository = stayRepository;
        this.imageFacade = imageFacade;
        this.userFacade = userFacade;
    }

    @Override
    public StayDTO addStay(StayDTO stayDTO) {
        new StayValidator().validateStay(stayDTO, false);
        if (!userFacade.userExists(stayDTO.getUsername())) {
            throw new UserNotFoundException(stayDTO.getUsername());
        }
        Stay newStay = Stay.builder()
                .name(stayDTO.getName())
                .email(stayDTO.getEmail())
                .description(stayDTO.getDescription())
                .mainPhoto(stayDTO.getMainPhoto())
                .phoneNumber(stayDTO.getPhoneNumber())
                .minPrice(stayDTO.getMinPrice())
                .category(StayCategory.valueOf(stayDTO.getCategory()))
                .user(userFacade.getUserByUsername(stayDTO.getUsername()))
                .photos(stayDTO.getPhotos().stream().map(imageFacade::findImage).collect(Collectors.toList()))
                .properties(new HashSet<>())
                .accommodations(new HashSet<>())
                .address(Address.builder()
                        .city(stayDTO.getAddress().getCity())
                        .zipCode(stayDTO.getAddress().getZipCode())
                        .street(stayDTO.getAddress().getStreet())
                        .country(stayDTO.getAddress().getCountry())
                        .longitude(stayDTO.getAddress().getLongitude())
                        .latitude(stayDTO.getAddress().getLatitude())
                        .build())
                .build();
        stayDTO.getProperties().forEach(property -> newStay.getProperties().add(StayProperty.valueOf(property)));

        return StayMapper.toDto(stayRepository.save(newStay));
    }

    @Override
    public void updateStay(StayDTO stayDTO) {
        new StayValidator().validateStay(stayDTO, true);

        if (!stayExists(stayDTO.getId())) {
            throw new StayNotFoundException(stayDTO.getId());
        }
        if (!userFacade.userExists(stayDTO.getUsername())) {
            throw new UserNotFoundException(stayDTO.getUsername());
        }
        Stay stay = stayRepository.findById(stayDTO.getId()).orElseThrow();
        stay.setName(stayDTO.getName());
        stay.setEmail(stayDTO.getPhoneNumber());
        stay.setDescription(stayDTO.getDescription());
        stay.setMainPhoto(stayDTO.getMainPhoto());
        stay.setMinPrice(stayDTO.getMinPrice());
        stay.setUser(userFacade.getUserByUsername(stayDTO.getUsername()));
        stay.setEmail(stayDTO.getEmail());
        stay.setPhoneNumber(stayDTO.getPhoneNumber());
        stay.setCategory(StayCategory.valueOf(stayDTO.getCategory()));
        stay.setPhotos(stayDTO.getPhotos().stream().map(imageFacade::findImage).collect(Collectors.toList()));
        stay.setProperties(new HashSet<>());
        stayDTO.getProperties().forEach(property -> stay.getProperties().add(StayProperty.valueOf(property)));
        Address address = stay.getAddress();
        address.setCity(stayDTO.getAddress().getCity());
        address.setStreet(stayDTO.getAddress().getStreet());
        address.setZipCode(stayDTO.getAddress().getZipCode());
        address.setCountry(stayDTO.getAddress().getCountry());
        address.setLatitude(stayDTO.getAddress().getLatitude());
        address.setLongitude(stayDTO.getAddress().getLongitude());
        stayRepository.save(stay);

    }


    @Override
    public StayDTO getStay(Long id) {
        return stayRepository.findById(id).map(StayMapper::toDto).orElseThrow(() -> new StayNotFoundException(id));
    }

    @Override
    public Stay loadStay(Long id) {
        return stayRepository.findById(id).orElseThrow(() -> new StayNotFoundException(id));
    }

    @Override
    public boolean stayExists(Long id) {
        return stayRepository.existsById(id);
    }

    @Override
    public List<StayDTO> getStays(StaySearchParameters searchParameters) {
        Pageable pageable = PageRequest.of(searchParameters.getPageNumber(), searchParameters.getPageSize());
        if (!isNull(searchParameters.getUsername())) {
            return stayRepository.findAllByUser_Username(searchParameters.getUsername(), pageable).map(StayMapper::toDto).toList();
        }
        Page<Stay> stays = stayRepository.findAllByParameters(searchParameters);
        return stays.get().map(StayMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public void addAccommodation(Stay stay, Accommodation accommodation) {
        stay.addAccommodation(accommodation);
        stayRepository.save(stay);
    }

    @Override
    public void addReview(Stay stay, Review review) {
        stay.addReview(review);
        stayRepository.save(stay);
    }

    @Override
    public void deleteStay(Long id) {
        if (!stayExists(id)) {
            throw new StayNotFoundException(id);
        }
        stayRepository.deleteById(id);
    }


}
