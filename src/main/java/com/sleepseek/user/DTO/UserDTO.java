package com.sleepseek.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String username;
}
