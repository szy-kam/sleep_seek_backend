package com.sleepseek.accomodation;

import com.sleepseek.accomodation.DTO.AccommodationDTO;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
class AccommodationController {
    private final AccommodationFacade accommodationFacade;

    AccommodationController(AccommodationFacade accommodationFacade) {
        this.accommodationFacade = accommodationFacade;
    }


    @GetMapping("/accommodation")
    List<AccommodationDTO> getAccommodations(@RequestParam Long stayId) {
        return accommodationFacade.getAccommodationsByStay(stayId);

    }

    @PostMapping("/accommodation")
    AccommodationDTO addAccommodation(Principal principal, @RequestBody AccommodationDTO accommodationDTO) {
        return accommodationFacade.addAccommodation(accommodationDTO);
    }

    @DeleteMapping("/accommodation/{accommodationId}")
    void deleteAccommodation(Principal principal, @PathVariable Long accommodationId) {
        accommodationFacade.deleteAccommodation(accommodationId );
    }

    @PutMapping("/accommodation/{accommodationId}")
    AccommodationDTO updateAccommodation(Principal principal, @RequestParam Long accommodationId, @RequestBody AccommodationDTO accommodationDTO) {
        accommodationDTO.setId(accommodationId);
        return accommodationFacade.updateAccommodation(accommodationDTO);
    }
}
