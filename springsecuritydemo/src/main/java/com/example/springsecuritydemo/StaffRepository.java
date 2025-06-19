package com.example.springsecuritydemo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff , String> {
    Staff findByRollno(String rollno);
}
