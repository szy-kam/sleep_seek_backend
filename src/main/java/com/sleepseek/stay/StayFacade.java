package com.sleepseek.stay;

import com.sleepseek.stay.DTO.StayDTO;

import java.util.List;

public interface StayFacade {
    StayDTO addStay(StayDTO stayDTO);

    void updateStay(StayDTO stayDTO);

    void deleteStay(Long id);

    StayDTO getStay(Long id);

    Stay loadStay(Long id);

    boolean stayExists(Long id);

    List<StayDTO> getStays(StaySearchParameters searchParameters);

}
