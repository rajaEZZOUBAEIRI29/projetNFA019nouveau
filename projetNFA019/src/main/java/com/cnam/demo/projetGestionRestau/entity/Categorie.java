package com.cnam.demo.projetGestionRestau.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categories")
public class Categorie implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer idCategorie;
  @Column(name = "nomCategorie",length = 128, nullable = false)
  private String nomCategorie;
  @Column(length = 256)
  private String description;
  @OneToMany(mappedBy = "categorie")
  private List<Produit> produits;

  public Categorie() {
  }

  public Categorie(String nomCategorie, String description, List<Produit> produits) {
    this.nomCategorie = nomCategorie;
    this.description = description;
    this.produits = produits;
  }

  public Integer getIdCategorie() {
    return idCategorie;
  }

  public void setIdCategorie(Integer idCategorie) {
    this.idCategorie = idCategorie;
  }

  public String getNomCategorie() {
    return nomCategorie;
  }

  public void setNomCategorie(String nomCategorie) {
    this.nomCategorie = nomCategorie;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Produit> getProduits() {
    return produits;
  }

  public void setProduits(List<Produit> produits) {
    this.produits = produits;
  }

  @Override
  public String toString() {
    return "Categorie{" +
            "idCategorie=" + idCategorie +
            ", nomCategorie='" + nomCategorie + '\'' +
            ", description='" + description + '\'' +
            ", produits=" + produits +
            '}';
  }
}
