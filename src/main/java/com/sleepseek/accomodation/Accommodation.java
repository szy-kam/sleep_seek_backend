package com.sleepseek.accomodation;

import com.sleepseek.common.BaseEntity;
import com.sleepseek.reservation.Reservation;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Accommodation extends BaseEntity {
    @Column(length = 40, nullable = false)
    private String alias;

    @ManyToOne
    @JoinColumn(name = "accommodationTemplate")
    AccommodationTemplate accommodationTemplate;

    @OneToMany(targetEntity = Reservation.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Reservation> reservations;

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setAccommodation(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setAccommodation(null);
    }
}
