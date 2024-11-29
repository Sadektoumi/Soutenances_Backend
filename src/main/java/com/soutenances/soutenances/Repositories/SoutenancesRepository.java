package com.soutenances.soutenances.Repositories;


import com.soutenances.soutenances.Models.Soutenance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Repository
@EnableJpaRepositories
public interface SoutenancesRepository extends JpaRepository<Soutenance,Integer> {

    Optional<Soutenance> findById(Integer id);
    List<Soutenance>findSoutenanceByDateSoutenanceAndAndHeureSoutenance(LocalDate date, LocalTime time);


}
