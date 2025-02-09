package com.cdacProject.taskhive.repository;

import com.cdacProject.taskhive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

}
