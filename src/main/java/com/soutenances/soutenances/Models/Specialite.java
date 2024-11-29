package com.soutenances.soutenances.Models;


import com.soutenances.soutenances.Models.ModelsEnum.SpecialiteType;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="Specialite")
public class Specialite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SpecialiteId")
    private int specialitéID;


    @Column(name="SpecialiteName")
    private String name ;

    @Column(name = "Specialitetype")
    @Enumerated(EnumType.STRING)
    private SpecialiteType specialiteType;

    @OneToMany(mappedBy = "grp")
    private Set<Groupe>JoinGrpSpecialite;



    public int getSpecialitéID() {
        return specialitéID;
    }

    public void setSpecialitéID(int specialitéID) {
        this.specialitéID = specialitéID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpecialiteType getSpecialiteType() {
        return specialiteType;
    }

    public void setSpecialiteType(SpecialiteType specialiteType) {
        this.specialiteType = specialiteType;
    }
}
