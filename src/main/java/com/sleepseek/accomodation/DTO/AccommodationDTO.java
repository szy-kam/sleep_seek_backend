package com.sleepseek.accomodation.DTO;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDTO {
    private Long id;
    private Long stayId;
    private Long sleepersCapacity;
    private Long quantity;
    private Long price;
    private List<String> properties;
}
