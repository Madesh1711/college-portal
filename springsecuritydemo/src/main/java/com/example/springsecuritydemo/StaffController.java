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
    @Autowired
    private StaffRepository staffrepo;

    @GetMapping("/staffPage")
    public String studentHome(Model model,Principal principal)
    {
        String rollno=principal.getName();
        Staff staff=staffrepo.findByRollno(rollno);
        model.addAttribute("staff",staff);
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
    public String showAddMarksPage(Model model,Principal principal)
    {
        String rollno=principal.getName();
        Staff staff =staffrepo.findByRollno(rollno);
        model.addAttribute("subject",staff.getSubject());
        model.addAttribute("marks",new Marks());
        return "addMarks";
    }
    @PostMapping("/addMarks")
    public String addMarks(@ModelAttribute Marks formMarks,Principal principal)
    {
        String rollno=principal.getName();
        Staff staff=staffrepo.findByRollno(rollno);
        String subject=staff.getSubject();

        String sturollno=formMarks.getRollno();
        Marks existing=marksrepo.findByRollno(sturollno);

        switch (subject)
        {
            case "Tamil" :
                existing.setTamil(formMarks.getTamil());
                break;
            case "English" :
                existing.setEnglish(formMarks.getEnglish());
                break;
            case "Organic" :
                existing.setOrganic(formMarks.getOrganic());
                break;
            case "Inorganic" :
                existing.setInorganic(formMarks.getInorganic());
                break;
            case "Physical" :
                existing.setPhysical(formMarks.getPhysical());
                break;
        }

        int total=existing.getTamil()+existing.getEnglish()+existing.getOrganic()+existing.getInorganic()+existing.getPhysical();
        float average=(float)total/5;
        existing.setTotal(total);
        existing.setAverage(average);
        marksrepo.save(existing);
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

