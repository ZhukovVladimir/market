package com.example.market.services;

import com.example.market.data.dto.UserDto;
import com.example.market.data.models.Cart;
import com.example.market.data.models.DeliveryStatus;
import com.example.market.data.models.User;
import com.example.market.data.repositories.AuthorityRepository;
import com.example.market.data.repositories.CartRepository;
import com.example.market.data.repositories.UserRepository;
import com.example.market.exceptions.ConflictException;
import com.example.market.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final String defaultRole = "ROLE_USER";

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final CartRepository cartRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, CartRepository cartRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto findOne(Long id) {
        return modelMapper.map(
                userRepository.findById(id).orElseThrow(ResourceNotFoundException::new),
                UserDto.class);
    }

    public UserDto findByEmail(String username) {
        User user = userRepository.findByUsername(username);
        return modelMapper.map(user, UserDto.class);
    }

    public void createUser(UserDto userDto, PasswordEncoder passwordEncoder) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new ConflictException(userDto.getUsername() + " User already exist");
        } else {
            User user = modelMapper.map(userDto, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAuthorities(authorityRepository.findAuthoritiesByAuthority(defaultRole));
            user = userRepository.save(user);
            setUpEmptyCart(user);
        }
    }

    private void setUpEmptyCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setDeliveryStatus(DeliveryStatus.PREORDER);
        cartRepository.save(cart);
    }
}