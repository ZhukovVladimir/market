package com.example.market.services.auth;

import com.example.market.data.models.User;
import com.example.market.data.repositories.UserRepository;
import com.example.market.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Primary
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("User " + username + " not found");
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return (User) authentication.getPrincipal();
        } else throw new ForbiddenException();
    }
}
