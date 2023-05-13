package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {

    void addRole(Role role);

    List<Role> getAllRoles();

    List<Role> parseArrayToListRoles(String[] roles);

    Role getRoleById(Long roleId);

    void updateRole(Long id, Role role);

    void deleteRole(Long roleId);
}
