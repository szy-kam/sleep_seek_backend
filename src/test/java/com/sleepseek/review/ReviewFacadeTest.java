package com.sleepseek.review;

import com.sleepseek.review.DTO.ReviewDTO;
import com.sleepseek.review.exceptions.ReviewNotFoundException;
import com.sleepseek.review.exceptions.ReviewValidationException;
import com.sleepseek.stay.StayFacade;
import com.sleepseek.stay.exception.StayNotFoundException;
import com.sleepseek.user.UserFacade;
import com.sleepseek.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ReviewFacadeTest {

    private static final Long NOT_EXISTING_REVIEW_ID = 1L;

    private static final Long NOT_EXISTING_STAY_ID = 3L;
    private static final String NOT_EXISTING_USERNAME = "dontExist@mail.com";
    private static final Long VALID_STAY_ID = 2L;
    private static final String VALID_USERNAME = "test@test.com";
    private static final String VALID_MESSAGE = "Stay was brilliant. Food was very good.";
    private static final Double VALID_RATING = 2.0;
    private static final Double INVALID_RATING = -1.0;
    private static final Long VALID_REVIEW_ID = 4L;


    @Mock
    private StayFacade stayFacade;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserFacade userFacade;

    private ReviewFacade reviewFacade;

    @BeforeEach
    void initReviewFacade() {
        reviewFacade = new ReviewFacadeImpl(stayFacade, reviewRepository, userFacade);
    }

    private void addReview(Long stayId, String username, String message, Double rating) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .message(message)
                .stayId(stayId)
                .rating(rating)
                .username(username)
                .build();
        reviewFacade.addReview(reviewDTO);
    }

    private void updateReview(Long reviewId, Long stayId, String username, String message, Double rating) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewId(reviewId)
                .username(username)
                .rating(rating)
                .stayId(stayId)
                .message(message)
                .build();
        reviewFacade.updateReview(reviewDTO);
    }

    @Test
    void deleteReview_reviewNotExist_throwsExceptionReviewNotFound() {
        assertThrows(ReviewNotFoundException.class, () -> reviewFacade.deleteReview(NOT_EXISTING_REVIEW_ID));
    }

    @Test
    void addReview_nullStayIdNullUsernameNullMessageNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(null, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullUsernameNullMessageNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(VALID_STAY_ID, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullStayIdNullMessageNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(null, VALID_USERNAME, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullStayIdNullUsernameNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(null, null, VALID_MESSAGE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullStayIdNullUsernameNullMessage_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(null, null, null, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullStayIdNullUsername_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(null, null, VALID_MESSAGE, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullStayIdNullMessage_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(null, VALID_USERNAME, null, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullUsernameNullMessage_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(VALID_STAY_ID, null, null, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullStayIdNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(null, VALID_USERNAME, VALID_MESSAGE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullUsernameNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(VALID_STAY_ID, null, VALID_MESSAGE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullMessageNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(VALID_STAY_ID, VALID_USERNAME, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.MESSAGE_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(VALID_STAY_ID, VALID_USERNAME, VALID_MESSAGE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullMessage_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(VALID_STAY_ID, VALID_USERNAME, null, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.MESSAGE_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullUsername_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(VALID_STAY_ID, null, VALID_MESSAGE, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.USER_NULL.getMessage()
                ));
    }

    @Test
    void addReview_nullStayId_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(null, VALID_USERNAME, VALID_MESSAGE, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage()
                ));
    }

    @Test
    void addReview_invalidRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> addReview(VALID_STAY_ID, VALID_USERNAME, VALID_MESSAGE, INVALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.RATING_OUT_OF_BOUNDS.getMessage()
                ));
    }

    @Test
    void addReview_userNotFound_throwsExceptionUserNotFound() {
        Mockito.when(userFacade.userExists(NOT_EXISTING_USERNAME)).thenReturn(false);
        Mockito.when(stayFacade.stayExists(VALID_STAY_ID)).thenReturn(true);
        assertThrows(UserNotFoundException.class, () -> addReview(VALID_STAY_ID, NOT_EXISTING_USERNAME, VALID_MESSAGE, VALID_RATING));

    }

    @Test
    void addReview_stayNotFound_throwsExceptionStayNotFound() {
        Mockito.when(stayFacade.stayExists(NOT_EXISTING_STAY_ID)).thenReturn(false);
        assertThrows(StayNotFoundException.class, () -> addReview(NOT_EXISTING_STAY_ID, VALID_USERNAME, VALID_MESSAGE, VALID_RATING));

    }

    @Test
    void updateReview_nullStayIdNullUsernameNullMessageNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, null, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullUsernameNullMessageNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, VALID_STAY_ID, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullStayIdNullMessageNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, null, VALID_USERNAME, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullStayIdNullUsernameNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, null, null, VALID_MESSAGE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullStayIdNullUsernameNullMessage_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, null, null, null, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullStayIdNullUsername_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, null, null, VALID_MESSAGE, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullStayIdNullMessage_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, null, VALID_USERNAME, null, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullUsernameNullMessage_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, VALID_STAY_ID, null, null, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullStayIdNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, null, VALID_USERNAME, VALID_MESSAGE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullUsernameNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, VALID_STAY_ID, null, VALID_MESSAGE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullMessageNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, VALID_STAY_ID, VALID_USERNAME, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.MESSAGE_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, VALID_STAY_ID, VALID_USERNAME, VALID_MESSAGE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullMessage_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, VALID_STAY_ID, VALID_USERNAME, null, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.MESSAGE_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullUsername_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, VALID_STAY_ID, null, VALID_MESSAGE, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.USER_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullStayId_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, null, VALID_USERNAME, VALID_MESSAGE, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.STAY_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_invalidRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(VALID_REVIEW_ID, VALID_STAY_ID, VALID_USERNAME, VALID_MESSAGE, INVALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.RATING_OUT_OF_BOUNDS.getMessage()
                ));
    }

    @Test
    void updateReview_userNotFound_throwsExceptionUserNotFound() {
        Mockito.when(userFacade.userExists(NOT_EXISTING_USERNAME)).thenReturn(false);
        Mockito.when(stayFacade.stayExists(VALID_STAY_ID)).thenReturn(true);
        assertThrows(UserNotFoundException.class, () -> updateReview(VALID_REVIEW_ID, VALID_STAY_ID, NOT_EXISTING_USERNAME, VALID_MESSAGE, VALID_RATING));

    }

    @Test
    void updateReview_stayNotFound_throwsExceptionStayNotFound() {
        assertThrows(StayNotFoundException.class, () -> updateReview(VALID_REVIEW_ID, NOT_EXISTING_STAY_ID, VALID_USERNAME, VALID_MESSAGE, VALID_RATING));
    }

    @Test
    void updateReview_nullReviewIdNullStayIdNullUsernameNullMessageNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, null, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullUsernameNullMessageNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, VALID_STAY_ID, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullStayIdNullMessageNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, null, VALID_USERNAME, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullStayIdNullUsernameNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, null, null, VALID_MESSAGE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullStayIdNullUsernameNullMessage_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, null, null, null, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullStayIdNullUsername_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, null, null, VALID_MESSAGE, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullStayIdNullMessage_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, null, VALID_USERNAME, null, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullUsernameNullMessage_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, VALID_STAY_ID, null, null, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullStayIdNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, null, VALID_USERNAME, VALID_MESSAGE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.STAY_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullUsernameNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, VALID_STAY_ID, null, VALID_MESSAGE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullMessageNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, VALID_STAY_ID, VALID_USERNAME, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullRating_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, VALID_STAY_ID, VALID_USERNAME, VALID_MESSAGE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.RATING_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullMessage_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, VALID_STAY_ID, VALID_USERNAME, null, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.MESSAGE_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullUsername_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, VALID_STAY_ID, null, VALID_MESSAGE, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.USER_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_nullReviewIdNullStayId_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(ReviewValidationException.class, () -> updateReview(null, null, VALID_USERNAME, VALID_MESSAGE, VALID_RATING));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        ReviewErrorCodes.ID_NULL.getMessage(),
                        ReviewErrorCodes.STAY_NULL.getMessage()
                ));
    }

    @Test
    void updateReview_reviewNotFound_throwsExceptionStayNotFound() {
        Mockito.when(stayFacade.stayExists(VALID_STAY_ID)).thenReturn(true);
        Mockito.when(userFacade.userExists(VALID_USERNAME)).thenReturn(true);
        Mockito.when(reviewRepository.existsById(NOT_EXISTING_REVIEW_ID)).thenReturn(false);
        assertThrows(ReviewNotFoundException.class, () -> updateReview(NOT_EXISTING_REVIEW_ID, VALID_STAY_ID, VALID_USERNAME, VALID_MESSAGE, VALID_RATING));
    }


}
