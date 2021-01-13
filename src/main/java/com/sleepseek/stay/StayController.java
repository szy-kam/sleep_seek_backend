package com.sleepseek.stay;

import com.sleepseek.stay.DTO.StayDTO;
import com.sleepseek.stay.exception.StayAlreadyExistsException;
import com.sleepseek.stay.exception.StayNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            return stayFacade.getStay(stayId);
        } catch (StayNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e.getCause());
        }
    }

    @GetMapping("/stays")
    List<StayDTO> getStaysInRange(@RequestParam(required = false) Integer pageNumber,
                                  @RequestParam(required = false) Integer pageSize,
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
        try {
            stay.setUsername(principal.getName());
            return stayFacade.addStay(stay).getId();
        } catch (StayAlreadyExistsException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    @PutMapping("/stays/{stayId}")
    Object updateStay(@RequestBody StayDTO stayDTO, @PathVariable Long stayId) {
        try {
            stayDTO.setId(stayId);
            stayFacade.updateStay(stayDTO);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (StayNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e.getCause());
        }
    }

    @DeleteMapping("/stays/{stayId}")
    Object deleteStay(@PathVariable Long stayId) {
        try {
            stayFacade.deleteStay(stayId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (StayNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e.getCause());
        }
    }

}
