package com.sleepseek.user;

import com.google.common.collect.Sets;
import com.sleepseek.user.DTO.UserDTO;
import com.sleepseek.user.exception.UserAlreadyExistsException;
import com.sleepseek.user.exception.UserNotFoundException;
import com.sleepseek.user.exception.UserValidationException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static com.sleepseek.user.UserErrorCodes.*;
import static java.util.Objects.isNull;

class UserFacadeImpl implements UserFacade {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserFacadeImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void postUser(UserDTO userDTO) {
        Set<UserErrorCodes> errorCodes = Sets.newHashSet();
        checkUsernameErrors(userDTO.getUsername()).ifPresent(errorCodes::add);
        checkDisplayNameErrors(userDTO.getDisplayName()).ifPresent(errorCodes::add);
        checkPasswordErrors(userDTO.getPassword()).ifPresent(errorCodes::add);
        if(!errorCodes.isEmpty()){
            throw new UserValidationException(errorCodes);
        }

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsException(userDTO.getUsername());
        } else {
            userRepository.save(User.builder()
                    .username(userDTO.getUsername())
                    .displayName(userDTO.getDisplayName())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build());
        }
    }

    private Optional<UserErrorCodes> checkPasswordErrors(String password) {
        if (isNull(password)) {
            return Optional.of(PASSWORD_IS_NULL);
        }
        return Optional.empty();
    }


    private Optional<UserErrorCodes> checkDisplayNameErrors(String displayName) {
        if (isNull(displayName)) {
            return Optional.of(DISPLAY_NAME_IS_NULL);
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
        return Optional.empty();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(UserMapper::toDetails).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
