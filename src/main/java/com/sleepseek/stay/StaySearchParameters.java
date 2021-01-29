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
    private Double latitude;
    private Double longitude;
    private String name;
    private String category;
    private String city;
    private List<String> property;
    private Long priceFrom;
    private Long priceTo;
    private String orderBy;
    private String order;
    private Double maxDistance;
    private String dateFrom;
    private String dateTo;
}
