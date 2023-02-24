package com.cnam.demo.projetGestionRestau.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tempHistoriques")
public class TempHistorique {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="idTempHistorique")
    private Integer idTempHistorique;

    @Column(name = "typeAction")
    private String typeAction;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateHistorique")
    Date dateHistorique;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="idTemperature", nullable = false)
    private Temperature temperature;

    public TempHistorique() {
    }

    public TempHistorique(String typeAction, Date dateHistorique, Temperature temperature) {
        this.typeAction = typeAction;
        this.dateHistorique = dateHistorique;
        this.temperature = temperature;
    }

    public Integer getIdTempHistorique() {
        return idTempHistorique;
    }

    public void setIdTempHistorique(Integer idTempHistorique) {
        this.idTempHistorique = idTempHistorique;
    }

    public String getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(String typeAction) {
        this.typeAction = typeAction;
    }

    public Date getDateHistorique() {
        return dateHistorique;
    }

    public void setDateHistorique(Date dateHistorique) {
        this.dateHistorique = dateHistorique;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "TempHistorique{" +
                "idTempHistorique=" + idTempHistorique +
                ", typeAction='" + typeAction + '\'' +
                ", dateHistorique=" + dateHistorique +
                ", temperature=" + temperature +
                '}';
    }
}
