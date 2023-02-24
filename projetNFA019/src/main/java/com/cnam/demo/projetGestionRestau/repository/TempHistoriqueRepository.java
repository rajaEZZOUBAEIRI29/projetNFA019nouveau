package com.cnam.demo.projetGestionRestau.repository;

import com.cnam.demo.projetGestionRestau.entity.TempHistorique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TempHistoriqueRepository extends JpaRepository<TempHistorique, Integer> {
}
