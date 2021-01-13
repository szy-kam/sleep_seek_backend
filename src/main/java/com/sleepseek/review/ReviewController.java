package com.sleepseek.review;

import com.sleepseek.review.DTO.ReviewDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {
    private final ReviewFacade reviewFacade;


    public ReviewController(ReviewFacade reviewFacade) {
        this.reviewFacade = reviewFacade;
    }

    @GetMapping(name = "/review/{stayId]")
    public List<ReviewDTO> getReviews(@PathVariable Long stayId){
        return reviewFacade.findByStayId(stayId);
    }

    @PostMapping("/review/{stayId}")
    public ReviewDTO addReview(@PathVariable Long stayId, ReviewDTO reviewDTO){
        return reviewFacade.addReview(reviewDTO);
    }
}
