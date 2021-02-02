package com.sleepseek.user;

import com.sleepseek.user.DTO.UserDTO;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
class UserController {

    private final UserFacade userFacade;

    UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping("/signup")
    void addUser(@RequestBody UserDTO userDTO) {
        userFacade.postUser(userDTO);
    }

    @GetMapping("/username")
    String currentUserName(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/currentUser")
    UserDTO currentUser(Principal principal) {
        return UserMapper.toDto(userFacade.getUserByUsername(principal.getName()));
    }

    @GetMapping("/displayName")
    String displayName(@RequestParam String username) {
        return userFacade.getUserByUsername(username).getDisplayName();
    }
}
