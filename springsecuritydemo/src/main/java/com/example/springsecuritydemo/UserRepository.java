package com.example.springsecuritydemo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User , String> {
    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query("update User u set u.password = :password where u.username=:username")
    void updatePassword(@Param("username")String username,@Param("password")String password);
}

