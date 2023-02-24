package com.cnam.demo.projetGestionRestau.controller;

import com.cnam.demo.projetGestionRestau.entity.*;
import com.cnam.demo.projetGestionRestau.repository.FrigoRepository;
import com.cnam.demo.projetGestionRestau.repository.TempHistoriqueRepository;
import com.cnam.demo.projetGestionRestau.repository.TemperatureRepository;
import com.cnam.demo.projetGestionRestau.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TempController {
    @Autowired
    private TemperatureRepository temperatureRepository;
    @Autowired
    private FrigoRepository frigoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TempHistoriqueRepository tempHistoriqueRepository;

    @GetMapping("/temperatures")
    public String getAllTemperatures(Model model) {
        try {
            List<Temperature> temperatures = new ArrayList<>();
            temperatures=temperatureRepository.findAll();
            List<Frigo> frigos= new ArrayList<>();

            model.addAttribute("frigos", frigos);
            model.addAttribute("temperatures",temperatures);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "temperatures";
    }
    @GetMapping("/temperatures/new")
    public String addTemperatures(Model model, Authentication authentication){
        List<Frigo> frigos= new ArrayList<>();
        frigos = frigoRepository.findAll();
        model.addAttribute("frigos", frigos);
        Temperature temperature=new Temperature();
        model.addAttribute("temperature",temperature);
        Frigo frigo=new Frigo();
        model.addAttribute("frigo",frigo);
        model.addAttribute("pageTitle", "Create new temperature");

        // Ajouter l'utilisateur connecté à l'objet Stock
        String mail = authentication.getName();
        User user = userRepository.findByEmail(mail);
        temperature.setUser(user);

        return "temperature_form";
    }
    @PostMapping("/temperatures/save")
    public String saveTemperature(@ModelAttribute("temperature") Temperature temperature ,
                              @ModelAttribute("frigo") Frigo frigo ,
                              @RequestParam("idFrigo") Integer  idFrigo,
                              Model model,Authentication authentication){
        frigo =frigoRepository.findById(idFrigo).get();

        // Vérification des températures minimale et maximale autorisées
        if (temperature.getTemperatureMatin() < frigo.getTemperatureMinAutorise() || temperature.getTemperatureMatin() > frigo.getTemperatureMaxAutorise()
                || temperature.getTemperatureSoir() < frigo.getTemperatureMinAutorise() || temperature.getTemperatureSoir() > frigo.getTemperatureMaxAutorise()) {
            model.addAttribute("error", "La température matin ou la température soir ne respecte pas les limites autorisées.");
            model.addAttribute("frigos", frigoRepository.findAll());
            return "temperature_form";
        }

        temperature.setFrigo(frigo);
        // save l'utilisateur connecté à l'objet Stock
        User user = userRepository.findByEmail(authentication.getName());
        temperature.setUser(user);

        temperatureRepository.save(temperature);
        model.addAttribute("temperature", temperature);
        // Save the temperature history
        TempHistorique tempHistorique = new TempHistorique();
        tempHistorique.setDateHistorique(new Date());
        tempHistorique.setTypeAction("Ajouter");
        temperature.setTemperatureMatin(temperature.getTemperatureMatin());
        temperature.setTemperatureSoir(temperature.getTemperatureSoir());
        temperature.setFrigo(temperature.getFrigo());
        tempHistorique.setTemperature(temperature);
        tempHistoriqueRepository.save(tempHistorique);
        return"redirect:/temperatures";
    }
    @GetMapping("/temperature/{id}")
    public String editTemperature(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Frigo> frigos= new ArrayList<>();
            frigos=frigoRepository.findAll();
            Temperature temperature  =temperatureRepository.findById(id).get();

            // créer un objet StockHistorique pour enregistrer l'action
            TempHistorique tempHistorique = new TempHistorique();
            tempHistorique.setDateHistorique(new Date());
            tempHistorique.setTemperature(temperature);

            // sauvegarder l'historique dans la base de données
            tempHistoriqueRepository.save(tempHistorique);

            model.addAttribute("temperature", temperature);
            model.addAttribute("frigos",frigos);
            model.addAttribute( "Edit temperature (ID: " + id + ")");
            return "temperature_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/temperatures";
        }
    }
    @GetMapping("/temperature/delete/{id}")
    public String deleteTemperature(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            temperatureRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "The temperature with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/temperatures";
    }
}
