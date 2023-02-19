package com.cnam.demo.projetGestionRestau.controller;
import com.cnam.demo.projetGestionRestau.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/accueil")
    public String viewHomePage() {
        return "accueil";
    }
    @GetMapping("/accueilUser")
    public String viewHomePageUser() {
        return "accueilUser";
    }
}
