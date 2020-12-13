package com.sleepseek.stay;

import com.sleepseek.common.BaseEntity;
import com.sleepseek.image.Image;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stays")
public class Stay extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Embedded
    private Address address;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "description")
    private String description;

    @Column(name = "minPrice")
    private String minPrice;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "mainPhoto")
    private Long mainPhoto;

    @OneToMany(targetEntity = Image.class)
    private List<Image> photos;

}
