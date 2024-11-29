package com.soutenances.soutenances.Service;

import com.soutenances.soutenances.DTO.AuthenticationResponse;
import com.soutenances.soutenances.DTO.SoutenanceDTO;
import com.soutenances.soutenances.DTO.UserDTO;
import com.soutenances.soutenances.Models.*;
import com.soutenances.soutenances.Repositories.GroupeRepository;
import com.soutenances.soutenances.Repositories.ProjetRepository;
import com.soutenances.soutenances.Repositories.SoutenancesRepository;
import com.soutenances.soutenances.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class UserService {


    private final UserRepository userrepo ;

    private final ProjetRepository projetRepository;

    private final SoutenancesRepository soutenancesRepository;



    private PasswordEncoder passwordEncoder ;

    private final GroupeRepository GroupeRepo ;


    public UserService(UserRepository userrepo, ProjetRepository projetRepository, SoutenancesRepository soutenancesRepository, GroupeRepository groupeRepo, PasswordEncoder passwordEncoder) {
        this.userrepo = userrepo;
        this.projetRepository = projetRepository;
        this.soutenancesRepository = soutenancesRepository;
        this.GroupeRepo = groupeRepo;
        this.passwordEncoder= passwordEncoder;


    }

    public User CreateUser(UserDTO userDTO) {
        try {
            // Check if the user already exists
            Optional<User> existingUser = userrepo.findByUsername(userDTO.getUsername());
            if (existingUser.isPresent()) {
                throw new IllegalArgumentException("This user " + userDTO.getUsername() + " already exists.");
            }

            // Find the group by ID, if it exists
            Optional<Groupe> optionalGroupe = GroupeRepo.findById(userDTO.getGroupe_id());
            Groupe groupe = optionalGroupe.orElse(null);

            // Create a new user and set the fields
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setName(userDTO.getName());
            user.setLastname(userDTO.getLastname());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setRole(userDTO.getRole()); // Hardcoded role as "Teacher"
            user.setGroupe(groupe);  // Set the group, which could be null

            // Save the user and return the result
            return userrepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }
    }
/*
public User CreateUser(UserDTO userDTO)
{

    try {
        Optional<User> existingUser = userrepo.findByUsername(userDTO.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("this user " + userDTO.getUsername() + " is aleardy exist ");

        }
        Groupe groupe = GroupeRepo.findById(userDTO.getGroupe_id())
              .orElseThrow(() -> new RuntimeException("groupe not found"));



        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setGroupe(groupe);

        return userrepo.save(user);
    }catch (Exception e) {
        throw  new RuntimeException("Error Creaing UsER "+e.getMessage(),e);
    }

}

 */
public List<User> GetAllUsers()
{
    try
    {
        return userrepo.findAll();

    }catch (Exception e)
    {
        throw new RuntimeException("Error :" +e.getMessage(),e);
    }
}

public void DeleteUser(int id ){
        try{
            userrepo.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Error deleeteing groupe "+e.getMessage(),e);
        }
}


public User EditUser(int id , UserDTO userDTO){
    try {
        Optional<User> existingUser = userrepo.findById(id);
        if (existingUser.isEmpty()) {
            throw new IllegalArgumentException("There is no user with this id  " +id);
        }
        Groupe groupe = GroupeRepo.findById(userDTO.getGroupe_id())
                .orElseThrow(() -> new RuntimeException("groupe not found"));
        // Create and save the new User
        User user = existingUser.get();

        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setGroupe(groupe);

        return userrepo.save(user);


    } catch (Exception e) {

        throw new RuntimeException("Error creating category: " + e.getMessage(), e);
    }

}

    public List<User> findAvailableTeacher(LocalDate date, LocalTime time) {
        // Find all soutenances that are scheduled at the given date and time
        List<Soutenance> bookedSoutenances = soutenancesRepository.findSoutenanceByDateSoutenanceAndAndHeureSoutenance(date, time);
        
        List<Integer> bookedTeacherIds = bookedSoutenances.stream()
                .map(soutenance -> soutenance.getRapporteur().getUserID())

                .distinct()
                .collect(Collectors.toList());

        if (bookedTeacherIds.isEmpty()) {
            return userrepo.findAll();  // If no salles are booked, return all available salles
        } else {
            return userrepo.findByuserIDNotIn(bookedTeacherIds);  // Fetch salles not in the booked IDs list
        }
    }

    public List<User> findAvailableStudents() {
        // Fetch all projects to extract student IDs
        List<Projet> projects = projetRepository.findAll();

        // Extract student IDs from projects where they are assigned as etudiant1 or etudiant2
       /* List<Integer> assignedStudentIds = projects.stream()
                .flatMap(p -> Stream.of(p.getEtudiant_id().getUserID(), p.getEtudiant2_id().getUserID()))
                .distinct()
                .collect(Collectors.toList());

        */

        List<Integer> assignedStudentIds = projects.stream()
                .flatMap(p -> Stream.of(
                        p.getEtudiant_id() != null ? p.getEtudiant_id().getUserID() : null,
                        p.getEtudiant2_id() != null ? p.getEtudiant2_id().getUserID() : null))
                .filter(Objects::nonNull)  // Filter out null values after checking for non-null user references
                .distinct()
                .collect(Collectors.toList());

        // Find all students who are not assigned to any projects
        if (assignedStudentIds.isEmpty()) {
            return userrepo.findAll();  // If no students are assigned, return all students
        } else {
            return userrepo.findByUserIDNotIn(assignedStudentIds);  // Fetch students not in the assigned IDs list
        }
    }
    









}