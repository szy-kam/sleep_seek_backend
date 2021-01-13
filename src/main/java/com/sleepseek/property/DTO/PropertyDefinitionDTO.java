package com.sleepseek.property.DTO;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyDefinitionDTO {
    private Long id;
    private String name;
    private String type;
    private String ico;
}
