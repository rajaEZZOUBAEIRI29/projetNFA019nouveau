package com.cnam.demo.projetGestionRestau.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "temperatures")
public class Temperature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idTemperature;
    @Column(name = "tempMatin")
    private float temperatureMatin;
    @Column(name = "tempSoir")
    private float temperatureSoir;
    @ManyToOne
    @JoinColumn(name="idFrigo")
    private Frigo frigo;

    @ManyToOne
    @JoinColumn(name="idUser")
    private User user;

    @OneToMany(mappedBy = "temperature")
    private List<TempHistorique> tempHistoriques= new ArrayList<>();

    public Temperature() {
    }

    public Temperature(float temperatureMatin, float temperatureSoir,
                       Frigo frigo, User user, List<TempHistorique> tempHistoriques) {
        this.temperatureMatin = temperatureMatin;
        this.temperatureSoir = temperatureSoir;
        this.frigo = frigo;
        this.user = user;
        this.tempHistoriques = tempHistoriques;
    }

    public Integer getIdTemperature() {
        return idTemperature;
    }

    public void setIdTemperature(Integer idTemperature) {
        this.idTemperature = idTemperature;
    }

    public float getTemperatureMatin() {
        return temperatureMatin;
    }

    public void setTemperatureMatin(float temperatureMatin) {
        this.temperatureMatin = temperatureMatin;
    }

    public float getTemperatureSoir() {
        return temperatureSoir;
    }

    public void setTemperatureSoir(float temperatureSoir) {
        this.temperatureSoir = temperatureSoir;
    }

    public Frigo getFrigo() {
        return frigo;
    }

    public void setFrigo(Frigo frigo) {
        this.frigo = frigo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TempHistorique> getTempHistoriques() {
        return tempHistoriques;
    }

    public void setTempHistoriques(List<TempHistorique> tempHistoriques) {
        this.tempHistoriques = tempHistoriques;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "idTemperature=" + idTemperature +
                ", temperatureMatin=" + temperatureMatin +
                ", temperatureSoir=" + temperatureSoir +
                ", frigo=" + frigo +
                ", user=" + user +
                ", tempHistoriques=" + tempHistoriques +
                '}';
    }
}

