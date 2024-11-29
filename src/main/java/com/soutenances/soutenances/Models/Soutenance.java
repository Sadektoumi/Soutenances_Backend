package com.soutenances.soutenances.Models;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Soutenances")
public class Soutenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SoutenanceId")

    private int SoutenanceId;

    @Column(name = "Date")
    private LocalDate dateSoutenance;

    @Column(name = "Time")
    private LocalTime heureSoutenance;

    @ManyToOne
    @JoinColumn(name = "Salle_id",nullable = true)
    private Salle salle ;

    @ManyToOne
    @JoinColumn(name = "Rapporteur_id",nullable = true)
    private User Rapporteur;

    @ManyToOne
    @JoinColumn(name = "Jury_id",nullable = true)
    private  User Jury ;

    @ManyToOne
    @JoinColumn(name = "Projet_id",nullable = true)
    private Projet projet;

    public int getSoutenanceId() {
        return SoutenanceId;
    }

    public void setSoutenanceId(int soutenanceId) {
        SoutenanceId = soutenanceId;
    }

    public LocalDate getDateSoutenance() {
        return dateSoutenance;
    }

    public void setDateSoutenance(LocalDate dateSoutenance) {
        this.dateSoutenance = dateSoutenance;
    }

    public LocalTime getHeureSoutenance() {
        return heureSoutenance;
    }

    public void setHeureSoutenance(LocalTime heureSoutenance) {
        this.heureSoutenance = heureSoutenance;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public User getRapporteur() {
        return Rapporteur;
    }

    public void setRapporteur(User rapporteur) {
        Rapporteur = rapporteur;
    }

    public User getJury() {
        return Jury;
    }

    public void setJury(User jury) {
        Jury = jury;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }
}
