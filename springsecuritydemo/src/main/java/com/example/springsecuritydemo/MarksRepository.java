package com.example.springsecuritydemo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MarksRepository extends JpaRepository<Marks,String> {

    Marks findByRollno(String rollno);
}





