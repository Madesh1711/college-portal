package com.example.springsecuritydemo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,String> {
Student findByRollno(String rollno);
}


