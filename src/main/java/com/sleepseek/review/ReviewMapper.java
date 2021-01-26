package com.sleepseek.review;

import com.sleepseek.review.DTO.ReviewDTO;

import java.time.format.DateTimeFormatter;

public class ReviewMapper {

    public static ReviewDTO toDTO(Review review) {
        return ReviewDTO.builder()
                .reviewId(review.getId())
                .username(review.getUser().getUsername())
                .stayId(review.getStay().getId())
                .message(review.getMessage())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
