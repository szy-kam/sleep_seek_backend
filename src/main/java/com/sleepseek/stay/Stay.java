package com.sleepseek.stay;

import com.sleepseek.accomodation.Accommodation;
import com.sleepseek.common.BaseEntity;
import com.sleepseek.image.Image;
import com.sleepseek.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Long minPrice;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private String mainPhoto;

    @ElementCollection(targetClass = StayPropertyDefinition.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "stay_properties")
    @Column(name = "properties")
    private Set<StayPropertyDefinition> properties;

    @Enumerated(EnumType.STRING)
    @Column
    private StayCategoryDefinition category;

    @OneToMany(targetEntity = Image.class)
    private List<Image> photos = new ArrayList<>();

    @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Accommodation> accommodations = new HashSet<>();

    public void addAccommodation(Accommodation accommodation) {
        accommodations.add(accommodation);
        accommodation.setStay(this);
    }

    public void removeAccommodation(Accommodation accommodation) {
        accommodations.remove(accommodation);
        accommodation.setStay(null);
    }

}