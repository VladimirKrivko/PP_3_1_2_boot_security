package ru.kata.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getALL() {
        return userService.getAll();
    }

    @PostMapping()
    public UserDto createUser(@RequestBody UserDto user) {
        log.info("create user {}", user);
        return userService.saveUser(user);
    }

    @PatchMapping("/user/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDto user) {
        log.info("update user {} with id {}", user, id);
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
