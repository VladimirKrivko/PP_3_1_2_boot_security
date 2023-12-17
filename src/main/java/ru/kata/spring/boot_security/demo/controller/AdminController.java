package ru.kata.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping
    public String findAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                          @RequestParam(required = false, defaultValue = "5") Integer size,
                          Model model) {
        log.info("handling users request: {} {}", page, size);

        model.addAttribute("user", userService.findUserByEmail(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()));

        Page<UserDto> usersPage = userService.fetchUsers(page - 1, size);
        model.addAttribute("users_page", usersPage);

        int totalPages = usersPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "admin";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid UserDto user,
                             BindingResult bindingResult) {
        log.info("handling create user request: {}", user);
        if (bindingResult.hasErrors()) {
            return "admin";
        }
        userService.saveUser(user);

        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") @Valid UserDto user,
                             BindingResult bindingResult) {
        log.info("handling update user request: {}", user);


//        if (bindingResult.hasErrors()) {
//            return "users";
//        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute("user") UserDto user) {
        log.info("handling delete user request: {}", user);
        userService.deleteById(user.getId());
        return "redirect:/admin";
    }
}
