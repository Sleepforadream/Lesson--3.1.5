package com.example.PP_3_1_2_spring_project_boot.repository;

import com.example.PP_3_1_2_spring_project_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
