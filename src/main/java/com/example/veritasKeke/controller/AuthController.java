package com.example.veritasKeke.controller;

import com.example.veritasKeke.entity.Rider;
import com.example.veritasKeke.entity.Student;
import com.example.veritasKeke.entity.User;
import com.example.veritasKeke.enums.Role;
import com.example.veritasKeke.repository.RiderRepository;
import com.example.veritasKeke.repository.StudentRepository;
import com.example.veritasKeke.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RiderRepository riderRepository;

    @GetMapping({"/", "/landing"})
    public String landing() {
        return "auth/landing";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/login-rider")
    public String loginRider() {
        return "auth/login-rider";
    }

    @GetMapping("/login-admin")
    public String loginAdmin() {
        return "auth/login-admin";
    }



    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @GetMapping("/register-rider")
    public String registerRider() {
        return "auth/registerRider";
    }






    @PostMapping("/students/register")
    public String registerStudent(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String matricNumber,
            @RequestParam String phoneNumber
    ) {

        // 1. Create User
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.STUDENT);
        user.setCreatedAt(java.time.LocalDateTime.now());

        userRepository.save(user);

        // 2. Create Student
        Student student = new Student();
        student.setMatricNumber(matricNumber);
        student.setPhoneNumber(phoneNumber);
        student.setUser(user); // 🔗 LINK

        studentRepository.save(student);

        return "redirect:/login";
    }





    @PostMapping("/riders/register")
    public String registerRider(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String plateNumber,
            @RequestParam String bankName,
            @RequestParam String accountNumber,
            @RequestParam String phoneNumber
    ) {

        // 1. Create User
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.RIDER);
        user.setCreatedAt(java.time.LocalDateTime.now());

        userRepository.save(user);

        // 2. Create Rider
        Rider rider = new Rider();
        rider.setPlateNumber(plateNumber);
        rider.setBankName(bankName);
        rider.setAccountNumber(accountNumber);
        rider.setPhoneNumber(phoneNumber);
        rider.setUser(user); // 🔗 LINK TO USER

        riderRepository.save(rider);

        return "redirect:/login";
    }

    @PostMapping("/login-user")
    public String loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session
    ) {

        User user = userRepository.findByEmail(email);

        if(user == null) {
            return "redirect:/login";
        }

        if(!user.getPassword().equals(password)) {
            return "redirect:/login";
        }

        session.setAttribute("loggedInUser", user);

        return "redirect:/dashboard";
    }
}