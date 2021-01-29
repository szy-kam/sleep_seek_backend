package com.sleepseek.image;

import com.sleepseek.image.DTO.ImageDTO;
import com.sleepseek.image.exception.ImageStorageException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
class ImageController {
    private final ImageFacade imageFacade;

    ImageController(ImageFacade imageFacade) {
        this.imageFacade = imageFacade;
    }

    @PostMapping(value = "/image", produces = MediaType.APPLICATION_JSON_VALUE)
    ImageDTO uploadImage(Principal principal, @RequestParam MultipartFile image) {
        try {
            return imageFacade.addImage(principal.getName(), image);
        } catch (IOException e) {
            throw new ImageStorageException(" io exception");
        }
    }

}
