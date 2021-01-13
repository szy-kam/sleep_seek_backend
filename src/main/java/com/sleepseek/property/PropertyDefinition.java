package com.sleepseek.property;

import com.sleepseek.common.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDefinition extends BaseEntity {

    @Column(unique = true)
    private String name;

    @Column
    private String type;

    @Column
    private String ico;
}
