package com.sleepseek.reservation;

import com.sleepseek.accomodation.AccommodationFacade;
import com.sleepseek.stay.StayFacade;
import com.sleepseek.user.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReservationConfiguration {

    @Bean
    ReservationFacade reservationFacade(ReservationRepository reservationRepository, AccommodationFacade accommodationFacade, StayFacade stayFacade, UserFacade userFacade) {
        return new ReservationFacadeImpl(reservationRepository, accommodationFacade, stayFacade, userFacade);
    }
}
