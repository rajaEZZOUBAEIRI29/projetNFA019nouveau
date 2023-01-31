package com.cnam.demo.projetGestionRestau.repository;

import com.cnam.demo.projetGestionRestau.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProduitRepository extends JpaRepository<Produit, Integer> {
    List<Produit> findByNomProduitContainingIgnoreCase(String keyword);
}
