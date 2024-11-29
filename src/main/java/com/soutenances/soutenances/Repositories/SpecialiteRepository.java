package com.soutenances.soutenances.Repositories;



import com.soutenances.soutenances.Models.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialiteRepository extends JpaRepository<Specialite,Integer>  {
    Optional<Specialite> findByName(String name);
    Optional<Specialite>findById(Integer id);
}
