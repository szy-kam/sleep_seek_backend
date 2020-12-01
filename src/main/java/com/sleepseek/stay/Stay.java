package com.sleepseek.stay;

import com.sleepseek.common.BaseEntity;
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
class Stay extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Embedded
    private Address address;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "contactInfo")
    private String contactInfo;

    @Column(name = "mainPhoto")
    private Long mainPhoto;

    @ElementCollection
    private List<Long> images;

}
