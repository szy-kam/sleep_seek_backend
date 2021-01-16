package com.sleepseek.review.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {

    private Long reviewId;

    private Long stayId;

    private String username;

    private String message;

    private Double rating;

}
