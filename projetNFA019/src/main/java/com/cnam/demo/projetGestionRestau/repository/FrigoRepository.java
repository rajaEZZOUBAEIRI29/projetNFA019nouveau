package com.cnam.demo.projetGestionRestau.repository;

import com.cnam.demo.projetGestionRestau.entity.Frigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FrigoRepository extends JpaRepository<Frigo, Integer> {
    List<Frigo> findByNomFrigoContainingIgnoreCase(String keyword);
}
