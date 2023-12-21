package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto findUserByEmail(String email);

    UserDto saveUser(UserDto user);

    UserDto updateUser(Long id, UserDto user);

    void deleteById(Long id);
}
