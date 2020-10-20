package com.sleepseek.stay;

import com.sleepseek.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stays")
class Stay extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Embedded
    private Address address;

    @Column(name = "userId")
    private String userId;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "contactInfo")
    private String contactInfo;

    @Column(name = "mainPhoto")
    private String mainPhoto;

    @Column(name = "properties")
    private String properties;
}
