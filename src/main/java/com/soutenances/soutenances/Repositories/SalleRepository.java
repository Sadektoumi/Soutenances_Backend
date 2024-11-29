package com.soutenances.soutenances.Repositories;

import com.soutenances.soutenances.Models.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface SalleRepository extends JpaRepository<Salle,Integer> {

    Optional<Salle> findByName(String name);
    Optional<Salle>findById(Integer id);
    List<Salle> findBysalleIdNotIn(List<Integer> salleIds);




}
