package com.soutenances.soutenances.Models;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "Projet")
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projetId")
    private int  projetid;

    @Column(name = "sujet")
    private String sujet ;

    public int getProjetid() {
        return projetid;
    }

    public void setProjetid(int projetid) {
        this.projetid = projetid;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public User getEtudiant_id() {
        return Etudiant_id;
    }

    public void setEtudiant_id(User etudiant_id) {
        Etudiant_id = etudiant_id;
    }

    public User getEncadrant_id() {
        return Encadrant_id;
    }

    public void setEncadrant_id(User encadrant_id) {
        Encadrant_id = encadrant_id;
    }

    @ManyToOne
    @JoinColumn(name = "Etudiant_id",nullable = true)
    @JsonIgnore


    private User Etudiant_id ;
    @ManyToOne
    @JoinColumn(name = "Etudiant2_id",nullable = true)
    @JsonIgnore


    private User Etudiant2_id ;

    public User getEtudiant2_id() {
        return Etudiant2_id;
    }

    public void setEtudiant2_id(User etudiant2_id) {
        Etudiant2_id = etudiant2_id;
    }

    @ManyToOne
    @JoinColumn(name = "Encadrant_id",nullable = true)
    @JsonIgnore


    private User Encadrant_id ;



}
