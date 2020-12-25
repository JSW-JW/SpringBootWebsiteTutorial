package com.example.spring_boot_tutorial.repository;

import com.example.spring_boot_tutorial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
