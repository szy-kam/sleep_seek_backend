package com.sleepseek.review;

import com.sleepseek.review.DTO.ReviewDTO;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class ReviewController {
    private final ReviewFacade reviewFacade;


    public ReviewController(ReviewFacade reviewFacade) {
        this.reviewFacade = reviewFacade;
    }

    @GetMapping("/review")
    public List<ReviewDTO> getReviews(@RequestParam Long stayId){
        return reviewFacade.findByStayId(stayId);
    }

    @PostMapping("/review")
    public ReviewDTO addReview(@RequestParam Long stayId, Principal principal, @RequestBody ReviewDTO reviewDTO){
        reviewDTO.setUsername(principal.getName());
        reviewDTO.setStayId(stayId);
        return reviewFacade.addReview(reviewDTO);
    }
}
