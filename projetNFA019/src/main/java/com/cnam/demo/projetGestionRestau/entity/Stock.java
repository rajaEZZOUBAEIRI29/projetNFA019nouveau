package com.cnam.demo.projetGestionRestau.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Stocks")

public class Stock {
    @Column(name="idStock")
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer idStock;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateUtilisation", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date dateUtilisation;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateExpiration")
    Date dateExpiration;
    @Column(name="statut")
    @Enumerated(EnumType.STRING)
    private Statut statut;
    @ManyToOne
    @JoinColumn(name="idProduit")
    private Produit produit;

    public Stock() {
    }

    public Stock(Date dateUtilisation, Date dateExpiration, Statut statut, Produit produit) {
        this.dateUtilisation = dateUtilisation;
        this.dateExpiration = dateExpiration;
        this.statut = statut;
        this.produit = produit;
    }

    public Integer getIdStock() {
        return idStock;
    }

    public void setIdStock(Integer idStock) {
        this.idStock = idStock;
    }

    public Date getDateUtilisation() {
        return dateUtilisation;
    }

    public void setDateUtilisation(Date dateUtilisation) {
        this.dateUtilisation = dateUtilisation;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "idStock=" + idStock +
                ", dateUtilisation=" + dateUtilisation +
                ", dateExpiration=" + dateExpiration +
                ", statut=" + statut +
                ", produit=" + produit +
                '}';
    }
}
