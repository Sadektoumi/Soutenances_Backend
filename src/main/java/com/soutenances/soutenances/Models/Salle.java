package com.soutenances.soutenances.Models;


import com.soutenances.soutenances.Models.ModelsEnum.SalleType;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="Salles")
public class Salle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "SalleId")
    private int salleId; /*SalleID*/;

    @Column(name="Name")
    private String name ;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SalleType type;

    @OneToMany
    private Set<Soutenance> JoinSalleSoutenance;


    /*public int getSalleID() {
        return SalleID;
    }

    public void setSalleID(int salleID) {
        SalleID = salleID;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SalleType getType() {
        return type;
    }

    public void setType(SalleType type) {
        this.type = type;
    }

    public Salle() {
    }

    public int getSalleId() {
        return salleId;
    }

    public void setSalleId(int salleId) {
        this.salleId = salleId;
    }
}
