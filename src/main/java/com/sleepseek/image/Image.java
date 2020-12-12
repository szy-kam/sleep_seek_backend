package com.sleepseek.image;

import com.sleepseek.common.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "images")
public class Image extends BaseEntity {
    @Column(name = "url")
    private String url;

    @Column(name = "original_filename")
    private String originalFilename;
}
