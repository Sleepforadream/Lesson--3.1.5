package ru.kata.spring.web.project.init;

import org.springframework.stereotype.Component;
import ru.kata.spring.web.project.model.Role;
import ru.kata.spring.web.project.model.User;
import ru.kata.spring.web.project.service.RoleServiceImpl;
import ru.kata.spring.web.project.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class DbInit {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public DbInit(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void postConstruct() {
        Role roleAdmin = new Role(1L, "ROLE_ADMIN");
        Role roleCommon = new Role(2L, "ROLE_COMMON");
        roleService.addRole(roleAdmin);
        roleService.addRole(roleCommon);

        User user = new User("maxim@mail.ru", "123456", "maxim", "maximov", 20);
        user.setRoles(Set.of(roleAdmin));
        User admin = new User("ivan@mail.ru", "123456", "ivan", "ivanov", 30);
        admin.setRoles(Set.of(roleCommon));
        userService.saveUser(user);
        userService.saveUser(admin);
    }
}
