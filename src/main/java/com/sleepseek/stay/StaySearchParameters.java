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
    private String name;
    private StayCategory category;
    private String city;
    private String country;
    private List<StayProperty> property;
    private Long priceFrom;
    private Long priceTo;
    private Long dateFrom;
    private Long dateTo;
}
