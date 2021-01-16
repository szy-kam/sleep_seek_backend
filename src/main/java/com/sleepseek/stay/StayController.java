package com.sleepseek.stay;

import com.sleepseek.stay.DTO.StayDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    @GetMapping("/stays")
    List<StayDTO> getStays(@RequestParam Integer pageNumber,
                           @RequestParam Integer pageSize,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String s) {
        return stayFacade.getStays(StaySearchParameters.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .username(username)
                .searchString(s)
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

}
