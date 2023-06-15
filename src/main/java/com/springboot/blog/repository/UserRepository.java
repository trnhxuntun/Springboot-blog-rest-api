package com.springboot.blog.repository;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByUsernameOrEmail(String username,String email);
    Optional<User> findByEmail(String Email);
    User findByUsername(String username);

    boolean existsByUsername(String Username);
    boolean existsByEmail(String email);

}
