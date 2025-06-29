package com.example.springsecuritydemo;

import jakarta.servlet.http.HttpSession;
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
import java.security.SecureRandom;
import java.util.Random;

import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Controller
public class LoginController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StudentRepository studentrepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private EmailService emailService;



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

    private static final SecureRandom RANDOM = new SecureRandom();
    public String generateOtp()
    {
        return String.format("%06d",RANDOM.nextInt(100000));
    }

    @GetMapping("/forgotPassword")
    public String showVerifyPage()
    {
        return "forgotPassword";
    }
    @PostMapping("/forgotPassword")
    public String verifyUser(@RequestParam String username, HttpSession session)
    {
        User user=userRepo.findByUsername(username);
        if(user!=null)
        {
            String otp=generateOtp();
            session.setAttribute("otp",otp);
            session.setAttribute("username",username);
            emailService.sendOtp(user.getEmail_id(),otp);
            return "redirect:/enter_otp";
        }
        return "invalid_username";
    }
    @GetMapping("/enter_otp")
    public String showOtpPage()
    {
        return "enter_otp";
    }

    @PostMapping("/validate_otp")
    public String validateOtp(@RequestParam String otp,HttpSession session)
    {
        String sessionOtp=(String)session.getAttribute("otp");
        if(otp.equals(sessionOtp))
        {
            return "newPassword";
        }
        return "enter_otp";
    }
    @PostMapping("/newPassword")
    public String newPassword(@RequestParam String pass,HttpSession session)
    {
        String sessionusername=(String)session.getAttribute("username");
        String password= passwordEncoder.encode(pass);
        User user =userRepo.findByUsername(sessionusername);
        user.setPassword(password);
        userRepo.save(user);
        return "redirect:/login";
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
        if(username.equals("Admin")){return "redirect:/adminPage";}
        return  "redirect:/staffPage";

    }

}