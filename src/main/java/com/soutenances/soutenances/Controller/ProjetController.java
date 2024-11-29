package com.soutenances.soutenances.Controller;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soutenances.soutenances.DTO.ProjetDTO;
import com.soutenances.soutenances.DTO.UserDTO;
import com.soutenances.soutenances.Models.Groupe;
import com.soutenances.soutenances.Models.Projet;
import com.soutenances.soutenances.Models.User;
import com.soutenances.soutenances.Service.ProjetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/projetController")
public class ProjetController {



    private final ProjetService projetService;

    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }


    @PostMapping("/CreateProjet")

    public ResponseEntity<String> createProjet(@RequestBody ProjetDTO projetDTO){
        try {
           Projet  projet = projetService.CreateProjet(projetDTO);
           return ResponseEntity.ok("project added successfully with name: " + projet.getSujet());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating groupe: " + e.getMessage());
        }
}


    @DeleteMapping("/DeleteProjet/{id}")
    public ResponseEntity<String>DeleteGroupe(@PathVariable int id ){
        try{
            projetService.DeleteProjet(id);
            return ResponseEntity.ok("Projet deleted successfully");


        }catch (Exception e){
            throw new RuntimeException("Error="+e.getMessage(),e);
        }

    }

    @GetMapping("/GetAllProject")
    @JsonIgnore
    public ResponseEntity<List<Projet>> GetAllgroupes(){
        try{
            List<Projet> s = projetService.GetAllprojet();
            return ResponseEntity.ok(s);
        }catch (Exception e){
            throw new RuntimeException("Error = "+e.getMessage(),e);

        }
    }

    @PutMapping("UpdateProjet/{id}")
    public ResponseEntity<Projet> UpdateProjet(@PathVariable int id, @RequestBody ProjetDTO projetDTO){

        try
        {
            Projet editedProjet = projetService.Editprojet(id, projetDTO);
            return ResponseEntity.ok(editedProjet);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


    }
    @GetMapping("/getAllProjetAreNotInSoutenance")

    public ResponseEntity<List<Projet>>getAllProjetAreNotInSoutenance(){
        try
        {
            List<Projet> projects = projetService.findAvailableProjects();
            return ResponseEntity.ok(projects);
        }catch(Exception e){
            throw new RuntimeException("Error = "+e.getMessage(),e);
        }

    }


}
