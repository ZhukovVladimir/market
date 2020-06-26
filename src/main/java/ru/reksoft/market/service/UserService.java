package ru.reksoft.market.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.reksoft.market.data.dto.UserDto;
import ru.reksoft.market.data.model.Cart;
import ru.reksoft.market.data.model.DeliveryStatus;
import ru.reksoft.market.data.model.User;
import ru.reksoft.market.data.repository.AuthorityRepository;
import ru.reksoft.market.data.repository.CartRepository;
import ru.reksoft.market.data.repository.UserRepository;
import ru.reksoft.market.exception.ConflictException;

@Service
public class UserService {
    private static final String DEFAULT_ROLE = "ROLE_USER";

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final CartRepository cartRepository;

    private final ModelMapper modelMapper;

    private final CartService cartService;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, CartRepository cartRepository, ModelMapper modelMapper, CartService cartService) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
        this.cartService = cartService;
    }

    public void createUser(UserDto userDto, PasswordEncoder passwordEncoder) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new ConflictException(userDto.getUsername() + " User already exist");
        } else {
            User user = modelMapper.map(userDto, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAuthorities(authorityRepository.findAuthoritiesByAuthority(DEFAULT_ROLE));
            user = userRepository.save(user);
            cartService.setUpEmptyCart(user);
        }
    }
}