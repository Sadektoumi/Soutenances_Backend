package com.soutenances.soutenances.Service;

import com.soutenances.soutenances.DTO.ProjetDTO;
import com.soutenances.soutenances.DTO.UserDTO;
import com.soutenances.soutenances.Models.Groupe;
import com.soutenances.soutenances.Models.Projet;
import com.soutenances.soutenances.Models.Soutenance;
import com.soutenances.soutenances.Models.User;
import com.soutenances.soutenances.Repositories.ProjetRepository;
import com.soutenances.soutenances.Repositories.SoutenancesRepository;
import com.soutenances.soutenances.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProjetService {

    private final ProjetRepository projectRepo;

    private  final SoutenancesRepository soutenancesRepository;

    private final UserRepository userRepo;


    public ProjetService(ProjetRepository projectRepo, SoutenancesRepository soutenancesRepository, UserRepository userRepo) {
        this.projectRepo = projectRepo;
        this.soutenancesRepository = soutenancesRepository;
        this.userRepo = userRepo;
    }

    public Projet CreateProjet (ProjetDTO projetDTO) {

        try {

            Optional<Projet> existingProjet = projectRepo.findBySujet(projetDTO.getSujet());

            if(existingProjet.isPresent()){
                throw new IllegalArgumentException("projet with this subjet "+projetDTO.getSujet()+"is aleardy exist");
            }

            User teacher = userRepo.findById(projetDTO.getEncadrant_id())
                    .orElseThrow(() -> new RuntimeException("this teacher does not exit"));

            //User Student = userRepo.findById(projetDTO.getEtudiant_id())
                   // .orElseThrow(() -> new RuntimeException("this Student1 does not exist "));

            Optional<User> OptionalStudent = userRepo.findById(projetDTO.getEtudiant_id());
            User Student = OptionalStudent.orElse(null);

           // User Student2 = userRepo.findById(projetDTO.getEtudiant2_id())
                    //.orElseThrow(() -> new RuntimeException("this Student2 does not exist "));

            Optional<User> OptionalStudent2 = userRepo.findById(projetDTO.getEtudiant2_id());
            User Student2 = OptionalStudent2.orElse(null);


            Projet projet = new Projet();
            projet.setSujet(projetDTO.getSujet());
            projet.setEncadrant_id(teacher);
            projet.setEtudiant_id(Student);
            projet.setEtudiant2_id(Student2);
            return projectRepo.save(projet);



        }catch(Exception e){
             throw new RuntimeException("Error while Crreating "+e.getMessage(),e);
        }





    }


    public List<Projet> GetAllprojet()
    {

        try{
            return projectRepo.findAll();

        }catch(Exception e){

            throw new RuntimeException("Error :"+e.getMessage(),e);
        }

    }

    public void DeleteProjet(int id)
    {
        try{

            projectRepo.deleteById(id);

        }catch(Exception e){
            throw new RuntimeException("Error deleting groupe"+e.getMessage(),e);
        }

    }

    public Projet Editprojet(int id , ProjetDTO projetDTO){
        try {
            Optional<Projet> existingProjet = projectRepo.findById(id);
            if (existingProjet.isEmpty()) {
                throw new IllegalArgumentException("There is no user with this id  " +id);
            }


            User teacher = userRepo.findById(projetDTO.getEncadrant_id())
                    .orElseThrow(() -> new RuntimeException("this teacher does not exit"));

            //User Student = userRepo.findById(projetDTO.getEtudiant_id())
            // .orElseThrow(() -> new RuntimeException("this Student1 does not exist "));

            Optional<User> OptionalStudent = userRepo.findById(projetDTO.getEtudiant_id());
            User Student = OptionalStudent.orElse(null);

            // User Student2 = userRepo.findById(projetDTO.getEtudiant2_id())
            //.orElseThrow(() -> new RuntimeException("this Student2 does not exist "));

            Optional<User> OptionalStudent2 = userRepo.findById(projetDTO.getEtudiant2_id());
            User Student2 = OptionalStudent2.orElse(null);
            // Create and save the new User
            Projet projet = existingProjet.get();

            projet.setSujet(projetDTO.getSujet());
            projet.setEncadrant_id(teacher);
            projet.setEtudiant_id(Student);
            projet.setEtudiant2_id(Student2);
            return projectRepo.save(projet);



        } catch (Exception e) {

            throw new RuntimeException("Error creating category: " + e.getMessage(), e);
        }

    }

    public List<Projet> findAvailableProjects() {

        List<Soutenance> soutenances = soutenancesRepository.findAll();

        // Extract student IDs from projects where they are assigned as etudiant1 or etudiant2
        List<Integer> assignedProjectIds = soutenances.stream()
                .map(s -> s.getProjet().getProjetid())
                .distinct()
                .collect(Collectors.toList());



        // Find all students who are not assigned to any projects
        if (assignedProjectIds.isEmpty()) {
            return projectRepo.findAll();  // If no students are assigned, return all students
        } else {
            return projectRepo.findByProjetidNotIn(assignedProjectIds);  // Fetch students not in the assigned IDs list
        }
    }










}
