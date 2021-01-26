package com.sleepseek.stay;

import com.sleepseek.common.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StayCategory extends BaseEntity {
    @Column
    private String name;

    @ManyToOne
    private Stay stay;
}
