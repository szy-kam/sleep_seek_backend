package com.sleepseek.review;

import com.sleepseek.review.DTO.ReviewDTO;

public class ReviewMapper {

    public static ReviewDTO toDTO(Review review) {
        return ReviewDTO.builder()
                .reviewId(review.getId())
                .username(review.getUser().getUsername())
                .stayId(review.getStay().getId())
                .message(review.getMessage())
                .rating(review.getRating())
                .build();
    }
}
