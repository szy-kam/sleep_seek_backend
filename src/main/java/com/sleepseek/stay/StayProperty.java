package com.sleepseek.stay;

import com.sleepseek.common.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StayProperty extends BaseEntity {
    @Column
    private String name;

    @ManyToOne
    private Stay stay;
}
