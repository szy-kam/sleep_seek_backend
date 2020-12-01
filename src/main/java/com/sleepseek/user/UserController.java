package com.sleepseek.user;

import com.sleepseek.user.DTO.UserDTO;
import org.springframework.web.bind.annotation.*;

@RestController
class UserController {

    private final UserFacade userFacade;

    UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping("/user")
    void addUser(@RequestBody UserDTO userDTO) {
        userFacade.postUser(userDTO);
    }

    @GetMapping("/user/{userId}")
    UserDTO getUser(@PathVariable Long userId) {
        return userFacade.getUser(userId);
    }
}
