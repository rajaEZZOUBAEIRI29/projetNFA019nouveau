package com.cnam.demo.projetGestionRestau.controller;

import com.cnam.demo.projetGestionRestau.entity.TempHistorique;
import com.cnam.demo.projetGestionRestau.entity.Temperature;
import com.cnam.demo.projetGestionRestau.repository.TempHistoriqueRepository;
import com.cnam.demo.projetGestionRestau.repository.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TempHistoriqueController {
    @Autowired
    private TempHistoriqueRepository tempHistoriqueRepository;
    @Autowired
    private TemperatureRepository temperatureRepository;

    @GetMapping("/tempHistoriques")
    public String showHistoriqueTemperature(Model model) {

        List<TempHistorique> tempHistoriques = tempHistoriqueRepository.findAll();
        List<Temperature> temperatures=temperatureRepository.findAll();
        model.addAttribute("temperatures",temperatures);
        model.addAttribute("tempHistoriques", tempHistoriques);
        return "tempHistoriques";
    }
}
