package com.sleepseek.review;

import com.sleepseek.review.DTO.ReviewDTO;

import java.util.List;

public interface ReviewFacade {
    ReviewDTO addReview(ReviewDTO reviewDTO);

    void updateReview(ReviewDTO reviewDTO);

    boolean existsById(Long id);

    List<ReviewDTO> findByStayId(Long stayId);
}
