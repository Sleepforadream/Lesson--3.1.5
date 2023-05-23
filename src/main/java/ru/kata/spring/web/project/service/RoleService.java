package ru.kata.spring.web.project.service;

import ru.kata.spring.web.project.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getAllRoles();

    void addRole(Role role);

    Role getRoleById(Long roleId);

    void deleteRole(Long roleId);

}
