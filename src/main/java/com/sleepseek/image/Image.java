package com.sleepseek.image;

import com.sleepseek.common.BaseEntity;
import com.sleepseek.stay.Stay;
import com.sleepseek.user.User;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "images")
public class Image extends BaseEntity {
    @Column
    private String url;

    @Column
    private String originalFilename;

    @ManyToOne
    private Stay stay;

    @ManyToOne
    private User owner;
}
