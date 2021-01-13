package com.sleepseek.accomodation;

import com.sleepseek.common.BaseEntity;
import com.sleepseek.stay.Stay;
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
class Accommodation extends BaseEntity {

    @ManyToOne
    private Stay stay;

    @Column
    private Long sleepersCapacity;

    @Column
    private Long quantity;

    @Column
    private String price;

}
