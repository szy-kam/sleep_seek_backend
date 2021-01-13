package com.sleepseek.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class UserDTO {
    private String username;
    private String password;
    private String displayName;

}
