package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("user with email {%s} not found", email)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> fetchUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User byId = findById(user.getId());
        if (!byId.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.findById(id)
                .ifPresent(userRepository::delete);
    }
}
