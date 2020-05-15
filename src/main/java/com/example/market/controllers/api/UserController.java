package com.example.market.controllers.api;

import com.example.market.data.dto.UserDto;
import com.example.market.services.UserService;
import com.example.market.services.auth.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for working with Account endpoints
 */

@Controller
public class UserController {

    private final UserService userService;

    private final SecurityService securityService;

    @Autowired
    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.findOne(id);
    }

    @PostMapping("/registration")
    @ResponseBody
    public UserDto createUser(@Validated @RequestBody UserDto userDto) {
        UserDto savedUser = userService.createUser(userDto);
        securityService.autoLogin(userDto.getEmail(), userDto.getPassword());
        return savedUser;
    }

    @GetMapping("/registration")
    public String createUser() {

        return "registration";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

}
