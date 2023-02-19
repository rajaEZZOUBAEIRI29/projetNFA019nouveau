package com.cnam.demo.projetGestionRestau.entity;

import javax.persistence.*;

@Entity
@Table(name="StockHistorique")
public class StockHistorique {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="idStock")
    private Integer idStockHistorique;






}
