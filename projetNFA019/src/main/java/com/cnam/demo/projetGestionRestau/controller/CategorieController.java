package com.cnam.demo.projetGestionRestau.controller;

import java.util.ArrayList;
import java.util.List;

import com.cnam.demo.projetGestionRestau.entity.Categorie;
import com.cnam.demo.projetGestionRestau.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategorieController {
  @Autowired
  private CategorieRepository categorieRepository;

  @GetMapping("/categories")
  public String getAllCategorie(Model model, @Param("keyword") String keyword) {
    try {
      List<Categorie> categories = new ArrayList<>();
      if (keyword == null) {
        categorieRepository.findAll().forEach(categories::add);
      } else {
        categorieRepository.findByNomCategorieContainingIgnoreCase(keyword).forEach(categories::add);
        model.addAttribute("keyword", keyword);
      }                model.addAttribute("categories", categories);            }
    catch (Exception e) {
      model.addAttribute("message", e.getMessage());            }
    return "categories";        }

  @GetMapping("/categories/new")
  public String addCategorie(Model model) {
    Categorie categorie  = new Categorie();
    model.addAttribute("categorie", categorie);
    model.addAttribute("pageTitle", "Create new categorie");
    return "categorie_form";        }

  @PostMapping("/categories/save")
  public String saveCategorie(Categorie categorie , RedirectAttributes redirectAttributes) {
    try {
      categorieRepository.save(categorie);
    redirectAttributes.addFlashAttribute("message", "The categorie has been saved successfully!");            } catch (Exception e) {
    redirectAttributes.addAttribute("message", e.getMessage());            }
    return "redirect:/categories";        }

    @GetMapping("/categories/{id}")
    public String editCategorie(@PathVariable("id") Integer id,
                                Model model,
                                RedirectAttributes redirectAttributes) {
    try {
      Categorie categorie  = categorieRepository.findById(id).get();
      model.addAttribute("categorie", categorie);
      model.addAttribute("pageNomCategorie", "Edit Categorie (ID: " + id + ")");
      return "categorie_form";            }
    catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
      return "redirect:/categories";
    }
  }

  @GetMapping("/categories/delete/{id}")
  public String deleteCategorie(@PathVariable("id") Integer id,
                                Model model,
                                RedirectAttributes redirectAttributes) {
    try {
      categorieRepository.deleteById(id);
      redirectAttributes.addFlashAttribute("message", "The Categorie with id=" + id + " has been deleted successfully!");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }
    return "redirect:/categories";    }
}
