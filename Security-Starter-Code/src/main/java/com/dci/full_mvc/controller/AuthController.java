package com.dci.full_mvc.controller;

import com.dci.full_mvc.model.User;
import com.dci.full_mvc.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


//    @GetMapping /login
//    @Getmapping /signup
//    @PostMapping /signup


    @GetMapping("/signup")
    public String signupForm(Model model){
        model.addAttribute("user",new User());
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String registerUser(@Valid  @ModelAttribute User user, BindingResult result, Model model){

//        validation

//        email is not already taken in the DB
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            model.addAttribute("emailTaken", true);
            return "auth/signup";
        }

        if(result.hasErrors()){
            return "auth/signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));



        userRepository.save(user);

        return "redirect:/auth/signup";
    }


}
