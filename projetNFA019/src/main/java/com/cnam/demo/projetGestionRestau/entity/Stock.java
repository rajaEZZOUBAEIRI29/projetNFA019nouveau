package com.cnam.demo.projetGestionRestau.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="idStock")
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
    @ManyToOne
    @JoinColumn(name="idUser")
    private User user;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.REMOVE)
    private List<StockHistorique> historiques= new ArrayList<>();

    public Stock() {
    }

    public Stock(Date dateUtilisation, Date dateExpiration,
                 Statut statut, Produit produit, User user,
                 List<StockHistorique> historiques) {
        this.dateUtilisation = dateUtilisation;
        this.dateExpiration = dateExpiration;
        this.statut = statut;
        this.produit = produit;
        this.user = user;
        this.historiques = historiques;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<StockHistorique> getHistoriques() {
        return historiques;
    }

    public void setHistoriques(List<StockHistorique> historiques) {
        this.historiques = historiques;
    }
//rappeller
    /*public void addHistorique(StockHistorique historique) {
        this.historiques.add(historique);
        historique.setStock(this);
    }

    public void removeHistorique(StockHistorique historique) {
        this.historiques.remove(historique);
        historique.setStock(null);
    }
*/
    @Override
    public String toString() {
        return "Stock{" +
                "idStock=" + idStock +
                ", dateUtilisation=" + dateUtilisation +
                ", dateExpiration=" + dateExpiration +
                ", statut=" + statut +
                ", produit=" + produit +
                ", user=" + user +
                ", historiques=" + historiques +
                '}';
    }
}
