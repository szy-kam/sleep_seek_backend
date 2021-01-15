package com.sleepseek.stayProperty;

import com.sleepseek.common.BaseEntity;
import com.sleepseek.property.PropertyDefinition;
import com.sleepseek.stay.Stay;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StayProperty extends BaseEntity {
    @ManyToOne
    private PropertyDefinition propertyDefinition;

    @ManyToOne
    private Stay stay;

}
