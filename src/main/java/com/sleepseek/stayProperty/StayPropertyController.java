package com.sleepseek.stayProperty;

import com.sleepseek.stayProperty.DTO.StayPropertyDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StayPropertyController {
    private final StayPropertyFacade stayPropertyFacade;

    public StayPropertyController(StayPropertyFacade stayPropertyFacade) {
        this.stayPropertyFacade = stayPropertyFacade;
    }

    @GetMapping("/stayProperty")
    List<StayPropertyDTO> getByStayId(@RequestParam Long stayId){
        return stayPropertyFacade.findByStay(stayId);
    }

    @PostMapping("/stayProperty")
    StayPropertyDTO add(StayPropertyDTO stayPropertyDTO){
        return stayPropertyFacade.add(stayPropertyDTO);
    }
}
