package com.soutenances.soutenances.Controller;


import com.soutenances.soutenances.DTO.SalleDTO;
import com.soutenances.soutenances.DTO.SoutenanceDTO;
import com.soutenances.soutenances.Models.Salle;
import com.soutenances.soutenances.Models.Soutenance;
import com.soutenances.soutenances.Models.User;
import com.soutenances.soutenances.Service.SalleService;
import com.soutenances.soutenances.Service.SoutenancesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/SoutenanceController")
public class SoutenanceController {

    private final SoutenancesService soutenance;

    public SoutenanceController(SoutenancesService soutenance) {
        this.soutenance = soutenance;
    }


    @PostMapping("/CreateSoutenance")


    public ResponseEntity<String> CreateSoutenance(@RequestBody SoutenanceDTO soutenanceDTO)
    {


        try{
            Soutenance soutenance1 = soutenance.CreateSoutenance(soutenanceDTO);
            return ResponseEntity.ok("User Created with Success"+soutenance1);


        }catch (Exception e ){

            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR WHILE CREATING");
        }


    }

    @DeleteMapping("/DeleteSoutenance/{id}")
    public ResponseEntity<String>DeleteSoutenance(@PathVariable int id ){
        try
        {
            soutenance.deleteSoutenanceById(id);
            return ResponseEntity.ok("Soutenance deleted Successfully");

        } catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
    @GetMapping("/GetAllSoutenance")
    public ResponseEntity<List<Soutenance>> GetSoutenances(){
        try
        {

            List<Soutenance> S = soutenance.GetAllSoutenances();
            return ResponseEntity.ok(S);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


    }

    @GetMapping("GetSoutenance/{id}")
    public ResponseEntity<Soutenance> GetSoutenance(@PathVariable int id){


            Soutenance S = soutenance.GetSoutenance(id);
            return ResponseEntity.ok(S);


    }

    @PutMapping("UpdateSoutenance/{id}")
    public ResponseEntity<Soutenance> UpdateSoutenance(@PathVariable int id,@RequestBody SoutenanceDTO soutenanceDTO)
    {
        try
        {
            Soutenance editedSoutenance = soutenance.EditSoutenance(id, soutenanceDTO);
            return ResponseEntity.ok(editedSoutenance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
