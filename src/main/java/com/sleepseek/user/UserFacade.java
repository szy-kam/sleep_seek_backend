package com.sleepseek.user;

import com.sleepseek.user.DTO.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserFacade extends UserDetailsService {

    void postUser(UserDTO userDTO);

    User getUserByUsername(String username);

    boolean userExists(String username);

}
