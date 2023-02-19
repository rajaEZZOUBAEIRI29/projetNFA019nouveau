package com.cnam.demo.projetGestionRestau.controller;

import com.cnam.demo.projetGestionRestau.entity.Produit;
import com.cnam.demo.projetGestionRestau.entity.Statut;
import com.cnam.demo.projetGestionRestau.entity.Stock;
import com.cnam.demo.projetGestionRestau.entity.User;
import com.cnam.demo.projetGestionRestau.repository.ProduitRepository;
import com.cnam.demo.projetGestionRestau.repository.StockRepository;
import com.cnam.demo.projetGestionRestau.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private UserRepository userRepository;

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
    public String addStock(Model model, Authentication authentication){
    List<Produit> produits= new ArrayList<>();
    produits = produitRepository.findAll();
    model.addAttribute("produits", produits);
    Stock stock = new Stock();
    Produit produit = new Produit();

        // définir la date d'utilisation et la date d'expiration
        Date dateUtilisation = new Date();
        stock.setDateUtilisation(dateUtilisation);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateUtilisation);
        cal.add(Calendar.DATE, (int) produit.getDureeConservation());
        stock.setDateExpiration(cal.getTime());

    model.addAttribute("stock",stock);
    model.addAttribute("produit",produit);
    model.addAttribute("pageTitle", "Create new stock");
        // utilisez l'énumération pour définir le statut de l'utilisateur
    stock.setStatut(Statut.EN_COURS);

        // Ajouter l'utilisateur connecté à l'objet Stock
        String mail = authentication.getName();
        User user = userRepository.findByEmail(mail);
        stock.setUser(user);

    return "stock_form";
}
    @PostMapping("/saveStock")
    public String saveStock(@ModelAttribute("stock") Stock stock,
                            @RequestParam("idProduit") Integer idProduit,
                            Model model, Authentication authentication) {
        Produit produit = produitRepository.findById(idProduit).orElse(null);

        User user = userRepository.findByEmail(authentication.getName());
        stock.setUser(user);

        stock.setProduit(produit);
        stock.setStatut(Statut.EN_COURS);

        // définir la date d'utilisation et la date d'expiration
        Date dateUtilisation = new Date();
        stock.setDateUtilisation(dateUtilisation);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateUtilisation);
        cal.add(Calendar.DATE, (int) produit.getDureeConservation());
        stock.setDateExpiration(cal.getTime());

        stockRepository.save(stock);
        model.addAttribute("produit", produit);
        model.addAttribute("stock", stock);
        return "redirect:/stocks";
    }

//    @GetMapping("/stock/{id}")
//    public String editProduit(@PathVariable("id") Integer id,
//                              Model model,
//                              RedirectAttributes redirectAttributes) {
//        try {
//
//            List<Produit> produits = new ArrayList<>();
//            produits = produitRepository.findAll();
//            Stock stock = stockRepository.findById(id).get();
//            model.addAttribute("stock", stock);
//            model.addAttribute("produits", produits);
//            model.addAttribute("pageNomStock", "Edit produit (ID: " + id + ")");
//            return "stock_edit";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("message", e.getMessage());
//            return "redirect:/stocks";
//        }
//    }
@GetMapping("/stock/{id}")
public String editProduit(@PathVariable("id") Integer id,
                          Model model,
                          RedirectAttributes redirectAttributes) {
    try {
        Stock stock = stockRepository.findById(id).orElse(null);
        Produit produit = null;
        if (stock != null) {
            produit = stock.getProduit();
        }

        if (produit == null) {
            redirectAttributes.addFlashAttribute("message", "Produit avec l'ID " + id + " non trouvé");
            return "redirect:/stocks";
        }

        Calendar cal = Calendar.getInstance();
        stock.setDateUtilisation(new Date());
        cal.setTime(stock.getDateUtilisation());
        cal.add(Calendar.DATE, (int) produit.getDureeConservation());
        stock.setDateExpiration(cal.getTime());

        List<Produit> produits = produitRepository.findAll();

        if (stock.getDateExpiration().before(new Date())) {
            stock.setStatut(Statut.EXPIRE);
            stockRepository.save(stock);
            return "redirect:/stocks";
        }

        model.addAttribute("stock", stock);
        model.addAttribute("produits", produits);
        model.addAttribute("pageNomStock", "Edit produit (ID: " + id + ")");
        return "stock_edit";

    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("message", e.getMessage());
    }
    return "redirect:/stocks";
}

    @GetMapping("/update-stock-status")
    public ResponseEntity<String> updateStockStatus() {
        List<Stock> expiredProducts= stockRepository.findAll();
        for (Stock produitStock : expiredProducts) {
            if (produitStock.getDateExpiration().before(new Date())) {
                produitStock.setStatut(Statut.EXPIRE);
                stockRepository.save(produitStock);
            }
        }
        return new ResponseEntity<>("Statut de stock mis à jour avec succès", HttpStatus.OK);
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
