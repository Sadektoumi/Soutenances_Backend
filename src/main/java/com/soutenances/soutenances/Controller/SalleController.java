package com.soutenances.soutenances.Controller;


import com.soutenances.soutenances.DTO.SalleDTO;
import com.soutenances.soutenances.Models.Salle;
import com.soutenances.soutenances.Service.SalleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/SalleController")
public class SalleController {


    private final SalleService Salle ;

    public SalleController(SalleService salles)
    {
        this.Salle=salles;
    }

    @PostMapping("/CreateSalle")


    public ResponseEntity<String> CreateSalle(@RequestBody SalleDTO salledto)
    {

        System.out.println("Received Salle DTO: Name - " + salledto.getName() + ", Type  - " + salledto.getType());
        try
        {
            Salle.CreateSalle(salledto);
            return ResponseEntity.ok("Salle added successfully");

        }catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");

        }


    }

    @DeleteMapping("/DeleteSalle/{id}")
    public ResponseEntity<String> DeleteSalle(@PathVariable int id )
    {
        try
        {
            Salle.deleteSalleById(id);
            return ResponseEntity.ok("SALLE deleted Successfully");

        } catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/GetAllSalles")
    public ResponseEntity<List<Salle>> GetSalles(){
        try
        {

           List<Salle> S = Salle.GetAllSalles();
           return ResponseEntity.ok(S);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


    }

    @PutMapping("UpdateSalle/{id}")
    public ResponseEntity<Salle> UpdateSalle(@PathVariable int id,@RequestBody SalleDTO salledto)
    {
        try
        {
            Salle editedSalle = Salle.EditSalle(id, salledto);
            return ResponseEntity.ok(editedSalle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @GetMapping("/available")
    public ResponseEntity<List<Salle>> getAvailableSalles(@RequestParam LocalDate date, @RequestParam LocalTime time) {
        List<Salle> availableSalles = Salle.findAvailableSalles(date, time);
        return ResponseEntity.ok(availableSalles);
    }









}
