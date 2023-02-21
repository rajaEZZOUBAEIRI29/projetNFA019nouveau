package com.cnam.demo.projetGestionRestau.entity;

import javax.persistence.*;

@Entity
public class Temperature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idTemperature;
    @Column(name = "tempMatin")
    private double TemperatureMatin;
    @Column(name = "tempSoir")
    private double TemperatureSoir;
    @ManyToOne
    @JoinColumn(name="idFrogo")
    private Frigo frigo;

    @ManyToOne
    @JoinColumn(name="idUser")
    private User user;

    public Temperature() {
    }

    public Temperature(double temperatureMatin, double temperatureSoir, Frigo frigo, User user) {
        TemperatureMatin = temperatureMatin;
        TemperatureSoir = temperatureSoir;
        this.frigo = frigo;
        this.user = user;
    }

    public Integer getIdTemperature() {
        return idTemperature;
    }

    public void setIdTemperature(Integer idTemperature) {
        this.idTemperature = idTemperature;
    }

    public double getTemperatureMatin() {
        return TemperatureMatin;
    }

    public void setTemperatureMatin(double temperatureMatin) {
        TemperatureMatin = temperatureMatin;
    }

    public double getTemperatureSoir() {
        return TemperatureSoir;
    }

    public void setTemperatureSoir(double temperatureSoir) {
        TemperatureSoir = temperatureSoir;
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

    @Override
    public String toString() {
        return "Temperature{" +
                "idTemperature=" + idTemperature +
                ", TemperatureMatin=" + TemperatureMatin +
                ", TemperatureSoir=" + TemperatureSoir +
                ", frigo=" + frigo +
                ", user=" + user +
                '}';
    }
}
