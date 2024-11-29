package com.soutenances.soutenances.Controller;


import com.soutenances.soutenances.DTO.SalleDTO;
import com.soutenances.soutenances.DTO.SpecialiteDTO;
import com.soutenances.soutenances.Models.Salle;
import com.soutenances.soutenances.Models.Specialite;
import com.soutenances.soutenances.Service.SpecialiteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/SpecialiteController")
public class SpecialiteController {
    private final SpecialiteService Specialite ;

    public SpecialiteController(SpecialiteService specialites) {
        this.Specialite = specialites;
    }


    @PostMapping("/CreateSpecialite")
    public ResponseEntity<String> CreateSpecialite(@RequestBody SpecialiteDTO specialiteDTO)
    {

        System.out.println("Received Specialite DTO: Name - " + specialiteDTO.getName() + ", Type  - " + specialiteDTO.getSpecialiteType());
        try
        {
            Specialite.CreateSpecialite(specialiteDTO);
            return ResponseEntity.ok("Specialite added successfully");

        }catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");

        }


    }

    @DeleteMapping("/DeleteSpecialite/{id}")
    public ResponseEntity<String> DeleteSpecialite(@PathVariable int id )
    {
        try
        {
            Specialite.deleteSpecialiteById(id);
            return ResponseEntity.ok("Specialite deleted Successfully");

        } catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/GetAllSpecialite")
    public ResponseEntity<List<Specialite>> GetAllSpecialtes(){
        try
        {

            List<Specialite> S = Specialite.GetAllSpecialite();
            return ResponseEntity.ok(S);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


    }

    @PutMapping("UpdateSpecialite/{id}")
    public ResponseEntity<Specialite> UpdateSpecialite(@PathVariable int id,@RequestBody SpecialiteDTO specialitedto)
    {
        try
        {
            Specialite editedSpecialite = Specialite.EditSpecialite(id, specialitedto);
            return ResponseEntity.ok(editedSpecialite);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
