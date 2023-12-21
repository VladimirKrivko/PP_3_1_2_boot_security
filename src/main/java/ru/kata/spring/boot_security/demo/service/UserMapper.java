package ru.kata.spring.boot_security.demo.service;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    private final ModelMapper mapper;
    private final RoleRepository roleRepository;

    public UserMapper(ModelMapper mapper, RoleRepository roleRepository) {
        this.mapper = mapper;
        this.roleRepository = roleRepository;
        mapper.createTypeMap(User.class, UserDto.class)
                .addMappings(mapping -> mapping.using(roleToStringConvertor())
                        .map(User::getRoles, UserDto::setRoles));
        mapper.createTypeMap(UserDto.class, User.class)
                .addMappings(mapping -> mapping.using(stringToRoleConvertor())
                        .map(UserDto::getRoles, User::setRoles));
    }

    public UserDto convertToDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    public User convertToUser(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }

    private Converter<Set<Role>, String> roleToStringConvertor() {
        return context -> context.getSource().stream()
                .map(Role::getName)
                .map(s -> s.replace("ROLE_", ""))
                .sorted()
                .collect(Collectors.joining(" "));
    }

    private Converter<String, Set<Role>> stringToRoleConvertor() {
        return context -> new HashSet<>(
                roleRepository.getAllByNames(Arrays.stream(context.getSource().split(","))
                        .collect(Collectors.toSet())));
    }

    public void updateUserFromDto(UserDto userDto, User user) {
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        if (Objects.nonNull(userDto.getPassword()) && !userDto.getPassword().isEmpty()) {
            user.setPassword(userDto.getPassword());
        }
        if (Objects.nonNull(userDto.getRoles()) && !userDto.getRoles().isEmpty()) {
            user.setRoles(new HashSet<>(
                    roleRepository.getAllByNames(Arrays.stream(userDto.getRoles().split(","))
                            .collect(Collectors.toSet()))));
        }
    }
}
