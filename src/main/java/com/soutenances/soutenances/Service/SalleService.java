package com.soutenances.soutenances.Service;


import com.soutenances.soutenances.DTO.SalleDTO;
import com.soutenances.soutenances.Models.Salle;
import com.soutenances.soutenances.Models.Soutenance;
import com.soutenances.soutenances.Repositories.SalleRepository;
import com.soutenances.soutenances.Repositories.SoutenancesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SalleService {

    private final SalleRepository SalleRep;

    private final SoutenancesRepository soutenancesRepository;




    public SalleService (SalleRepository SalleRep, SoutenancesRepository soutenancesRep, SoutenancesRepository soutenancesRepository)
    {
        this.SalleRep=SalleRep;


        this.soutenancesRepository = soutenancesRepository;
    }

    public Salle CreateSalle(SalleDTO salle)
    {

        try {
            Optional<Salle> existingSalle = SalleRep.findByName(salle.getName());
            if (existingSalle.isPresent()) {
                throw new IllegalArgumentException("Salle with name " + salle.getName() + " already exists.");
            }

            // Create Salle
            Salle salle1 = new Salle();
            salle1.setName(salle.getName());
            salle1.setType(salle.getType());
            return SalleRep.save(salle1);
        } catch (Exception e) {

            throw new RuntimeException("Error creating Salle: " + e.getMessage(), e);
        }
    }


    public void deleteSalleById(int id) {
        try {
            SalleRep.deleteById(id);
         } catch (Exception e) {
            throw new RuntimeException("Error deleting SALLE: " + e.getMessage(), e);
        }
    }

    public List<Salle> GetAllSalles()
    {
        try
        {
            return SalleRep.findAll();		 }
        catch(Exception e )
        {
            throw new RuntimeException("Error :"+e.getMessage(),e);
        }
    }


    public Salle EditSalle(int id,SalleDTO salle){

        try {
            Optional<Salle> existingSalle = SalleRep.findById(id);
            if (existingSalle.isEmpty()) {
                throw new IllegalArgumentException("There is no salles with this id  " +id);
            }

            // Create and save the new salle
            Salle salle2 = existingSalle.get();
            salle2.setName(salle.getName());
            salle2.setType(salle.getType());
            return SalleRep.save(salle2);
        } catch (Exception e) {

            throw new RuntimeException("Error creating category: " + e.getMessage(), e);
        }







    }

    // Method to find available salles
  public List<Salle> findAvailableSalles(LocalDate date, LocalTime time) {
        // Find all soutenances that are scheduled at the given date and time
        List<Soutenance> bookedSoutenances = soutenancesRepository.findSoutenanceByDateSoutenanceAndAndHeureSoutenance(date, time);

        // Extract salle IDs from booked soutenances
        List<Integer> bookedSalleIds = bookedSoutenances.stream()
                .map(soutenance -> soutenance.getSalle().getSalleId())
                .distinct()
                .collect(Collectors.toList());

        // Return all salles that are not booked at the given time
        if (bookedSalleIds.isEmpty()) {
            return SalleRep.findAll();  // If no salles are booked, return all available salles
        } else {
            return SalleRep.findBysalleIdNotIn(bookedSalleIds);  // Fetch salles not in the booked IDs list
        }
    }









}
