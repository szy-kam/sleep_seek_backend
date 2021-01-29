package com.sleepseek.stay;

import com.sleepseek.accomodation.Accommodation;
import com.sleepseek.review.Review;
import com.sleepseek.stay.DTO.StayDTO;

import java.util.List;

public interface StayFacade {
    StayDTO addStay(StayDTO stayDTO);

    void updateStay(StayDTO stayDTO);

    void addReview(Stay stay, Review review);

    void deleteStay(Long id);

    StayDTO getStay(Long id);

    Stay loadStay(Long id);

    boolean stayExists(Long id);

    List<StayDTO> getStays(StaySearchParameters searchParameters);

    void addAccommodation(Stay stay, Accommodation accommodation);

}
