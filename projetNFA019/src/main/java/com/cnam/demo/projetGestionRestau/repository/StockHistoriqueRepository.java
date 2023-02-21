package com.cnam.demo.projetGestionRestau.repository;

import com.cnam.demo.projetGestionRestau.entity.Stock;
import com.cnam.demo.projetGestionRestau.entity.StockHistorique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockHistoriqueRepository extends JpaRepository<StockHistorique, Integer> {
    List<StockHistorique> findByStock(Stock stock);
}
