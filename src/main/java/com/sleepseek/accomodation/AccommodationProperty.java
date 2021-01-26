package com.sleepseek.accomodation;

import com.sleepseek.common.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class AccommodationProperty extends BaseEntity {
    @Column
    private String name;

    @ManyToOne
    private Accommodation accommodation;
}
