package com.sleepseek.review;

import com.sleepseek.review.DTO.ReviewDTO;
import com.sleepseek.stay.StayFacade;
import com.sleepseek.user.UserFacade;

import java.util.List;
import java.util.stream.Collectors;

class ReviewFacadeImpl implements ReviewFacade {
    private final StayFacade stayFacade;
    private final ReviewRepository reviewRepository;
    private final UserFacade userFacade;

    ReviewFacadeImpl(StayFacade stayFacade, ReviewRepository reviewRepository, UserFacade userFacade) {
        this.stayFacade = stayFacade;
        this.reviewRepository = reviewRepository;
        this.userFacade = userFacade;
    }


    @Override
    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        Review newReview = reviewRepository.save(
                Review.builder()
                        .message(reviewDTO.getMessage())
                        .rating(reviewDTO.getRating())
                        .user(userFacade.getUserByUsername(reviewDTO.getUsername()))
                        .stay(stayFacade.loadStay(reviewDTO.getStayId()))
                        .build()
        );
        return ReviewMapper.toDTO(newReview);
    }

    @Override
    public void updateReview(ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewDTO.getReviewId()).orElseThrow();
        review.setMessage(reviewDTO.getMessage());
        review.setRating(reviewDTO.getRating());
        review.setUser(userFacade.getUserByUsername(reviewDTO.getUsername()));
        review.setStay(stayFacade.loadStay(reviewDTO.getStayId()));
    }

    @Override
    public boolean existsById(Long id) {
        return reviewRepository.existsById(id);
    }

    @Override
    public List<ReviewDTO> findByStayId(Long stayId) {
        return reviewRepository.findByStay(stayFacade.loadStay(stayId)).stream().map(ReviewMapper::toDTO).collect(Collectors.toList());
    }
}
