package com.sleepseek.reservation;

import com.sleepseek.accomodation.Accommodation;
import com.sleepseek.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private Accommodation accommodation;

    @Embedded
    private Customer customer;

    @Column
    private LocalDate dateFrom;

    @Column
    private LocalDate dateTo;

    @Column
    private Boolean confirmed;

    @Column
    private Boolean completed;

}
