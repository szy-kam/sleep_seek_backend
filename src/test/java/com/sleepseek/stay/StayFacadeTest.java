package com.sleepseek.stay;

import com.sleepseek.accomodation.AccommodationRepository;
import com.sleepseek.image.ImageFacade;
import com.sleepseek.user.UserFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StayFacadeTest {

    private StayFacade stayFacade;

    @Mock
    private StayRepository stayRepository;

    @Mock
    private ImageFacade imageFacade;
    @Mock
    private UserFacade userFacade;

    @Mock
    private AccommodationRepository accommodationRepository;

    @BeforeEach
    void initStayFacade() {
        stayFacade = new StayFacadeImpl(stayRepository, imageFacade, userFacade, accommodationRepository);
    }
}
