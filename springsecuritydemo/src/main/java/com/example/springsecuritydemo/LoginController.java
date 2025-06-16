package com.example.springsecuritydemo;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.security.Principal;

@Controller
public class LoginController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StudentRepository studentrepo;
    @Autowired
    private UserRepository userRepo;



    @GetMapping("/login")
    public String loginPage()
    {
        return "loginPage";
    }

    @GetMapping("/default")
    public String redirect(org.springframework.security.core.Authentication auth)
    {

        String role=auth.getAuthorities().iterator().next().getAuthority();

        switch(role)
        {
            case "ROLE_ADMIN":
                return "redirect:/adminPage";

            case "ROLE_STAFF":
                return "redirect:/staffPage";

            case "ROLE_STUDENT":
                return "redirect:/studentPage";

        }

        return "redirect:/login?error=true";
    }

    @GetMapping("/changePassword")
    public String showChangePasswordPage()
    {

        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String pass1, @RequestParam String pass2, Principal principal)
    {
        if(pass1.equals(pass2)) {
            String password= passwordEncoder.encode(pass2);
            String username= principal.getName();
            userRepo.updatePassword(username,password);
            return "redirect:/login";
        }
        return "passwordError";
    }
    @GetMapping("/redirectUser")
    public String redirectUser(Principal principal)
    {
        String username=principal.getName();
        Student student=studentrepo.findByRollno(username);
        if(student!=null){return "redirect:/studentPage";}
        if(username.equals("Staff1")){return "redirect:/staffPage";}
        return  "redirect:/adminPage";

    }

}