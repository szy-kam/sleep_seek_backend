package com.sleepseek.accomodation;

import com.sleepseek.common.BaseEntity;
import com.sleepseek.stay.Stay;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationTemplate extends BaseEntity {
    @Column(nullable = false)
    private String prefix;

    @Column(nullable = false)
    private Long sleepersCapacity;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long price;

    @OneToMany(mappedBy = "accommodationTemplate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Accommodation> accommodations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stay")
    private Stay stay;

    @ElementCollection(targetClass = AccommodationProperty.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "accommodation_properties")
    @Column(name = "properties")
    private Set<AccommodationProperty> properties;

    public void addAccommodation(Accommodation accommodation){
        accommodations.add(accommodation);
        accommodation.setAccommodationTemplate(this);
    }

    public void removeAccommodation(Accommodation accommodation){
        accommodation.setAccommodationTemplate(null);
        accommodations.remove(accommodation);
    }

}
