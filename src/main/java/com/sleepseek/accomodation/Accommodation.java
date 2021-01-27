package com.sleepseek.accomodation;

import com.sleepseek.common.BaseEntity;
import com.sleepseek.reservation.Reservation;
import com.sleepseek.stay.Stay;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Accommodation extends BaseEntity {
    @Column
    private Long sleepersCapacity;

    @Column
    private Long quantity;

    @Column
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Stay stay;

    @ElementCollection(targetClass = AccommodationProperty.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "accommodation_properties")
    @Column(name = "properties")
    private Set<AccommodationProperty> properties;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setAccommodation(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setAccommodation(null);
    }
}
