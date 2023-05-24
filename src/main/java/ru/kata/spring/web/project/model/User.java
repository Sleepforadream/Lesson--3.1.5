package ru.kata.spring.web.project.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "users")
@EqualsAndHashCode
@Getter
@Setter
@ToString(exclude = {"roles"})
public class User implements UserDetails {

    public User() {}

    public User(String email, String password, String name, String surname, Integer age) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    private String password;

    @Column(name = "name")
    @Size(min = 2, max = 40, message = "Name should be between 2 to 30")
    private String name;

    @Column(name = "surname")
    @Size(min = 2, max = 40, message = "Surname should be between 2 to 30")
    private String surname;

    @Column(name = "age")
    @Positive(message = "Age should not be empty or negative")
    @Max(120)
    private Integer age;

    @NotEmpty(message = "Roles should not be empty")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
