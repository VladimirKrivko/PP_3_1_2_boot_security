package ru.kata.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @GetMapping("/users")
    public String findAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                          @RequestParam(required = false, defaultValue = "5") Integer size,
                          Model model) {
        log.info("handling users request: {} {}", page, size);

        Page<UserDto> usersPage = userService.fetchUsers(page - 1, size);
        model.addAttribute("users_page", usersPage);

        int totalPages = usersPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "users";
    }

    @GetMapping("/user-create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "user-create";
    }

    @PostMapping("/user-create")
    public String createUser(@ModelAttribute("user") @Valid UserDto user,
                             BindingResult bindingResult) {
        log.info("handling create user request: {}", user);
        if (bindingResult.hasErrors()) {
            return "user-create";
        }
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/user-update")
    public String updateUserForm(@RequestParam(name = "id") Long id,
                                 Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(@ModelAttribute("user") @Valid UserDto user,
                             BindingResult bindingResult) {
        log.info("handling update user request: {}", user);
        if (bindingResult.hasErrors()) {
            return "user-update";
        }
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/user-delete")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        log.info("handling delete user by id request: id = {}", id);
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
