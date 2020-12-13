package com.sleepseek.image;

import com.sleepseek.image.DTO.ImageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
class ImageController {
    private final ImageFacade imageFacade;

    public ImageController(ImageFacade imageFacade) {
        this.imageFacade = imageFacade;
    }

    @PostMapping(value = "/image", produces = MediaType.APPLICATION_JSON_VALUE)
    ImageDTO uploadImage(@RequestParam MultipartFile image) {
        try {
            return imageFacade.addImage(image).orElseThrow();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getCause());
        }
    }

}
