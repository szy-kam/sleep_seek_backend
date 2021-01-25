package com.sleepseek.user;

import com.sleepseek.user.DTO.UserDTO;
import com.sleepseek.user.exception.UserAlreadyExistsException;
import com.sleepseek.user.exception.UserNotFoundException;
import com.sleepseek.user.exception.UserValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {

    private static final String INVALID_EMAIL = "invalid@..s";
    private static final String VALID_DISPLAY_NAME = "John Smith";
    private static final String VALID_EMAIL = "test@mail.com";
    private static final String VALID_PASSWORD = "password";
    private static final String TOO_LONG_PASSWORD = "1234567890123456789012345678901234567890";

    private UserFacade userFacade;

    @Mock
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void initUserFacade() {
        userFacade = new UserFacadeImpl(userRepository, passwordEncoder);
    }

    private void postUser(String email, String password, String displayName) {
        var user = UserDTO.builder().username(email).password(password).displayName(displayName).build();
        userFacade.postUser(user);
    }

    @Test
    void postUser_nullUsernameNullPasswordNullDisplayName_throwsExceptionWithUserErrorCodes() {
        var exception = assertThrows(UserValidationException.class, () -> postUser(null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(UserErrorCodes.DISPLAY_NAME_IS_NULL.getMessage(),
                        UserErrorCodes.PASSWORD_IS_NULL.getMessage(),
                        UserErrorCodes.USERNAME_IS_NULL.getMessage()));
    }

    @Test
    void postUser_nullUsernameNullPassword_throwsExceptionWithUserErrorCodes() {
        var exception = assertThrows(UserValidationException.class, () -> postUser(null, null, VALID_DISPLAY_NAME));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        UserErrorCodes.PASSWORD_IS_NULL.getMessage(),
                        UserErrorCodes.USERNAME_IS_NULL.getMessage()));
    }

    @Test
    void postUser_nullUsernameNullDisplayName_throwsExceptionWithUserErrorCodes() {
        var exception = assertThrows(UserValidationException.class, () -> postUser(null, VALID_PASSWORD, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(UserErrorCodes.DISPLAY_NAME_IS_NULL.getMessage(),
                        UserErrorCodes.USERNAME_IS_NULL.getMessage()));
    }

    @Test
    void postUser_nullPasswordNullDisplayName_throwsExceptionWithUserErrorCodes() {
        var exception = assertThrows(UserValidationException.class, () -> postUser(VALID_EMAIL, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(UserErrorCodes.DISPLAY_NAME_IS_NULL.getMessage(),
                        UserErrorCodes.PASSWORD_IS_NULL.getMessage()));
    }

    @Test
    void postUser_nullUsername_throwsExceptionWithUserErrorCodes() {
        var exception = assertThrows(UserValidationException.class, () -> postUser(null, VALID_PASSWORD, VALID_DISPLAY_NAME));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(UserErrorCodes.USERNAME_IS_NULL.getMessage()
                ));
    }

    @Test
    void postUser_nullPassword_throwsExceptionWithUserErrorCodes() {
        var exception = assertThrows(UserValidationException.class, () -> postUser(VALID_EMAIL, null, VALID_DISPLAY_NAME));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        UserErrorCodes.PASSWORD_IS_NULL.getMessage()));
    }

    @Test
    void postUser_nullDisplayName_throwsExceptionWithUserErrorCodes() {
        var exception = assertThrows(UserValidationException.class, () -> postUser(VALID_EMAIL, VALID_PASSWORD, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(UserErrorCodes.DISPLAY_NAME_IS_NULL.getMessage()
                ));
    }

    @Test
    void postUser_usernameIsNotEmail_throwsExceptionWithUserErrorCodes() {
        var exception = assertThrows(UserValidationException.class, () -> postUser(INVALID_EMAIL, VALID_PASSWORD, VALID_DISPLAY_NAME));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        UserErrorCodes.USERNAME_IS_NOT_EMAIL.getMessage()));
    }

    @Test
    void postUser_tooLongPassword_throwsExceptionWithUserErrorCodes() {
        var exception = assertThrows(UserValidationException.class, () -> postUser(INVALID_EMAIL, TOO_LONG_PASSWORD, VALID_DISPLAY_NAME));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        UserErrorCodes.PASSWORD_IS_TOO_LONG.getMessage()));
    }

    @Test
    void postUser_userExists() {
        UserDTO newUser = UserDTO.builder().displayName(VALID_DISPLAY_NAME).username(VALID_EMAIL).password(VALID_PASSWORD).build();
        Mockito.when(userRepository.existsByUsername(VALID_EMAIL)).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> userFacade.postUser(newUser));
    }

    @Test
    void getUserByUsername_usedDoesntExist_throwsExceptionUserNotFound() {
        Mockito.when(userRepository.findByUsername(VALID_EMAIL)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userFacade.getUserByUsername(VALID_EMAIL));
    }

}
