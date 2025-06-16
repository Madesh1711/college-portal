package com.example.springsecuritydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

@Controller
public class StudentController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    MarksRepository marksrepo;
    @Autowired
    StudentRepository studentrepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/studentPage")
    public String studentHome(Model model,Principal principal)
    {
        String rollno=principal.getName();
        Student student=studentrepo.findByRollno(rollno);
        model.addAttribute("student",student);
        return "student";
    }

    @GetMapping("/viewMarks")
    public String showMarks(Model model, Principal principal)
    {
        String rollno=principal.getName();
        Marks marks=marksrepo.findByRollno(rollno);
        if(marks==null || marks.getTamil() == 0 ||marks.getEnglish()==0 ||marks.getOrganic()==0 ||marks.getInorganic()==0 ||marks.getPhysical()==0 )
        {
            return "marks_not_updated";
        }
        model.addAttribute("marks",marks);
        return "viewMarks";
    }

}
