package com.sleepseek.accommodationProperty;

import com.sleepseek.accommodationProperty.DTO.AccommodationPropertyDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccommodationPropertyController {
    private final AccommodationPropertyFacade accommodationPropertyFacade;

    public AccommodationPropertyController(AccommodationPropertyFacade accommodationPropertyFacade) {
        this.accommodationPropertyFacade = accommodationPropertyFacade;
    }

    @PostMapping("/accommodationProperty")
    AccommodationPropertyDTO add(AccommodationPropertyDTO accommodationPropertyDTO){
        return accommodationPropertyFacade.add(accommodationPropertyDTO);
    }

    @GetMapping("/accommodationProperty")
    List<AccommodationPropertyDTO> getByAccommodationId(@RequestParam Long accommodationId){
        return accommodationPropertyFacade.findByAccommodation(accommodationId);
    }
}
