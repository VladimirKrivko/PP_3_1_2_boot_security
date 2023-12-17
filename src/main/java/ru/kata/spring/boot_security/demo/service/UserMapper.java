package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserMapper {
    private final ModelMapper mapper;
    private final RoleRepository roleRepository;

    public UserDto convertToDto(User user) {
        mapper.addConverter(roleToStringConvertor());

//        mapper.createTypeMap(User.class, UserDto.class)
//                .addMapping(User::getRoles, UserDto::setRoles)
//                .addMappings(mapper -> mapper.using(roleToStringConvertor)
//                        .map(User::getRoles, UserDto::setRoles));

        return mapper.map(user, UserDto.class);
    }

    public User convertToUser(UserDto userDto) {
//        mapper.addConverter(stringToRoleConvertor());
        return mapper.map(userDto, User.class);
    }

    private Converter<Set<Role>, String> roleToStringConvertor() {
        return context -> context.getSource().stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));
    }

    private Converter<String, Set<Role>> stringToRoleConvertor() {
        return context -> new HashSet<>(
                roleRepository.getAllByNames(Arrays.stream(context.getSource().split(","))
                        .collect(Collectors.toSet())));
    }
}
