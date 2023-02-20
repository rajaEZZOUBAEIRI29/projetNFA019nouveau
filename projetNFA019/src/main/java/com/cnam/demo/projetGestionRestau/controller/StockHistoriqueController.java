package com.cnam.demo.projetGestionRestau.controller;

import com.cnam.demo.projetGestionRestau.entity.StockHistorique;
import com.cnam.demo.projetGestionRestau.repository.StockHistoriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StockHistoriqueController {
    @Autowired
    private StockHistoriqueRepository stockHistoriqueRepository;

    @GetMapping("/stockHistoriques")
    public String showHistorique(Model model) {
        List<StockHistorique> stockHistoriques = stockHistoriqueRepository.findAll();
        model.addAttribute("stockHistoriques", stockHistoriques);
        return "stockHistoriques";
    }
}
