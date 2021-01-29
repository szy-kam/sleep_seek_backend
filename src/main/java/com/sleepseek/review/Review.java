package com.sleepseek.review;

import com.sleepseek.common.BaseEntity;
import com.sleepseek.stay.Stay;
import com.sleepseek.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stay_id", nullable = false)
    private Stay stay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "message", length = 1000)
    private String message;

    @Column(name = "rating")
    private Double rating;
}
