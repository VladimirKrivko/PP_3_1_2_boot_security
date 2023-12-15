//package ru.kata.spring.boot_security.demo.dto;
//
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import ru.kata.spring.boot_security.demo.model.Role;
//
//import java.util.Collection;
//
//@Data
//public class UserDto {
//    @EqualsAndHashCode.Exclude
//    private Long id;
//
//    @NotNull
//    @NotEmpty(message = "should not be empty")
//    @Size(min = 2, max = 32, message = "should be between 2 and 32 characters")
//    private String firstName;
//
//    @Size(min = 0, max = 32, message = "can be empty or no longer than 32 characters")
//    private String lastName;
//
//    private Integer age;
//
//    @NotNull
//    @NotEmpty(message = "should not be empty")
//    @Size(max = 80, message = "should be no more than 80 characters")
//    private String password;
//
//    private String email;
//
//    @NotNull
//    @NotEmpty
//    private Collection<Role> roles;
//}
