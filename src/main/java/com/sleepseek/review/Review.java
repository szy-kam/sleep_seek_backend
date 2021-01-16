package com.sleepseek.review;

import com.sleepseek.common.BaseEntity;
import com.sleepseek.stay.Stay;
import com.sleepseek.user.User;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review extends BaseEntity {

    @ManyToOne
    private Stay stay;

    @ManyToOne
    private User user;

    @Column
    private String message;

    @Column
    private Double rating;
}
