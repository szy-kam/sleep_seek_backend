package com.sleepseek.image;

import com.sleepseek.image.exception.ImageValidationException;
import com.sleepseek.user.UserFacade;
import com.sleepseek.user.exception.UserValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ImageFacadeTest {

    private static final String VALID_USERNAME = "test@test.com";
    
    private ImageFacade imageFacade;

    @Mock
    private UserFacade userFacade;

    @Mock
    private ImageStorage imageStorage;

    @Mock
    private ImageRepository imageRepository;

    @BeforeEach
    void initImageFacade() {
        imageFacade = new ImageFacadeImpl(imageStorage, imageRepository, userFacade);
    }

    @Test
    void addImage_nullFileNullUsername() {
        var exception = assertThrows(ImageValidationException.class, () -> imageFacade.addImage(null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(ImageErrorCodes.USER_NULL.getMessage(),
                        ImageErrorCodes.FILE_NULL.getMessage()
                ));
    }

    @Test
    void addImage_nullFile() {
        var exception = assertThrows(ImageValidationException.class, () -> imageFacade.addImage(VALID_USERNAME, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ImageErrorCodes.FILE_NULL.getMessage()
                ));
    }
}
