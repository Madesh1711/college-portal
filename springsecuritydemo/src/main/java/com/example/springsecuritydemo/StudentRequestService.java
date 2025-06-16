package com.example.springsecuritydemo;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class StudentRequestService {

    private final Map<String,Student> pendingRequests = new HashMap<>();

    public void addRequest(Student student) {
        pendingRequests.put(student.getRollno(),student);
    }

    public Collection<Student> getAllRequests() {
        return pendingRequests.values();
    }

    public Student getRequestByRollno(String rollno){return pendingRequests.get(rollno);}

    public void removeRequest(String rollno) {
        pendingRequests.remove(rollno);
    }
}