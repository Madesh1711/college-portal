package com.example.springsecuritydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRequestService requestService;

    @Autowired
    private StudentRepository studentrepo;

    @Autowired
    private EditRequestService editRequestService;
    @Autowired
    private MarksRepository marksrepo;



    @GetMapping("/adminPage")
            public String adminPage()
    {
        return "admin";
    }

    @GetMapping("/addRequest")
    public String viewPending(Model model)
    {
        model.addAttribute("requests",requestService.getAllRequests());
        return "addRequest";
    }

    @PostMapping("/add_student_request/{rollno}")
    public String approveStudent(@PathVariable String rollno)
    {
        Student req=requestService.getRequestByRollno(rollno);
        studentrepo.save(req);

        User user = new User();
        user.setUsername(req.getRollno());
        user.setPassword(passwordEncoder.encode(req.getRollno()));
        user.setRole("STUDENT");
        userRepo.save(user);

        Marks marks = new Marks();
        marks.setRollno(req.getRollno());
        marks.setTamil(0);
        marks.setEnglish(0);
        marks.setOrganic(0);
        marks.setInorganic(0);
        marks.setPhysical(0);
        marks.setTotal(0);
        marks.setAverage(0);
        marksrepo.save(marks);

     requestService.removeRequest(rollno);
     return "redirect:/addRequest";
    }

    @GetMapping("/editRequest")
    public String editRequests(Model model)
    {
        model.addAttribute("editRequests",editRequestService.getAllRequests());
        return "editRequest";
    }

    @PostMapping("/edit_student_request/{rollno}")
    public String approveEditedStudent(@PathVariable String rollno)
    {
        Student req=editRequestService.getRequestByRollNo(rollno);
        studentrepo.save(req);


        editRequestService.removeRequest(rollno);
        return "redirect:/editRequest";
    }
}
