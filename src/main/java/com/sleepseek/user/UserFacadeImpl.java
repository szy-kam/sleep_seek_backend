package com.sleepseek.user;

import com.sleepseek.user.DTO.UserDTO;
import com.sleepseek.user.exception.UserAlreadyExistsException;
import com.sleepseek.user.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserFacadeImpl implements UserFacade {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserFacadeImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void postUser(UserDTO userDTO) {
        new UserValidator().validate(userDTO);

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
