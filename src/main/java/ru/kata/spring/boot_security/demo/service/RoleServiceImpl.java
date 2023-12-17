package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

//    @Override
//    public List<Role> getRoles() {
//        return roleRepository.findAll();
//    }
//
//    @Override
//    public Role findById(Long id) {
//        return roleRepository.getById(id);
//    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getAllByName(Set<String> roles) {
        return roleRepository.getAllByNames(roles);
    }
}
