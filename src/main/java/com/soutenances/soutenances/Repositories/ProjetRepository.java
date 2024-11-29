package com.soutenances.soutenances.Repositories;


import com.soutenances.soutenances.Models.Projet;
import com.soutenances.soutenances.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetRepository extends JpaRepository<Projet,Integer> {


    Optional<Projet> findById(Integer id);

    Optional<Projet>findBySujet(String sujet);


    List<Projet>findByProjetidNotIn(List<Integer>Projetids);


}
