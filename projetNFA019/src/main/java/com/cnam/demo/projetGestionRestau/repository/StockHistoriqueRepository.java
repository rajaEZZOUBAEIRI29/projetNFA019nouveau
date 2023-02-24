package com.cnam.demo.projetGestionRestau.repository;

import com.cnam.demo.projetGestionRestau.entity.Stock;
import com.cnam.demo.projetGestionRestau.entity.StockHistorique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface StockHistoriqueRepository extends JpaRepository<StockHistorique, Integer> {
    List<StockHistorique> findByStock(Stock stock);
}
