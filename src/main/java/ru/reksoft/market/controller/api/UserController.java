package ru.reksoft.market.controller.api;

import ru.reksoft.market.data.dto.UserDto;
import ru.reksoft.market.data.model.User;
import ru.reksoft.market.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for working with Account endpoints
 */

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public UserDto getCurrentUser(@AuthenticationPrincipal User user) {
        return modelMapper.map(user, UserDto.class);//userService.getCurrentUser();
    }
}
