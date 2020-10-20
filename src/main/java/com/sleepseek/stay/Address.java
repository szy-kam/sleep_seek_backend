package com.sleepseek.stay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Address {
    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "zipCode")
    private String zipCode;
}
