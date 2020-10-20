package com.sleepseek.stay;

import com.sleepseek.stay.DTO.StayDTO;

import java.util.List;

public interface StayFacade {
    void addStay(StayDTO stayDTO);

    StayDTO getStay(Long id);

    boolean stayExists(Long id);

    List<StayDTO> getStays(Long from, Long to);
}
