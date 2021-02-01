package com.sleepseek.accomodation.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDTO {
    Long id;
    String alias;
    Long accommodationTemplateId;
}
