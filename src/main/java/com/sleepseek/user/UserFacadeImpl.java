package com.sleepseek.user;

import com.sleepseek.user.DTO.UserDTO;
import com.sleepseek.user.exception.UserAlreadyExistsException;
import com.sleepseek.user.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserFacadeImpl implements UserFacade {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserFacadeImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDTO postUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException(userDTO.getEmail());
        } else {
            userRepository.save(User.builder()
                    .username(userDTO.getUsername())
                    .email(userDTO.getEmail())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build());
            return UserMapper.toDTO(userRepository.findByEmail(userDTO.getEmail()).orElse(null));
        }
    }

    @Override
    public UserDTO getUser(String email) {
        return userRepository.findByEmail(email).map(UserMapper::toDTO).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public UserDTO getUser(Long id) {
        return userRepository.findById(id).map(UserMapper::toDTO).orElseThrow(() -> new UserNotFoundException(id));
    }
}
