package com.sleepseek.reservation;

import com.sleepseek.user.User;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String phoneNumber;

    @Column
    private String fullName;

}
