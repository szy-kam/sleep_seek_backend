package com.sleepseek.image;

import com.sleepseek.image.DTO.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageFacade {

    Optional<ImageDTO> addImage(MultipartFile image);

    boolean imageExist(Long id);

    void deleteImage(Long id);

    Optional<ImageDTO> getImage(Long id);


}
