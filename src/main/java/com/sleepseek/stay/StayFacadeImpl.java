package com.sleepseek.stay;

import com.google.common.collect.Sets;
import com.sleepseek.accomodation.Accommodation;
import com.sleepseek.image.ImageFacade;
import com.sleepseek.review.Review;
import com.sleepseek.stay.DTO.StayDTO;
import com.sleepseek.stay.exception.*;
import com.sleepseek.user.UserFacade;
import com.sleepseek.user.exception.UserNotFoundException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.sleepseek.stay.StayConfiguration.MAX_PAGE_SIZE;
import static com.sleepseek.stay.StayErrorCodes.*;
import static java.util.Objects.isNull;

class StayFacadeImpl implements StayFacade {

    private static final Long MAX_PRICE = 1000000L;
    private final List<String> SUPPORTED_ORDER_BY = Arrays.asList("name", "avgRate", "city");
    private final StayRepository stayRepository;
    private final ImageFacade imageFacade;
    private final UserFacade userFacade;

    StayFacadeImpl(StayRepository stayRepository, ImageFacade imageFacade, UserFacade userFacade) {
        this.stayRepository = stayRepository;
        this.imageFacade = imageFacade;
        this.userFacade = userFacade;
    }

    @Override
    public StayDTO addStay(StayDTO stayDTO) {
        Set<StayErrorCodes> errors = validateStay(stayDTO);
        if (!isNull(stayDTO.getId())) {
            errors.add(ID_SHOULD_NOT_DEFINED);
        }
        if (!errors.isEmpty()) {
            throw new StayValidationException(errors);
        }
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
        Set<StayErrorCodes> errors = validateStay(stayDTO);
        checkId(stayDTO.getId()).ifPresent(errors::add);
        if (!errors.isEmpty()) {
            throw new StayValidationException(errors);
        }
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

    private Optional<StayErrorCodes> checkId(Long id) {
        if (isNull(id)) {
            return Optional.of(ID_NULL);
        }
        return Optional.empty();
    }

    private Set<StayErrorCodes> validateStay(StayDTO stayDTO) {
        Set<StayErrorCodes> errorCodes = Sets.newHashSet();
        checkName(stayDTO.getName()).ifPresent(errorCodes::add);
        checkDescription(stayDTO.getDescription()).ifPresent(errorCodes::add);
        checkUsername(stayDTO.getUsername()).ifPresent(errorCodes::add);
        checkAddress(stayDTO.getAddress()).ifPresent(errorCodes::add);
        checkEmail(stayDTO.getEmail()).ifPresent(errorCodes::add);
        checkPhoneNumber(stayDTO.getPhoneNumber()).ifPresent(errorCodes::add);
        checkCategory(stayDTO.getCategory()).ifPresent(errorCodes::add);
        checkMinPrice(stayDTO.getMinPrice()).ifPresent(errorCodes::add);
        checkPhotos(stayDTO.getPhotos()).ifPresent(errorCodes::add);
        checkProperties(stayDTO.getProperties()).ifPresent(errorCodes::add);
        if (!isNull(stayDTO.getAddress())) {
            checkStreet(stayDTO.getAddress().getStreet()).ifPresent(errorCodes::add);
            checkZipCode(stayDTO.getAddress().getZipCode()).ifPresent(errorCodes::add);
            checkCountry(stayDTO.getAddress().getCountry()).ifPresent(errorCodes::add);
            checkCity(stayDTO.getAddress().getCity()).ifPresent(errorCodes::add);
        }
        return errorCodes;
    }

    private Optional<StayErrorCodes> checkProperties(List<String> properties) {
        if (isNull(properties)) {
            return Optional.of(PROPERTIES_NULL);
        }

        for (String property : properties) {
            try {
                StayProperty.valueOf(property);
            } catch (IllegalArgumentException e) {
                throw new StayPropertyNotFoundException(property);
            }
        }

        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkPhotos(List<String> photos) {
        if (isNull(photos)) {
            return Optional.of(PHOTOS_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkCity(String city) {
        if (isNull(city)) {
            return Optional.of(CITY_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkCountry(String country) {
        if (isNull(country)) {
            return Optional.of(COUNTRY_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkZipCode(String zipCode) {
        if (isNull(zipCode)) {
            return Optional.of(ZIPCODE_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkStreet(String street) {
        if (isNull(street)) {
            return Optional.of(STREET_NULL);
        }
        return Optional.empty();

    }

    private Optional<StayErrorCodes> checkPhoneNumber(String phoneNumber) {
        if (isNull(phoneNumber)) {
            return Optional.of(PHONE_NUMBER_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkMinPrice(Long minPrice) {
        if (isNull(minPrice)) {
            return Optional.of(MIN_PRICE_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkCategory(String category) {
        if (isNull(category)) {
            return Optional.of(CATEGORY_NULL);
        }
        try {
            StayCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            throw new StayCategoryNotFoundException(category);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkEmail(String email) {
        if (isNull(email)) {
            return Optional.of(EMAIL_NULL);
        }

        if (!email.equals("") && !EmailValidator.getInstance().isValid(email)) {
            return Optional.of(EMAIL_INVALID);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkAddress(StayDTO.AddressDTO addressDTO) {
        if (isNull(addressDTO)) {
            return Optional.of(ADDRESS_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkUsername(String username) {
        if (isNull(username)) {
            return Optional.of(USERNAME_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkDescription(String description) {
        if (isNull(description)) {
            return Optional.of(DESCRIPTION_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkName(String name) {
        if (isNull(name)) {
            return Optional.of(NAME_NULL);
        }
        return Optional.empty();
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
        validateSearchParameters(searchParameters);
        Pageable pageable = PageRequest.of(searchParameters.getPageNumber(), searchParameters.getPageSize());
        if (!isNull(searchParameters.getUsername())) {
            return getStaysByUsername(searchParameters.getUsername(), pageable);
        }
        Page<Stay> stays = getStaysBySearchParameters(searchParameters, pageable);
        return stays.get().map(StayMapper::toDto).collect(Collectors.toList());
    }

    private Page<Stay> getStaysBySearchParameters(StaySearchParameters searchParameters, Pageable pageable) {
        String orderBy = searchParameters.getOrderBy();
        if (isNull(orderBy) || orderBy.equals("name")) {
            return getStaysOrderByName(searchParameters, pageable);
        } else if (orderBy.equals("avgRate")) {
            return getStaysOrderByRating(searchParameters, pageable);
        } else if (orderBy.equals("city")) {
            return getStaysOrderByCity(searchParameters, pageable);
        }
        return Page.empty();
    }

    private Page<Stay> getStaysOrderByRating(StaySearchParameters searchParameters, Pageable pageable) {
        boolean descending = isNull(searchParameters.getOrder()) || searchParameters.getOrder().equals("DESC");
        if (descending) {
            return stayRepository.findByAvgRateDesc(
                    searchParameters.getPriceFrom(),
                    searchParameters.getPriceTo(),
                    searchParameters.getUsername(),
                    searchParameters.getCategory(),
                    searchParameters.getCity(),
                    searchParameters.getLongitude(),
                    searchParameters.getLatitude(),
                    searchParameters.getMaxDistance(),
                    pageable);
        } else {
            return stayRepository.findByAvgRateAsc(
                    searchParameters.getPriceFrom(),
                    searchParameters.getPriceTo(),
                    searchParameters.getUsername(),
                    searchParameters.getCategory(),
                    searchParameters.getCity(),
                    searchParameters.getLongitude(),
                    searchParameters.getLatitude(),
                    searchParameters.getMaxDistance(),
                    pageable);
        }
    }

    private Page<Stay> getStaysOrderByName(StaySearchParameters searchParameters, Pageable pageable) {
        boolean descending = isNull(searchParameters.getOrder()) || searchParameters.getOrder().equals("DESC");
        if (descending) {
            return stayRepository.findAllByNameDesc(
                    searchParameters.getPriceFrom(),
                    searchParameters.getPriceTo(),
                    searchParameters.getUsername(),
                    searchParameters.getCategory(),
                    searchParameters.getCity(),
                    searchParameters.getLongitude(),
                    searchParameters.getLatitude(),
                    searchParameters.getMaxDistance(),
                    pageable);
        } else {
            return stayRepository.findAllByNameAsc(
                    searchParameters.getPriceFrom(),
                    searchParameters.getPriceTo(),
                    searchParameters.getUsername(),
                    searchParameters.getCategory(),
                    searchParameters.getCity(),
                    searchParameters.getLongitude(),
                    searchParameters.getLatitude(),
                    searchParameters.getMaxDistance(),
                    pageable);
        }
    }

    private Page<Stay> getStaysOrderByCity(StaySearchParameters searchParameters, Pageable pageable) {
        boolean descending = isNull(searchParameters.getOrder()) || searchParameters.getOrder().equals("DESC");
        if (descending) {
            return stayRepository.findAllByCityDesc(
                    searchParameters.getPriceFrom(),
                    searchParameters.getPriceTo(),
                    searchParameters.getUsername(),
                    searchParameters.getCategory(),
                    searchParameters.getCity(),
                    searchParameters.getLongitude(),
                    searchParameters.getLatitude(),
                    searchParameters.getMaxDistance(),
                    pageable);
        } else {
            return stayRepository.findAllByCityAsc(
                    searchParameters.getPriceFrom(),
                    searchParameters.getPriceTo(),
                    searchParameters.getUsername(),
                    searchParameters.getCategory(),
                    searchParameters.getCity(),
                    searchParameters.getLongitude(),
                    searchParameters.getLatitude(),
                    searchParameters.getMaxDistance(),
                    pageable);
        }
    }


    private List<StayDTO> getStaysByUsername(String username, Pageable pageable) {
        if (!userFacade.userExists(username)) {
            throw new UserNotFoundException(username);
        }
        return stayRepository.findAllByUserOrderByName(userFacade.getUserByUsername(username), pageable).get().map(StayMapper::toDto).collect(Collectors.toList());
    }

    private void validateSearchParameters(StaySearchParameters searchParameters) {
        Set<StaySearchParametersErrorCodes> errorCodes = Sets.newHashSet();
        checkPrice(searchParameters.getPriceFrom()).ifPresent(errorCodes::add);
        checkPrice(searchParameters.getPriceTo()).ifPresent(errorCodes::add);
        checkCategorySearch(searchParameters.getCategory()).ifPresent(errorCodes::add);
        checkPropertiesSearch(searchParameters.getProperty()).ifPresent(errorCodes::add);
        checkDate(searchParameters.getDateTo()).ifPresent(errorCodes::add);
        checkDate(searchParameters.getDateFrom()).ifPresent(errorCodes::add);
        checkPageNumber(searchParameters.getPageNumber()).ifPresent(errorCodes::add);
        checkPageSize(searchParameters.getPageSize()).ifPresent(errorCodes::add);
        checkOrderBy(searchParameters.getOrderBy()).ifPresent(errorCodes::add);
        checkOrder(searchParameters.getOrder()).ifPresent(errorCodes::add);
        if (!errorCodes.isEmpty()) {
            throw new StaySearchParametersException(errorCodes);
        }
    }

    private Optional<StaySearchParametersErrorCodes> checkOrder(String order) {
        if (!isNull(order)) {
            if (!order.equals("ASC") && !order.equals("DESC")) {
                return Optional.of(StaySearchParametersErrorCodes.ORDER_INVALID);
            }
        }
        return Optional.empty();
    }

    private Optional<StaySearchParametersErrorCodes> checkOrderBy(String orderBy) {
        if (!isNull(orderBy)) {
            if (!SUPPORTED_ORDER_BY.contains(orderBy)) {
                return Optional.of(StaySearchParametersErrorCodes.ORDER_BY_INVALID);
            }
        }

        return Optional.empty();
    }


    private Optional<StaySearchParametersErrorCodes> checkDate(String dateFrom) {
        if (!isNull(dateFrom)) {
            try {
                LocalDate.parse(dateFrom);
            } catch (DateTimeParseException e) {
                return Optional.of(StaySearchParametersErrorCodes.DATES_OUT_OF_BOUNDS);
            }
        }
        return Optional.empty();
    }

    private Optional<StaySearchParametersErrorCodes> checkPageSize(Integer pageSize) {
        if (isNull(pageSize) || pageSize < 0 || pageSize > MAX_PAGE_SIZE) {
            return Optional.of(StaySearchParametersErrorCodes.WRONG_PAGE_CONSTRAINTS);
        }
        return Optional.empty();
    }

    private Optional<StaySearchParametersErrorCodes> checkPageNumber(Integer pageNumber) {
        if (isNull(pageNumber) || pageNumber < 0) {
            return Optional.of(StaySearchParametersErrorCodes.WRONG_PAGE_CONSTRAINTS);
        }
        return Optional.empty();
    }

    private Optional<StaySearchParametersErrorCodes> checkCategorySearch(String category) {
        if (!isNull(category)) {
            try {
                StayCategory.valueOf(category);
            } catch (IllegalArgumentException e) {
                return Optional.of(StaySearchParametersErrorCodes.CATEGORY_NOT_FOUND);
            }
        }
        return Optional.empty();
    }

    private Optional<StaySearchParametersErrorCodes> checkPropertiesSearch(List<String> properties) {
        if (!isNull(properties)) {
            for (String property : properties) {
                try {
                    StayProperty.valueOf(property);
                } catch (IllegalArgumentException e) {
                    return Optional.of(StaySearchParametersErrorCodes.PROPERTY_NOT_FOUND);
                }
            }
        }
        return Optional.empty();
    }

    private Optional<StaySearchParametersErrorCodes> checkPrice(Long price) {
        if (!isNull(price)) {
            if (price < 0 || price > MAX_PRICE) {
                return Optional.of(StaySearchParametersErrorCodes.PRICE_OUT_OF_BOUNDS);
            }
        }


        return Optional.empty();
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
