package com.sleepseek.stayProperty;

import com.sleepseek.stayProperty.DTO.StayPropertyDTO;

import java.util.List;

public interface StayPropertyFacade {

    StayPropertyDTO add(StayPropertyDTO stayPropertyDTO);

    void delete(Long id);

    List<StayPropertyDTO> findByStay(Long stayId);

}
