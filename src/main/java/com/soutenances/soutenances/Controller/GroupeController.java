package com.soutenances.soutenances.Controller;

import com.soutenances.soutenances.DTO.GroupeDTO;
import com.soutenances.soutenances.Models.Groupe;
import com.soutenances.soutenances.Service.GroupeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groupeController")
public class GroupeController {


    private final GroupeService groupeService ;


    public GroupeController(GroupeService groupeService) {
        this.groupeService = groupeService;
    }

    @PostMapping("/CreateGroupe")
    public ResponseEntity<String> createGroupe(@RequestBody GroupeDTO groupeDTO){

        try {
            Groupe groupe = groupeService.CreateGroupe(groupeDTO);
            return ResponseEntity.ok("groupe added successfully with name: " + groupe.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating groupe: " + e.getMessage());
        }
    }

    @DeleteMapping("/DeleteGroupe/{id}")
    public ResponseEntity<String>DeleteGroupe(@PathVariable int id ){
        try{
            groupeService.DeleteGroupe(id);
            return ResponseEntity.ok("groupe deleted successfully");


        }catch (Exception e){
            throw new RuntimeException("Error="+e.getMessage(),e);
        }

    }
    @GetMapping("/GetAllGroupes")
    public ResponseEntity<List<Groupe>> GetAllgroupes(){
        try{
            List<Groupe> s = groupeService.GetAllGroupes();
            return ResponseEntity.ok(s);
        }catch (Exception e){
            throw new RuntimeException("Error = "+e.getMessage(),e);

        }
    }

    @PutMapping("/UpdateGroupe/{id}")
    public ResponseEntity<Groupe> UpdateGroupe(@PathVariable int id ,@RequestBody GroupeDTO groupeDTO){
        try
        {
         Groupe editedGroupe= groupeService.EditGroupe(id,groupeDTO);
         return ResponseEntity.ok(editedGroupe);
        }catch (Exception e){
            throw new RuntimeException("Error"+e.getMessage(),e);

        }
    }
}
