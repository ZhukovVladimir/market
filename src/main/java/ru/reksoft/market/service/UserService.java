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
import ru.reksoft.market.exception.ResourceNotFoundException;

@Service
public class UserService {
    private static final String DEFAULT_ROLE = "ROLE_USER";

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
            user.setAuthorities(authorityRepository.findAuthoritiesByAuthority(DEFAULT_ROLE));
            user = userRepository.save(user);
            setUpEmptyCart(user);
        }
    }

    private void setUpEmptyCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setDeliveryStatus(DeliveryStatus.PREORDER);
        if (!user.getAddress().isEmpty()) {
            cart.setDeliveryAddress(user.getAddress());
        }
        cartRepository.save(cart);
    }

//    public UserDto getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null) {
//            throw new ForbiddenException();
//        }
//        return modelMapper.map(authentication.getPrincipal(), UserDto.class);
//    }
}