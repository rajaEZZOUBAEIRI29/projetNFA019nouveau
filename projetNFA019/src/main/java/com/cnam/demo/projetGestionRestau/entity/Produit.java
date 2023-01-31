package com.cnam.demo.projetGestionRestau.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "produits")
public class Produit implements Serializable {
    @Column(name = "idProduit")
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer idProduit;
    @Column(name = "nomProduit")
    private String nomProduit;
    @Column(name = "dureeProduit")
    private long dureeConservation;
    @ManyToOne
    @JoinColumn(name="idCategorie")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit")
    private List<Stock> stocks;

    public Produit() {
    }

    public Produit(String nomProduit, long dureeConservation, Categorie categorie, List<Stock> stocks) {
        this.nomProduit = nomProduit;
        this.dureeConservation = dureeConservation;
        this.categorie = categorie;
        this.stocks = stocks;
    }

    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public long getDureeConservation() {
        return dureeConservation;
    }

    public void setDureeConservation(long dureeConservation) {
        this.dureeConservation = dureeConservation;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "idProduit=" + idProduit +
                ", nomProduit='" + nomProduit + '\'' +
                ", DureeConservation=" + dureeConservation +
                ", categorie=" + categorie +
                ", stocks=" + stocks +
                '}';
    }
}
