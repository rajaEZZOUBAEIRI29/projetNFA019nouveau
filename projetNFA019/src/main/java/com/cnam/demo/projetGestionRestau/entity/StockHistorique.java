package com.cnam.demo.projetGestionRestau.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="StockHistorique")
public class StockHistorique {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="idStockHistorique")
    private Integer idStockHistorique;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateAction")
    Date dateAction;


    @Column(name="typeAction")
    private String typeAction; // Ajout, édition, suppression

    @Column(name="statut")
    @Enumerated(EnumType.STRING)
    private Statut statut;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="idStock", nullable = false)
    private Stock stock;

    public StockHistorique() {
    }

    public StockHistorique(Date dateAction, String typeAction, Statut statut, Stock stock) {
        this.dateAction = dateAction;
        this.typeAction = typeAction;
        this.statut = statut;
        this.stock = stock;
    }

    public Integer getIdStockHistorique() {
        return idStockHistorique;
    }

    public void setIdStockHistorique(Integer idStockHistorique) {
        this.idStockHistorique = idStockHistorique;
    }

    public Date getDateAction() {
        return dateAction;
    }

    public void setDateAction(Date dateAction) {
        this.dateAction = dateAction;
    }

    public String getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(String typeAction) {
        this.typeAction = typeAction;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "StockHistorique{" +
                "idStockHistorique=" + idStockHistorique +
                ", dateAction=" + dateAction +
                ", typeAction='" + typeAction + '\'' +
                ", statut=" + statut +
                ", stock=" + stock +
                '}';
    }
}
