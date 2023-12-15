package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.domain.Page;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserService {

    Page<User> fetchUsers(Integer page, Integer size);

    User findById(Long id);

    User findUserByEmail(String email);

    void saveUser(User user);
    void updateUser(User user);

    void deleteById(Long id);
}
