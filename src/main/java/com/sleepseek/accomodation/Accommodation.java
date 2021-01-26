package com.sleepseek.accomodation;

import com.sleepseek.common.BaseEntity;
import com.sleepseek.stay.Stay;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Accommodation extends BaseEntity {

    @ManyToOne
    private Stay stay;

    @Column
    private Long sleepersCapacity;

    @Column
    private Long quantity;

    @Column
    private Long price;

    @OneToMany(targetEntity = AccommodationProperty.class)
    private List<AccommodationProperty> properties;

}
