package com.sleepseek.accommodationProperty;

import com.sleepseek.accomodation.Accommodation;
import com.sleepseek.common.BaseEntity;
import com.sleepseek.property.PropertyDefinition;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationProperty extends BaseEntity {
    @ManyToOne
    private PropertyDefinition property;

    @ManyToOne
    private Accommodation accommodation;
}
