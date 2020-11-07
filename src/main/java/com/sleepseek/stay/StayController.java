package com.sleepseek.stay;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sleepseek.stay.DTO.StayDTO;
import com.sleepseek.stay.exception.StayAlreadyExistsException;
import com.sleepseek.stay.exception.StayNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
class StayController {

    private final StayFacade stayFacade;

    StayController(StayFacade stayFacade) {
        this.stayFacade = stayFacade;
    }

    @GetMapping("/stays/{stayId}")
    Object getStayById(@PathVariable Long stayId) {
        try {
            return stayFacade.getStay(stayId);
        } catch (StayNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e.getCause());
        }
    }

    @GetMapping("/stays")
    List<StayDTO> getStaysInRange(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return stayFacade.getStays(pageNumber, pageSize);
    }

    @PostMapping("/stays")
    void addStay(@RequestBody StayDTO stay) {
        try {
            stayFacade.addStay(stay);
        } catch (StayAlreadyExistsException e) {
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