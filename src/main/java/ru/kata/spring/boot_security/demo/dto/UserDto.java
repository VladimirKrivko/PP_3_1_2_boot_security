package ru.kata.spring.boot_security.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotNull
    @NotEmpty(message = "should not be empty")
    @Size(min = 2, max = 32, message = "should be between 2 and 32 characters")
    private String firstName;

    @Size(min = 0, max = 32, message = "can be empty or no longer than 32 characters")
    private String lastName;

    @Min(value = 16)
    @Max(value = 200)
    private Integer age;

    @NotNull
    @NotEmpty(message = "should not be empty")
    @Size(max = 80, message = "should be no more than 80 characters")
    private String password;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
//    private Set<Role> roles;
    private String roles;
}
