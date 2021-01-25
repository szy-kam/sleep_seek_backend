package com.sleepseek.image;

import com.sleepseek.image.DTO.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageFacade {

    ImageDTO addImage(String username, MultipartFile image) throws IOException;

    Image findImage(String url);
}
