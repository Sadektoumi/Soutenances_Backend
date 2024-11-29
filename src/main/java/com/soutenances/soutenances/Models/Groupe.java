package com.soutenances.soutenances.Models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "groupe")
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GroupeId")
    private int groupeId;

    @Column(name="Name")
    private String name ;

    public int getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(int groupeId) {
        this.groupeId = groupeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Specialite getGrp() {
        return grp;
    }

    public void setGrp(Specialite grp) {
        this.grp = grp;
    }

    @ManyToOne
    @JoinColumn(name = "specialite_ID",nullable =true)
    private Specialite grp;

    @OneToMany(mappedBy = "groupe")
    private Set<User> JoinUserGroupe;
}
