package com.sleepseek.stay;

import com.sleepseek.image.ImageFacade;
import com.sleepseek.user.UserFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StayFacadeTest {

    private static final String VALID_NAME = "U Basi";
    private static final String VALID_USERNAME = "Admin";
    private static final String VALID_DESCRIPTION = "Super obiekt polecamy";
    private static final Long VALID_MIN_PRICE = 23L;
    private static final String PHONE_NUMBER = "123456789";
    private static final String EMAIL = "some@email.com";
    private static final String MAIN_PHOTO = "https://url1.com";
    private static final String CATEGORY = StayCategory.PENSION.getName();
    private final List<String> VALID_PHOTOS = Arrays.asList("https://url1.com", "https://url2.com");
    private final List<String> VALID_PROPERTIES = Arrays.asList(StayProperty.BAR.getName(), StayProperty.RECEPTION24H.getName());


    private StayFacade stayFacade;

    @Mock
    private StayRepositoryAdapter stayRepository;

    @Mock
    private ImageFacade imageFacade;
    @Mock
    private UserFacade userFacade;

    @BeforeEach
    void initStayFacade() {
        stayFacade = new StayFacadeImpl(stayRepository, imageFacade, userFacade);
    }
}
