package com.sleepseek.review;

import com.sleepseek.review.DTO.ReviewDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
class ReviewController {
    private final ReviewFacade reviewFacade;


    ReviewController(ReviewFacade reviewFacade) {
        this.reviewFacade = reviewFacade;
    }

    @GetMapping("/review")
    List<ReviewDTO> getReviews(@RequestParam Long stayId, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return reviewFacade.findByStayId(stayId, pageable);
    }

    @PostMapping("/review")
    void addReview(@RequestParam Long stayId, Principal principal, @RequestBody ReviewDTO reviewDTO) {
        reviewDTO.setUsername(principal.getName());
        reviewDTO.setStayId(stayId);
        reviewFacade.addReview(reviewDTO);
    }

    @PutMapping("/review/{reviewId}")
    void updateReview(@PathVariable Long reviewId, @RequestBody ReviewDTO reviewDTO) {
        reviewDTO.setReviewId(reviewId);
        reviewFacade.updateReview(reviewDTO);
    }

    @DeleteMapping("/review/{reviewId}")
    void deleteReview(@PathVariable Long reviewId) {
        reviewFacade.deleteReview(reviewId);
    }
}
