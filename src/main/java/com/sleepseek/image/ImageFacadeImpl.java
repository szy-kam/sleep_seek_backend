package com.sleepseek.image;

import com.google.common.collect.Sets;
import com.sleepseek.image.DTO.ImageDTO;
import com.sleepseek.image.exception.ImageNotFoundException;
import com.sleepseek.image.exception.ImageStorageException;
import com.sleepseek.image.exception.ImageValidationException;
import com.sleepseek.user.UserFacade;
import com.sleepseek.user.exception.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static com.sleepseek.image.ImageErrorCodes.FILE_NULL;
import static com.sleepseek.image.ImageErrorCodes.USER_NULL;
import static java.util.Objects.isNull;

class ImageFacadeImpl implements ImageFacade {
    private final ImageStorage imageStorage;
    private final ImageRepository imageRepository;
    private final UserFacade userFacade;

    ImageFacadeImpl(ImageStorage imageStorage, ImageRepository imageRepository, UserFacade userFacade) {
        this.imageStorage = imageStorage;
        this.imageRepository = imageRepository;
        this.userFacade = userFacade;
    }

    @Override
    public ImageDTO addImage(String username, MultipartFile image) throws IOException {
        Set<ImageErrorCodes> errors = Sets.newHashSet();
        checkUsername(username).ifPresent(errors::add);
        checkImage(image).ifPresent(errors::add);
        if (!errors.isEmpty()) {
            throw new ImageValidationException(errors);
        }
        if (!userFacade.userExists(username)) {
            throw new UserNotFoundException(username);
        }
        File file = convertMultiPartToFile(image);
        String originalFilename = image.getOriginalFilename();
        String url = imageStorage.uploadFile(file, generateFileName(image));
        if (!file.delete()) {
            throw new ImageStorageException("errors on delete local temp file");
        }

        Image imageEntity = imageRepository.save(Image.builder()
                .originalFilename(originalFilename)
                .owner(userFacade.getUserByUsername(username))
                .url(url)
                .build());
        return ImageMapper.toDto(imageEntity);
    }

    private Optional<ImageErrorCodes> checkImage(MultipartFile image) {
        if (isNull(image)) {
            return Optional.of(FILE_NULL);
        }
        return Optional.empty();
    }

    @Override
    public Image findImage(String url) {
        return imageRepository.findByUrl(url).stream().findAny().orElseThrow(() -> new ImageNotFoundException(url));
    }

    private Optional<ImageErrorCodes> checkUsername(String username) {
        if (isNull(username)) {
            return Optional.of(USER_NULL);
        }
        return Optional.empty();
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
