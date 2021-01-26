package com.sleepseek.review;

import com.google.common.collect.Sets;
import com.sleepseek.review.DTO.ReviewDTO;
import com.sleepseek.review.exceptions.ReviewNotFoundException;
import com.sleepseek.review.exceptions.ReviewValidationException;
import com.sleepseek.stay.StayFacade;
import com.sleepseek.stay.exception.StayNotFoundException;
import com.sleepseek.user.UserFacade;
import com.sleepseek.user.exception.UserNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sleepseek.review.ReviewErrorCodes.*;
import static java.util.Objects.isNull;

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
        Set<ReviewErrorCodes> errors = validateReview(reviewDTO);

        validate(reviewDTO, errors);

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

    private Set<ReviewErrorCodes> validateReview(ReviewDTO reviewDTO) {
        Set<ReviewErrorCodes> errors = Sets.newHashSet();
        checkMessage(reviewDTO.getMessage()).ifPresent(errors::add);
        checkRating(reviewDTO.getRating()).ifPresent(errors::add);
        checkStay(reviewDTO.getStayId()).ifPresent(errors::add);
        checkUser(reviewDTO.getUsername()).ifPresent(errors::add);
        return errors;
    }

    private Optional<ReviewErrorCodes> checkUser(String username) {
        if (isNull(username)) {
            return Optional.of(USER_NULL);
        }
        return Optional.empty();
    }

    private Optional<ReviewErrorCodes> checkStay(Long stayId) {
        if (isNull(stayId)) {
            return Optional.of(STAY_NULL);
        }
        return Optional.empty();
    }

    private Optional<ReviewErrorCodes> checkMessage(String message) {
        if (isNull(message)) {
            return Optional.of(MESSAGE_NULL);
        }
        return Optional.empty();
    }

    private Optional<ReviewErrorCodes> checkRating(Double rating) {
        if (isNull(rating)) {
            return Optional.of(RATING_NULL);
        }
        if (rating < 0.0 || rating > 5.0) {
            return Optional.of(RATING_OUT_OF_BOUNDS);
        }
        return Optional.empty();
    }

    @Override
    public void updateReview(ReviewDTO reviewDTO) {
        Set<ReviewErrorCodes> errors = validateReview(reviewDTO);
        if (isNull(reviewDTO.getReviewId())) {
            errors.add(ID_NULL);
        }
        validate(reviewDTO, errors);
        if (!existsById(reviewDTO.getReviewId())) {
            throw new ReviewNotFoundException(reviewDTO.getReviewId());
        }
        Review review = reviewRepository.findById(reviewDTO.getReviewId()).orElseThrow();
        review.setMessage(reviewDTO.getMessage());
        review.setRating(reviewDTO.getRating());
        review.setUser(userFacade.getUserByUsername(reviewDTO.getUsername()));
        review.setStay(stayFacade.loadStay(reviewDTO.getStayId()));
    }

    private void validate(ReviewDTO reviewDTO, Set<ReviewErrorCodes> errors) {
        if (!errors.isEmpty()) {
            throw new ReviewValidationException(errors);
        }
        if (!stayFacade.stayExists(reviewDTO.getStayId())) {
            throw new StayNotFoundException(reviewDTO.getStayId());
        }
        if (!userFacade.userExists(reviewDTO.getUsername())) {
            throw new UserNotFoundException(reviewDTO.getUsername());
        }
    }

    @Override
    public void deleteReview(Long id) {
        if (!existsById(id)) {
            throw new ReviewNotFoundException(id);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return reviewRepository.existsById(id);
    }

    @Override
    public List<ReviewDTO> findByStayId(Long stayId, Pageable pageable) {
        return reviewRepository.findByStay(stayFacade.loadStay(stayId), pageable).stream().map(ReviewMapper::toDTO).collect(Collectors.toList());
    }
}
