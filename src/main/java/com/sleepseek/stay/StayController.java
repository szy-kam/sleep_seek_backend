package com.sleepseek.stay;

import com.sleepseek.stay.DTO.StayDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
class StayController {

    private final StayFacade stayFacade;

    StayController(StayFacade stayFacade) {
        this.stayFacade = stayFacade;
    }

    @GetMapping("/stays/{stayId}")
    StayDTO getStayById(@PathVariable Long stayId) {
        return stayFacade.getStay(stayId);

    }

    @GetMapping("/stays/user/{username}")
    List<StayDTO> getStayByUser(@PathVariable String username, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return stayFacade.getStays(StaySearchParameters.builder()
                .username(username)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build());
    }

    @GetMapping("/stays")
    List<StayDTO> getStays(@RequestParam Integer pageNumber,
                           @RequestParam Integer pageSize,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String city,
                           @RequestParam(required = false) String country,
                           @RequestParam(required = false) Double latitude,
                           @RequestParam(required = false) Double longitude,
                           @RequestParam(required = false) Long distance,
                           @RequestParam(required = false) String category,
                           @RequestParam(required = false) List<String> prop,
                           @RequestParam(required = false) Long priceFrom,
                           @RequestParam(required = false) Long priceTo,
                           @RequestParam(required = false) String orderBy,
                           @RequestParam(required = false) String order,
                           @RequestParam(required = false) String dateFrom,
                           @RequestParam(required = false) String dateTo) {

        return stayFacade.getStays(StaySearchParameters.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .username(username)
                .country(country)
                .name(name)
                .city(city)
                .distance(distance)
                .latitude(latitude)
                .longitude(longitude)
                .category(category)
                .property(prop)
                .priceFrom(priceFrom)
                .priceTo(priceTo)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .order(order)
                .orderBy(orderBy)
                .build());
    }

    @PostMapping("/stays")
    Long addStay(Principal principal, @RequestBody StayDTO stay) {
        stay.setUsername(principal.getName());
        return stayFacade.addStay(stay).getId();
    }

    @PutMapping("/stays/{stayId}")
    Object updateStay(@RequestBody StayDTO stayDTO, @PathVariable Long stayId) {
        stayDTO.setId(stayId);
        stayFacade.updateStay(stayDTO);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/stays/{stayId}")
    Object deleteStay(@PathVariable Long stayId) {
        stayFacade.deleteStay(stayId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/stayCategories")
    List<String> getCategories() {
        return Arrays.stream(StayCategory.values()).map(StayCategory::getName).collect(Collectors.toList());
    }

    @GetMapping("/stayProperties")
    List<String> getProperties() {
        return Arrays.stream(StayProperty.values()).map(StayProperty::getName).collect(Collectors.toList());
    }

}
