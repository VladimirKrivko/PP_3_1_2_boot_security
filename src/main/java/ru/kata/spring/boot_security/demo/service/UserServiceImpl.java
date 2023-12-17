package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public UserDto findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("user with email {%s} not found", email)));
        return mapper.convertToDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> fetchUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable)
                .map(mapper::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        return mapper.convertToDto(userRepository.getById(id));
    }

    @Override
    @Transactional
    public void saveUser(UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User toUser = mapper.convertToUser(user);
        //TODO: to remove
        toUser.setRoles(convertStringToSetRoles(user.getRoles()));

        userRepository.save(toUser);
    }

    @Override
    @Transactional
    public void updateUser(UserDto user) {
        User userById = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("user with id {%d} not found", user.getId())));

        if (!userById.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        User toUser = mapper.convertToUser(user);

        //TODO: to remove
        System.out.println("toUser.roles()= " + toUser.getRoles());
        System.out.println("userDto.roles()= " + user.getRoles());
        toUser.setRoles(convertStringToSetRoles(user.getRoles()));
        System.out.println("toUser.roles()= " + toUser.getRoles());

        userRepository.save(toUser);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.findById(id)
                .ifPresent(userRepository::delete);
    }

    //TODO: remove this костылище
    private Set<Role> convertStringToSetRoles(String roles) {
        return new HashSet<>(roleRepository.getAllByNames(
                Arrays.stream(roles.split(","))
                        .collect(Collectors.toSet())));
    }
}
