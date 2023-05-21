package ru.kata.spring.web.project.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kata.spring.web.project.repository.RoleRepository;
import ru.kata.spring.web.project.model.Role;

import java.util.HashSet;
import java.util.Set;

@Component
public class Converters {
    private final RoleRepository roleRepository;

    @Autowired
    public Converters(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Component
    public class StringsArrayToRoleConverter implements Converter<String[], Set<Role>> {

        @Override
        public Set<Role> convert(String[] source) {
            Set<Role> roles = new HashSet<>();
            for (String roleName : source) {
                Role role = roleRepository.findByRole(roleName);
                roles.add(role);
            }
            return roles;
        }
    }

    @Component
    public class StringToRoleConverter implements Converter<String, Set<Role>> {

        @Override
        public Set<Role> convert(String source) {
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByRole(source);
            roles.add(role);
            return roles;
        }
    }
}