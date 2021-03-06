package com.sleepseek.accomodation;

import com.sleepseek.accomodation.DTO.AccommodationDTO;
import com.sleepseek.accomodation.DTO.AccommodationTemplateDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
class AccommodationController {
    private final AccommodationFacade accommodationFacade;

    AccommodationController(AccommodationFacade accommodationFacade) {
        this.accommodationFacade = accommodationFacade;
    }


    @GetMapping("/accommodation-template")
    List<AccommodationTemplateDTO> getAccommodationTemplates(
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo,
            @RequestParam Long stayId) {
        return accommodationFacade.getAccommodationTemplatesByStay(stayId, PageRequest.of(pageNumber, pageSize), dateFrom, dateTo);
    }

    @GetMapping("/accommodation")
    List<AccommodationDTO> getAccommodations(
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestParam Long accommodationTemplateId,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo) {
        if (dateFrom != null && dateTo != null) {
            return accommodationFacade.getAccommodationsByDate(accommodationTemplateId, PageRequest.of(pageNumber, pageSize), dateFrom, dateTo);
        }

        return accommodationFacade.getAccommodations(accommodationTemplateId, PageRequest.of(pageNumber, pageSize));
    }

    @GetMapping("/accommodation/{id}")
    AccommodationDTO getAccommodation(
            @PathVariable Long id) {
        return accommodationFacade.getAccommodation(id);
    }

    @GetMapping("/accommodation-template/{id}")
    AccommodationTemplateDTO getAccommodationTemplate(
            @PathVariable Long id) {
        return accommodationFacade.getAccommodationTemplate(id);
    }

    @PostMapping("/accommodation-template")
    void addAccommodation(Principal principal, @RequestBody AccommodationTemplateDTO accommodationTemplateDTO) {
        accommodationFacade.addAccommodationTemplate(accommodationTemplateDTO);
    }

    @DeleteMapping("/accommodation-template/{accommodationTemplateId}")
    void deleteAccommodation(Principal principal, @PathVariable Long accommodationTemplateId) {
        accommodationFacade.deleteAccommodationTemplate(accommodationTemplateId);
    }

    @PutMapping("/accommodation-template/{accommodationTemplateId}")
    void updateAccommodationTemplate(Principal principal, @PathVariable Long accommodationTemplateId, @RequestBody AccommodationTemplateDTO accommodationTemplateDTO) {
        accommodationTemplateDTO.setId(accommodationTemplateId);
        accommodationFacade.updateAccommodationTemplate(accommodationTemplateDTO);
    }

    @PutMapping("/accommodation/{accommodationId}")
    void updateAccommodation(Principal principal, @PathVariable Long accommodationId, @RequestBody AccommodationDTO accommodationDTO) {
        accommodationDTO.setId(accommodationId);
        accommodationFacade.updateAccommodation(accommodationDTO);
    }

    @GetMapping("/accommodationProperties")
    List<String> getProperties() {
        return Arrays.stream(AccommodationProperty.values()).map(AccommodationProperty::getName).collect(Collectors.toList());
    }
}
