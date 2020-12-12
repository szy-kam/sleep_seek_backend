package com.sleepseek.image;

import com.sleepseek.image.DTO.ImageDTO;

import java.util.Optional;

public interface ImageFacade {

    Optional<ImageDTO> addImage(ImageDTO imageDTO);

    boolean imageExist(Long id);

    void deleteImage(Long id);

    Optional<ImageDTO> getImage(Long id);



}
