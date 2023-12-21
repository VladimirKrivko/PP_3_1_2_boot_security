package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.domain.Page;
import ru.kata.spring.boot_security.demo.dto.UserDto;

public interface UserService {

    Page<UserDto> fetchUsers(Integer page, Integer size);

    UserDto findById(Long id);

    UserDto findByFirstName(String firstName);

    void saveUser(UserDto user);
    void updateUser(UserDto user);

    void deleteById(Long id);
}
