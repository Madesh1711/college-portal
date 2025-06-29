package com.example.springsecuritydemo;

import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentEditRequestService {
    private Map<String, Student> pendingEditRequests = new HashMap<>();

    public void addRequest(Student student) {
        pendingEditRequests.put(student.getRollno(), student);
    }

    public Collection<Student> getAllRequests() {
        return pendingEditRequests.values();
    }

    public Student getRequestByRollNo(String rollno) {
        return pendingEditRequests.get(rollno);
    }

    public void removeRequest(String rollno) {
        pendingEditRequests.remove(rollno);
    }
}

