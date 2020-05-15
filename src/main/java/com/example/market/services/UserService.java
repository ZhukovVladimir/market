package com.example.market.services;

import com.example.market.data.dto.UserDto;
import com.example.market.data.models.User;
import com.example.market.data.repositories.RoleRepository;
import com.example.market.data.repositories.UserRepository;
import com.example.market.exceptions.ConflictException;
import com.example.market.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final String defaultRole = "user";

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    public UserDto findOne(Long id) {
        return modelMapper.map(
                userRepository.findById(id).orElseThrow(ResourceNotFoundException::new),
                UserDto.class);
    }

    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new ConflictException(userDto.getEmail() + " User already exist");
        } else {
            User user = modelMapper.map(userDto, User.class);
            user.setRole(roleRepository.findRoleByName(defaultRole));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            return modelMapper.map(user, UserDto.class);
        }
    }
}
