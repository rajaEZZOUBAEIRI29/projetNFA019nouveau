package com.cnam.demo.projetGestionRestau.controller;

import com.cnam.demo.projetGestionRestau.entity.*;
import com.cnam.demo.projetGestionRestau.repository.ProduitRepository;
import com.cnam.demo.projetGestionRestau.repository.StockHistoriqueRepository;
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

import java.time.LocalDateTime;
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
    @Autowired
    private StockHistoriqueRepository stockHistoriqueRepository;

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
                            @RequestParam(name = "typeAction", required = false) String typeAction,
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

        // créer un objet StockHistorique pour enregistrer l'action
        StockHistorique stockHistorique = new StockHistorique();
        stockHistorique.setDateAction(new Date());
        stockHistorique.setTypeAction(typeAction);
        stockHistorique.setStatut(stock.getStatut());
        stockHistorique.setTypeAction("ajouter");
        stockHistorique.setStock(stock);

        // sauvegarder l'historique dans la base de données
        stockHistoriqueRepository.save(stockHistorique);

        return "redirect:/stocks";
    }

@GetMapping("/stock/{id}")
public String editStock(@PathVariable("id") Integer id,
                          @RequestParam(name = "typeAction", required = false) String typeAction,
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

            // créer un objet StockHistorique pour enregistrer l'action
            StockHistorique stockHistorique = new StockHistorique();
            stockHistorique.setDateAction(new Date());
            stockHistorique.setTypeAction(typeAction);
            stockHistorique.setStatut(stock.getStatut());
            stockHistorique.setTypeAction("editer ");
            stockHistorique.setStock(stock);

            // sauvegarder l'historique dans la base de données
            stockHistoriqueRepository.save(stockHistorique);

            return "redirect:/stocks";
        } else {
            stock.setStatut(Statut.CONSOMME);
            stockRepository.save(stock);

            // créer un objet StockHistorique pour enregistrer l'action
            StockHistorique stockHistorique = new StockHistorique();
            stockHistorique.setDateAction(new Date());
            stockHistorique.setTypeAction(typeAction);
            stockHistorique.setStatut(stock.getStatut());
            stockHistorique.setTypeAction("editer");
            stockHistorique.setStock(stock);

            // sauvegarder l'historique dans la base de données
            stockHistoriqueRepository.save(stockHistorique);

            model.addAttribute("stock", stock);
            model.addAttribute("produits", produits);
            model.addAttribute("pageNomStock", "Edit produit (ID: " + id + ")");
            return "redirect:/stocks";
    }

    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("message", e.getMessage());
    }
    return "redirect:/stocks";
}

    @GetMapping("/stock/delete/{id}")
    public String deleteStock(@PathVariable("id") Integer id,
                                @RequestParam(name = "typeAction", required = false) String typeAction,
                                RedirectAttributes redirectAttributes) {
        try {
            // Trouver le stock à supprimer
            Stock stock = stockRepository.findById(id).orElse(null);

            // Si le stock n'existe pas, rediriger vers la liste des stocks avec un message d'erreur
            if (stock == null) {
                redirectAttributes.addFlashAttribute("message", "Stock with id=" + id + " not found");
                return "redirect:/stocks";
            }

            // Créer un nouvel objet StockHistorique pour l'action de suppression
            StockHistorique stockHistorique = new StockHistorique();
            stockHistorique.setDateAction(new Date());
            stockHistorique.setTypeAction("supprimer");
            stockHistorique.setStatut(stock.getStatut());
            stockHistorique.setStock(stock);

            // Ajouter l'objet StockHistorique à la liste historiques du Stock
            stock.getHistoriques().add(stockHistorique);

            // Supprimer le Stock de la base de données
            stockRepository.delete(stock);

            redirectAttributes.addFlashAttribute("message", "Le stock avec l'ID=" + id + " a été supprimé avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/stocks";
    }



 /*   @GetMapping("/stock/delete/{id}")
    public String deleteProduit(@PathVariable("id") Integer id,
                                @RequestParam(name = "typeAction", required = false) String typeAction,
                                RedirectAttributes redirectAttributes) {
        try {
            stockRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "The product with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/stocks";
    }*/
}
