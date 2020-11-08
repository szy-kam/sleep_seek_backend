package com.sleepseek.user;

import com.sleepseek.user.DTO.UserDTO;

public interface UserFacade {

    void postUser(UserDTO userDTO);

    UserDTO getUser(String email);

    UserDTO getUser(Long id);
}
