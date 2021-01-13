package com.sleepseek.user;

import com.sleepseek.user.DTO.UserDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class UserMapper {
    static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .build();
    }

    static UserDetails toDetails(User user){
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

}
