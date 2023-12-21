package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("from Role where name in (:names)")
    List<Role> getAllByNames(@Param("names") Set<String> names);
}
