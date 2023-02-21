package com.cnam.demo.projetGestionRestau.controller;

import com.cnam.demo.projetGestionRestau.entity.Frigo;
import com.cnam.demo.projetGestionRestau.repository.FrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FrigoController {
    @Autowired
    private FrigoRepository frigoRepository;
    @GetMapping("/frigos")
    public String getAllFrigo(Model model, @Param("keyword") String keyword) {
        try {
            List<Frigo> frigos = new ArrayList<>();
            if (keyword == null) {
                frigoRepository.findAll().forEach(frigos::add);
            } else {
                frigoRepository.findByNomFrigoContainingIgnoreCase(keyword).forEach(frigos::add);
                model.addAttribute("keyword", keyword);
            }                model.addAttribute("frigos", frigos);            }
        catch (Exception e) {
            model.addAttribute("message", e.getMessage());            }
        return "frigos";        }
    @GetMapping("/frigos/new")
    public String addFrigo(Model model) {
        Frigo frigo = new Frigo();
        model.addAttribute("frigo", frigo);
        model.addAttribute("pageTitle", "Create new frigo");
        return "frigo_form";        }

    @PostMapping("/frigos/save")
    public String saveFrigo(Frigo frigo, RedirectAttributes redirectAttributes) {
        try {
            frigoRepository.save(frigo);
            redirectAttributes.addFlashAttribute("message", "The frigo has been saved successfully!");            } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());            }
        return "redirect:/frigos";        }

    @GetMapping("/frigos/{id}")
    public String editFrigo(@PathVariable("id") Integer id,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        try {
            Frigo frigo  = frigoRepository.findById(id).get();
            model.addAttribute("frigo", frigo);
            model.addAttribute("pageNomCategorie", "Edit frigo (ID: " + id + ")");
            return "frigo_form";            }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/frigos";
        }
    }
    @GetMapping("/frigos/delete/{id}")
    public String deleteFrigo(@PathVariable("id") Integer id,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        try {
            frigoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "The Frigo with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/frigos";    }
}
