package com.sleepseek.image;

import com.sleepseek.image.DTO.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

class ImageFacadeImpl implements ImageFacade {
    private final ImageStorage imageStorage;
    private final ImageRepository imageRepository;

    public ImageFacadeImpl(ImageStorage imageStorage, ImageRepository imageRepository) {
        this.imageStorage = imageStorage;
        this.imageRepository = imageRepository;
    }

    @Override
    public Optional<ImageDTO> addImage(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        File file = convertMultiPartToFile(image);
        String url = imageStorage.uploadFile(file, generateFileName(image));
        if(!file.delete()){
            throw new IOException();
        }
        Image imageEntity = imageRepository.save(Image.builder()
                .originalFilename(originalFilename)
                .url(url)
                .build());
        return Optional.of(ImageMapper.toDto(imageEntity));
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
}
