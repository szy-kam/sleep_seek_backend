package com.sleepseek.review;

import com.sleepseek.review.DTO.ReviewDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewFacade {
    ReviewDTO addReview(ReviewDTO reviewDTO);

    void updateReview(ReviewDTO reviewDTO);

    boolean existsById(Long id);

    List<ReviewDTO> findByStayId(Long stayId, Pageable pageable);
}
