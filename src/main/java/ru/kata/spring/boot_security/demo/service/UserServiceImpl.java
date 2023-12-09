package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDto findByFirstName(String firstName) {
        return modelMapper.map(userRepository.findByFirstName(firstName), UserDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String firstName) throws UsernameNotFoundException {
        UserDto user = findByFirstName(firstName);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", firstName));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getFirstName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<UserDto> fetchUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).
                map(user -> modelMapper.map(user, UserDto.class));
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        return modelMapper.map(userRepository.getById(id), UserDto.class);
    }

    public void saveUser(UserDto user) {
        userRepository.save(modelMapper.map(user, User.class));
    }

    public void deleteById(Long id) {
        userRepository.findById(id)
                .ifPresent(userRepository::delete);
    }
}
