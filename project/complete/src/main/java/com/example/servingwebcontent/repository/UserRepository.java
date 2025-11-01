package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Tìm user theo username
    Optional<User> findByUsername(String username);
    
    // Tìm user theo email
    Optional<User> findByEmail(String email);
    
    // Tìm user theo username và password (để đăng nhập)
    Optional<User> findByUsernameAndPassword(String username, String password);
    
    // Tìm user theo tên (case insensitive)
    List<User> findByNameContainingIgnoreCase(String name);
    
    // Đếm số lượng user
    long count();
}
