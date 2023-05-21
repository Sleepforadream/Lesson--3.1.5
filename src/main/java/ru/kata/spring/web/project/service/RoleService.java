package ru.kata.spring.web.project.service;

import ru.kata.spring.web.project.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getAllRoles();

    Role getRoleByName(String roleName);

    Set<Role> parseArrayToSet(String[] roles);

    void addRole(Role role);

    void updateRole(Long id, Role role);

    Role getRoleById(Long roleId);

    void deleteRole(Long roleId);

}
