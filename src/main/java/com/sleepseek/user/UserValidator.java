package com.sleepseek.user;

import com.google.common.collect.Sets;
import com.sleepseek.user.DTO.UserDTO;
import com.sleepseek.user.exception.UserValidationException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Optional;
import java.util.Set;

import static com.sleepseek.user.UserErrorCodes.*;
import static java.util.Objects.isNull;

class UserValidator {
    private static final int PASSWORD_MAX_LENGTH = 32;
    private static final int USERNAME_MAX_LENGTH = 64;
    private static final int DISPLAY_NAME_MAX_LENGTH = 64;

    void validate(UserDTO userDTO) {
        Set<UserErrorCodes> errorCodes = Sets.newHashSet();
        checkUsernameErrors(userDTO.getUsername()).ifPresent(errorCodes::add);
        checkDisplayNameErrors(userDTO.getDisplayName()).ifPresent(errorCodes::add);
        checkPasswordErrors(userDTO.getPassword()).ifPresent(errorCodes::add);
        if (!errorCodes.isEmpty()) {
            throw new UserValidationException(errorCodes);
        }
    }

    private Optional<UserErrorCodes> checkPasswordErrors(String password) {
        if (isNull(password)) {
            return Optional.of(PASSWORD_IS_NULL);
        }
        if (password.length() > PASSWORD_MAX_LENGTH) {
            return Optional.of(PASSWORD_IS_TOO_LONG);
        }
        return Optional.empty();
    }


    private Optional<UserErrorCodes> checkDisplayNameErrors(String displayName) {
        if (isNull(displayName)) {
            return Optional.of(DISPLAY_NAME_IS_NULL);
        }
        if (displayName.length() > DISPLAY_NAME_MAX_LENGTH) {
            return Optional.of(DISPLAY_NAME_OUT_OF_BOUNDS);
        }
        return Optional.empty();
    }

    private Optional<UserErrorCodes> checkUsernameErrors(String username) {
        if (isNull(username)) {
            return Optional.of(USERNAME_IS_NULL);
        }
        if (!EmailValidator.getInstance().isValid(username)) {
            return Optional.of(USERNAME_IS_NOT_EMAIL);
        }
        if (username.length() > USERNAME_MAX_LENGTH) {
            return Optional.of(USERNAME_OUT_OF_BOUNDS);
        }
        return Optional.empty();
    }

}
