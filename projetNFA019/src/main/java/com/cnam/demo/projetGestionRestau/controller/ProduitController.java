package com.cnam.demo.projetGestionRestau.controller;

import com.cnam.demo.projetGestionRestau.entity.Categorie;
import com.cnam.demo.projetGestionRestau.entity.Produit;
import com.cnam.demo.projetGestionRestau.repository.CategorieRepository;
import com.cnam.demo.projetGestionRestau.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProduitController {
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("/produits")
    public String getAllProduits(Model model, @Param("keyword") String keyword) {
        try {

            List<Produit> produits = new ArrayList<>();
            if (keyword == null) {
                produitRepository.findAll().forEach(produits::add);
            } else {
                produitRepository.findByNomProduitContainingIgnoreCase(keyword).forEach(produits::add);
                model.addAttribute("keyword", keyword);
            }
            List<Categorie> categories= new ArrayList<>();
            model.addAttribute("categories", categories);
            model.addAttribute("produits",produits);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "produits";
    }

    @GetMapping("/produits/new")
    public String addProduit(Model model){
        List<Categorie> categories= new ArrayList<>();
        categories = categorieRepository.findAll();
        model.addAttribute("categories", categories);
        Produit produit = new Produit();
        model.addAttribute("produit",produit);
        Categorie categorie = new Categorie();
        model.addAttribute("categorie",categorie);
        model.addAttribute("pageTitle", "Create new product");
        return "produit_form";
    }

    @PostMapping("/saveProduit")
    public String saveProduit(@ModelAttribute("produit") Produit produit,
                              @ModelAttribute("categorie") Categorie categorie,
                              @RequestParam("idCategorie") Integer  idCategorie){
        categorie =categorieRepository.findById(idCategorie).get();
        produit.setCategorie(categorie);
        produitRepository.save(produit);
        return"redirect:/produits";
    }

    @GetMapping("/produit/{id}")
    public String editProduit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Categorie> categories= new ArrayList<>();
            categories=categorieRepository.findAll();
            Produit produit =produitRepository.findById(id).get();
            model.addAttribute("produit", produit);
            model.addAttribute("categories",categories);
            model.addAttribute("pageNomProduit", "Edit produit (ID: " + id + ")");
            return "produit_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/produits";
        }
    }

    @GetMapping("/produit/delete/{id}")
    public String deleteProduit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
        produitRepository.deleteById(id);
           redirectAttributes.addFlashAttribute("message", "The product with id=" + id + " has been deleted successfully!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("message", e.getMessage());
    }
    return "redirect:/produits";
    }
}
