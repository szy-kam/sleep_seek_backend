package com.sleepseek.stay;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class StaySearchParameters {
    private Integer pageNumber;
    private Integer pageSize;
    private String username;
    private String searchString;

}
