package com.sleepseek.accomodation;

import com.sleepseek.accomodation.DTO.AccommodationDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class AccommodationController {
    private final AccommodationFacade accommodationFacade;

    public AccommodationController(AccommodationFacade accommodationFacade) {
        this.accommodationFacade = accommodationFacade;
    }


    @GetMapping("/accomodation/{stayId}")
    List<AccommodationDTO> getAccommodations(@PathVariable Long stayId){
        return accommodationFacade.getAccommodationsByStay(stayId);

    }

    @PostMapping("/accomodation")
    AccommodationDTO addAccommodation(Principal principal, AccommodationDTO accommodationDTO){
        return accommodationFacade.addAccommodation(accommodationDTO);
    }
}
