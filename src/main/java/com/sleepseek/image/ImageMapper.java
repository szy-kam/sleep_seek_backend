package com.sleepseek.image;

import com.sleepseek.image.DTO.ImageDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ImageMapper {

    public static ImageDTO toDto(Image image){
        return ImageDTO.builder()
                .id(image.getId())
                .url(image.getUrl())
                .originalFilename(image.getOriginalFilename())
                .build();
    }
}
