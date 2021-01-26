package com.sleepseek.stay;

import com.sleepseek.common.BaseEntity;
import com.sleepseek.image.Image;
import com.sleepseek.user.User;
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
    @Column
    private String name;

    @Embedded
    private Address address;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user")
    private User user;

    @Column(length = 10000)
    private String description;

    @Column
    private String minPrice;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private String mainPhoto;

    @ManyToOne
    private StayCategory category;

    @OneToMany(targetEntity = StayProperty.class)
    private List<StayProperty> properties;

    @OneToMany(targetEntity = Image.class)
    private List<Image> photos;

}
