package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDto findByFirstName(String firstName) {
        return modelMapper.map(userRepository.findByFirstName(firstName), UserDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> fetchUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).
                map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        return modelMapper.map(userRepository.getById(id), UserDto.class);
    }

    @Override
    @Transactional
    public void saveUser(UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(modelMapper.map(user, User.class));
    }

    @Override
    @Transactional
    public void updateUser(UserDto user) {
        UserDto byId = findById(user.getId());
        if (!byId.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(modelMapper.map(user, User.class));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.findById(id)
                .ifPresent(userRepository::delete);
    }
}
