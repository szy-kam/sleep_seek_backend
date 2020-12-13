package com.sleepseek.image;

import com.sleepseek.image.DTO.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

class ImageFacadeImpl implements ImageFacade{
    @Override
    public Optional<ImageDTO> addImage(MultipartFile image) {
        return Optional.empty();
    }

    @Override
    public boolean imageExist(Long id) {
        return false;
    }

    @Override
    public void deleteImage(Long id) {

    }

    @Override
    public Optional<ImageDTO> getImage(Long id) {
        return Optional.empty();
    }
}
