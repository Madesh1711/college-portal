package com.example.springsecuritydemo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultAdminLoader {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (userRepo.findByUsername("Admin")==null) {
            User admin = new User();
            admin.setUsername("Admin");
            admin.setPassword(passwordEncoder.encode("Admin")); // auto hash it here
            admin.setRole("ADMIN");
            userRepo.save(admin);
        }
    }
}
