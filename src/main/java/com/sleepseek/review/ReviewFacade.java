package com.sleepseek.review;

import com.sleepseek.review.DTO.ReviewDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewFacade {
    void addReview(ReviewDTO reviewDTO);

    void updateReview(ReviewDTO reviewDTO);

    void deleteReview(Long id);

    boolean existsById(Long id);

    List<ReviewDTO> findByStayId(Long stayId, Pageable pageable);
}
