package com.cnam.demo.projetGestionRestau.controller;

import com.cnam.demo.projetGestionRestau.entity.User;
import com.cnam.demo.projetGestionRestau.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public String getAllUser(Model model, @Param("keyword") String keyword) {
        try {
            List<User> users = new ArrayList<>();
            if (keyword == null) {
                userRepository.findAll().forEach(users::add);
            } else {
                userRepository.findByNomContainingIgnoreCase(keyword).forEach(users::add);
                model.addAttribute("keyword", keyword);
            }                model.addAttribute("users", users);            }
        catch (Exception e) {
            model.addAttribute("message", e.getMessage());            }
        return "users";        }

    @GetMapping("/users/new")
    public String addUser(Model model) {
        User user= new User();
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Create new user");
        return "user_form";        }

    @PostMapping("/users/save")
    public String saveUser(User user , RedirectAttributes redirectAttributes) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            userRepository.save(user);
            redirectAttributes.addFlashAttribute("message", "The user has been saved successfully!");            } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());            }
        return "redirect:/users";        }

    @GetMapping("/users/{id}")
    public String editUser(@PathVariable("id") Integer id,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        try {
            User user  = userRepository.findById(id).get();
            model.addAttribute("user", user);
            model.addAttribute("pageNomUser", "Edit User (ID: " + id + ")");
            return "user_form";            }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        try {
            userRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "The user with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";    }
}
