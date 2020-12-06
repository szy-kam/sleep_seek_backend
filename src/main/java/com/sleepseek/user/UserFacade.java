package com.sleepseek.user;

import com.sleepseek.user.DTO.UserDTO;

public interface UserFacade {

    UserDTO postUser(UserDTO userDTO);

    UserDTO getUser(String email);

    UserDTO getUser(Long id);
}
