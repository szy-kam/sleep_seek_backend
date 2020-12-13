package com.sleepseek.image;

import com.sleepseek.image.DTO.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageFacade {

    Optional<ImageDTO> addImage(MultipartFile image) throws IOException;

}
