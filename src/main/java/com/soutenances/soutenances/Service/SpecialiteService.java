package com.soutenances.soutenances.Service;




import com.soutenances.soutenances.DTO.SpecialiteDTO;



import com.soutenances.soutenances.Models.Specialite;

import com.soutenances.soutenances.Repositories.SpecialiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialiteService {

    private final SpecialiteRepository SpecialiteRep;

    public SpecialiteService (SpecialiteRepository SpecialiteRep)
    {
        this.SpecialiteRep=SpecialiteRep;

    }

    public Specialite CreateSpecialite(SpecialiteDTO specialite)
    {

        try {
            Optional<Specialite> existingSalle = SpecialiteRep.findByName(specialite.getName());
            if (existingSalle.isPresent()) {
                throw new IllegalArgumentException("Salle with name " + specialite.getName() + " already exists.");
            }

            // Create Salle
            Specialite salle1 = new Specialite();
            salle1.setName(specialite.getName());
            salle1.setSpecialiteType(specialite.getSpecialiteType());
            return SpecialiteRep.save(salle1);
        } catch (Exception e) {

            throw new RuntimeException("Error creating Salle: " + e.getMessage(), e);
        }
    }
    public void deleteSpecialiteById(int id) {
        try {
            SpecialiteRep.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting specialite: " + e.getMessage(), e);
        }
    }

    public List<Specialite> GetAllSpecialite()
    {
        try
        {
            return SpecialiteRep.findAll();		 }
        catch(Exception e )
        {
            throw new RuntimeException("Error :"+e.getMessage(),e);
        }
    }

    public Specialite EditSpecialite(int id, SpecialiteDTO specialite){

        try {
            Optional<Specialite> existingSpecialite = SpecialiteRep.findById(id);
            if (existingSpecialite.isEmpty()) {
                throw new IllegalArgumentException("There is no salles with this id  " +id);
            }


            // Create and save the new category
            Specialite salle2 = existingSpecialite.get();
            salle2.setName(specialite.getName());
            salle2.setSpecialiteType(specialite.getSpecialiteType());
            return SpecialiteRep.save(salle2);
        } catch (Exception e) {

            throw new RuntimeException("Error creating category: " + e.getMessage(), e);
        }







    }


}
