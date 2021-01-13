package com.sleepseek.accommodationProperty.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationPropertyDTO {
    private String name;
    private Long accommodationId;
}
