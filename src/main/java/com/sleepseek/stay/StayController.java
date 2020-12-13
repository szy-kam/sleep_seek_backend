package com.sleepseek.stay;

import com.sleepseek.image.DTO.ImageDTO;
import com.sleepseek.image.ImageFacade;
import com.sleepseek.stay.DTO.StayDTO;
import com.sleepseek.stay.exception.StayAlreadyExistsException;
import com.sleepseek.stay.exception.StayNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
class StayController {

    private final StayFacade stayFacade;
    private final ImageFacade imageFacade;

    StayController(StayFacade stayFacade, ImageFacade imageFacade) {
        this.stayFacade = stayFacade;
        this.imageFacade = imageFacade;
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
    List<StayDTO> getStaysInRange(@RequestParam(required = false) Integer pageNumber,
                                  @RequestParam(required = false) Integer pageSize,
                                  @RequestParam(required = false) Long userId,
                                  @RequestParam(required = false) String s) {
        return stayFacade.getStays(StaySearchParameters.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .userId(userId)
                .searchString(s)
                .build());
    }

    @PostMapping("/stays")
    void addStay(@RequestBody StayDTO stay, @RequestBody(required = false) List<MultipartFile> newPhotos) {
        try {
            if(newPhotos != null){
                List<ImageDTO> imageDTOS = new ArrayList<>();
                for(MultipartFile image: newPhotos){
                    ImageDTO imageDTO = imageFacade.addImage(image).orElseThrow();
                    imageDTOS.add(imageDTO);
                }
                stay.setPhotos(imageDTOS);
            }
            stayFacade.addStay(stay);
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
