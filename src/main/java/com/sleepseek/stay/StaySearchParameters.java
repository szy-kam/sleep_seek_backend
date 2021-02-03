package com.sleepseek.stay;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class StaySearchParameters {
    private Integer pageNumber;
    private Integer pageSize;
    private String username;
    private Double southWestLatitude;
    private Double southWestLongitude;
    private Double northEastLatitude;
    private Double northEastLongitude;
    private String name;
    private String category;
    private String city;
    private String country;
    private String sleepersCapacity;
    private List<String> property;
    private Long priceFrom;
    private Long priceTo;
    private String orderBy;
    private String order;
    private String dateFrom;
    private String dateTo;
}
