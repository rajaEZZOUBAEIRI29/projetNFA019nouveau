package com.cnam.demo.projetGestionRestau.repository;

import com.cnam.demo.projetGestionRestau.entity.StockHistorique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHistoriqueRepository extends JpaRepository<StockHistorique, Integer> {
}
