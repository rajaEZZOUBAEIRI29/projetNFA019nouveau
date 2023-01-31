package com.cnam.demo.projetGestionRestau.repository;
import com.cnam.demo.projetGestionRestau.entity.Statut;
import com.cnam.demo.projetGestionRestau.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface StockRepository extends JpaRepository<Stock, Integer> {

    List<Stock> findByProduitNomProduitLikeIgnoreCase(String keyword);
    List<Stock> findByStatut(Statut statut);
}
