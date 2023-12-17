package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.domain.Page;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserService {

    Page<UserDto> fetchUsers(Integer page, Integer size);

    UserDto findById(Long id);

    UserDto findUserByEmail(String email);

    void saveUser(UserDto user);
    void updateUser(UserDto user);

    void deleteById(Long id);
}
