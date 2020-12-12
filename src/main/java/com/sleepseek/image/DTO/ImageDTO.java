package com.sleepseek.image.DTO;

import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private Long id;
    private String url;
    @Nullable
    private String originalFilename;
}
