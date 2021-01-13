package com.sleepseek.accomodation.DTO;

import lombok.*;

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
    private String price;

}
