package com.sleepseek.user;

import com.sleepseek.user.DTO.UserDTO;
import org.springframework.web.bind.annotation.*;

@RestController
class UserController {

    private final UserFacade userFacade;

    UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping("/user/signup")
    Long addUser(@RequestBody UserDTO userDTO) {
        return userFacade.postUser(userDTO).getId();
    }

    @GetMapping("/user/{userId}")
    UserDTO getUser(@PathVariable Long userId) {
        return userFacade.getUser(userId);
    }
}
