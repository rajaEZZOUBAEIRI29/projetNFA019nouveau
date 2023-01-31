package com.cnam.demo.projetGestionRestau.controller;

import com.cnam.demo.projetGestionRestau.entity.Categorie;
import com.cnam.demo.projetGestionRestau.entity.Produit;
import com.cnam.demo.projetGestionRestau.entity.Statut;
import com.cnam.demo.projetGestionRestau.entity.Stock;
import com.cnam.demo.projetGestionRestau.repository.CategorieRepository;
import com.cnam.demo.projetGestionRestau.repository.ProduitRepository;
import com.cnam.demo.projetGestionRestau.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class StockController {
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/stocks")
    public String getAllStocks(Model model,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "statut", required = false) Statut statut) {
        try {
            List<Stock> stocks = new ArrayList<>();
            if (keyword != null) {
                stocks = stockRepository.findByProduitNomProduitLikeIgnoreCase("%" + keyword + "%");
            } else if (statut != null) {
                stocks = stockRepository.findByStatut(statut);
            } else {
                stockRepository.findAll().forEach(stocks::add);
            }
            model.addAttribute("stocks", stocks);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "stocks";
    }
    @GetMapping("/stocks/new")
    public String addStock(Model model){
    List<Produit> produits= new ArrayList<>();
    produits = produitRepository.findAll();
    model.addAttribute("produits", produits);
    Stock stock = new Stock();
    Produit produit = new Produit();

    // définir la date d'expiration
        Calendar cal = Calendar.getInstance();
        stock.setDateUtilisation(new Date());
        cal.setTime(stock.getDateUtilisation());
        cal.add(Calendar.DATE, (int) produit.getDureeConservation());
        stock.setDateExpiration(cal.getTime());


    model.addAttribute("stock",stock);
    model.addAttribute("produit",produit);
    model.addAttribute("pageTitle", "Create new stock");
        // utilisez l'énumération pour définir le statut de l'utilisateur
    stock.setStatut(Statut.EN_COURS);
    return "stock_form";
}
    @PostMapping("/saveStock")
    public String saveStock(@ModelAttribute("stock") Stock stock,
                            @RequestParam("idProduit") Integer idProduit,
                            //@RequestParam("statut") Statut statut,
                            Model model) {
        Produit produit = produitRepository.findById(idProduit).orElse(null);
        stock.setProduit(produit);
        stock.setStatut(Statut.EN_COURS);

        //ajouter aujourd hui
        Calendar cal = Calendar.getInstance();
        stock.setDateUtilisation(new Date());
        cal.setTime(stock.getDateUtilisation());
        cal.add(Calendar.DATE, (int) produit.getDureeConservation());
        stock.setDateExpiration(cal.getTime());

        stockRepository.save(stock);
        model.addAttribute("produit", produit);
        model.addAttribute("stock", stock);
        return "redirect:/stocks";
    }

    @GetMapping("/stock/{id}")
    public String editProduit(@PathVariable("id") Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
    try {
        List<Produit> produits= new ArrayList<>();
        produits=produitRepository.findAll();
        Stock stock=stockRepository.findById(id).get();
        model.addAttribute("stock", stock);
        model.addAttribute("produits",produits);
        model.addAttribute("pageNomStock", "Edit produit (ID: " + id + ")");
    return "stock_edit";
    } catch (Exception e) {
    redirectAttributes.addFlashAttribute("message", e.getMessage());
    return "redirect:/stocks";
    }
    }
    @GetMapping("/stock/delete/{id}")
    public String deleteProduit(@PathVariable("id") Integer id,
                                RedirectAttributes redirectAttributes) {
        try {
            stockRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "The product with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/stocks";
    }
}
