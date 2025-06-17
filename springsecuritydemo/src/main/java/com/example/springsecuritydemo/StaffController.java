package com.example.springsecuritydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

@Controller
public class StaffController {

    @Autowired
    private MarksRepository marksrepo;
    @Autowired
    private EditRequestService editRequestService;
    @Autowired
    private StudentRepository studentrepo;

    @GetMapping("/staffPage")
    public String staffHome(Principal principal , Model model)
    {
        String currentUser=principal.getName();
        model.addAttribute("user",currentUser);
        return "staff";
    }
    @GetMapping("/addStudent")
    public String addStudent()
    {
        return "addStudent";
    }

    @Autowired
    private StudentRequestService requestService;

    @PostMapping("/add_student_request")
    public String addStudentRequest(@ModelAttribute Student student)
    {
        requestService.addRequest(student);
     return "redirect:/addStudent";
    }
    @GetMapping("/addMarks")
    public String showAddMarksPage(Model model)
    {
        model.addAttribute("marks",new Marks());
        return "addMarks";
    }
    @PostMapping("/addMarks")
    public String addMarks(@ModelAttribute Marks marks)
    {
        int total=marks.getTamil()+marks.getEnglish()+marks.getOrganic()+marks.getInorganic()+marks.getPhysical();
        float average=(float)total/5;
        marks.setTotal(total);
        marks.setAverage(average);
        marksrepo.save(marks);
        return "redirect:/addMarks";
    }

    @GetMapping("/editStudent")
    public String showEditPage(@RequestParam(value="rollno",required= false)String rollno,Model model)
    {
        if(rollno != null && !rollno.isEmpty())
        {
            Student student=studentrepo.findByRollno(rollno);
            model.addAttribute("student",student);
        }
        return "editStudent";
    }


    @PostMapping("/submitEditRequest")
    public String submitEditRequest(Student editedStudent) {
        editRequestService.addRequest(editedStudent);
        return "redirect:/editStudent";
    }


}

