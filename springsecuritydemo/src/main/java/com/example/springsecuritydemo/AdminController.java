package com.example.springsecuritydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentAddRequestService requestService;

    @Autowired
    private StudentRepository studentrepo;

    @Autowired
    private StudentEditRequestService studentEditRequestService;
    @Autowired
    private MarksRepository marksrepo;
    @Autowired
    private StaffRepository staffrepo;



    @GetMapping("/adminPage")
            public String adminPage(Principal principal,Model model)
    {
        String currentUser=principal.getName();
        model.addAttribute("user",currentUser);
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
        user.setPhone_no(req.getPhone_no());
        user.setEmail_id(req.getEmail_id());

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
        model.addAttribute("editRequests", studentEditRequestService.getAllRequests());
        return "editRequest";
    }

    @PostMapping("/edit_student_request/{rollno}")
    public String approveEditedStudent(@PathVariable String rollno)
    {
        Student req= studentEditRequestService.getRequestByRollNo(rollno);
        studentrepo.save(req);

        User user=userRepo.findByUsername(req.getRollno());
        user.setPhone_no(req.getPhone_no());
        user.setEmail_id(req.getEmail_id());
        userRepo.save(user);

        studentEditRequestService.removeRequest(rollno);
        return "redirect:/editRequest";
    }
    @GetMapping("/addStaff")
    public String addStaff()
    {
        return "addStaff";
    }
    @PostMapping ("/addStaff")
    public String add_staf(@ModelAttribute Staff staff,@ModelAttribute User user, @RequestParam String name, @RequestParam String rollno, @RequestParam int age, @RequestParam String subject, @RequestParam String gender, @RequestParam String dob, @RequestParam String email_id, @RequestParam String phone_no)
    {
        staff.setName(name);
        staff.setAge(age);
        staff.setSubject(subject);
        staff.setDob(dob);
        staff.setGender(gender);
        staff.setRollno(rollno);
        staff.setEmail_id(email_id);
        staff.setPhone_no(phone_no);

        staffrepo.save(staff);

        String password= passwordEncoder.encode(rollno);
        user.setPassword(password);
        user.setUsername(rollno);
        user.setRole("STAFF");
        user.setEmail_id(email_id);
        user.setPhone_no(phone_no);
        userRepo.save(user);

        return "addStaff";
    }

    @GetMapping("/editStaff")
    public String showEditPage(@RequestParam(value="rollno",required= false)String rollno,Model model)
    {
        if(rollno != null && !rollno.isEmpty())
        {
            Staff staff=staffrepo.findByRollno(rollno);
            model.addAttribute("staff",staff);
        }
        return "editStaff";
    }

    @PostMapping("/editStaff")
    public String updateStaff(@ModelAttribute Staff staff,@RequestParam String phone_no,@RequestParam String rollno)
    {
        User user=userRepo.findByUsername(rollno);
        user.setPhone_no(phone_no);
        userRepo.save(user);

        staffrepo.save(staff);
        return "editStaff";
    }
}
