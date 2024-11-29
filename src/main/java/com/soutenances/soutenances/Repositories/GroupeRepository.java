package com.soutenances.soutenances.Repositories;

import com.soutenances.soutenances.Models.Groupe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe,Integer> {

    Optional<Groupe> findByName(String name);
    Optional<Groupe> findById(Integer id);


}
