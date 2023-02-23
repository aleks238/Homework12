package com.example.homework12.controllers;

import com.example.homework12.converters.Converter;
import com.example.homework12.dtos.UserDto;
import com.example.homework12.entities.Role;
import com.example.homework12.entities.User;
import com.example.homework12.services.UserService;
import com.example.homework12.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final Converter converter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    public UserDto saveNewPUser(@RequestBody UserDto userDto){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_USER"));
        userValidator.validate(userDto);
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRoles(roles);
        user = userService.save(user);
        return converter.userToUserDto(user);
    }
}
