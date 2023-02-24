package com.cnam.demo.projetGestionRestau.entity;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "frigos")
public class Frigo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idFrigo;
    @Column(name="nomFrigo",length = 128, nullable = false)
    private String nomFrigo;
    @Column(name = "tempMin")
    private double temperatureMinAutorise;
    @Column(name = "tempMax")
    private double temperatureMaxAutorise;
    @OneToMany(mappedBy = "frigo")
    private List<Temperature> temperatures;

    public Frigo() {
    }

    public Frigo(String nomFrigo, double temperatureMinAutorise, double temperatureMaxAutorise, List<Temperature> temperatures) {
        this.nomFrigo = nomFrigo;
        this.temperatureMinAutorise = temperatureMinAutorise;
        this.temperatureMaxAutorise = temperatureMaxAutorise;
        this.temperatures = temperatures;
    }

    public Integer getIdFrigo() {
        return idFrigo;
    }

    public void setIdFrigo(Integer idFrigo) {
        this.idFrigo = idFrigo;
    }

    public String getNomFrigo() {
        return nomFrigo;
    }

    public void setNomFrigo(String nomFrigo) {
        this.nomFrigo = nomFrigo;
    }

    public double getTemperatureMinAutorise() {
        return temperatureMinAutorise;
    }

    public void setTemperatureMinAutorise(double temperatureMinAutorise) {
        this.temperatureMinAutorise = temperatureMinAutorise;
    }

    public double getTemperatureMaxAutorise() {
        return temperatureMaxAutorise;
    }

    public void setTemperatureMaxAutorise(double temperatureMaxAutorise) {
        this.temperatureMaxAutorise = temperatureMaxAutorise;
    }

    public List<Temperature> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<Temperature> temperatures) {
        this.temperatures = temperatures;
    }

    @Override
    public String toString() {
        return "Frigo{" +
                "idFrigo=" + idFrigo +
                ", nomFrigo='" + nomFrigo + '\'' +
                ", temperatureMinAutorise=" + temperatureMinAutorise +
                ", temperatureMaxAutorise=" + temperatureMaxAutorise +
                ", temperatures=" + temperatures +
                '}';
    }
}
