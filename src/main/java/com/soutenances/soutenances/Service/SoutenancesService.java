package com.soutenances.soutenances.Service;

import com.soutenances.soutenances.DTO.SoutenanceDTO;
import com.soutenances.soutenances.Models.Projet;
import com.soutenances.soutenances.Models.Salle;
import com.soutenances.soutenances.Models.Soutenance;
import com.soutenances.soutenances.Models.User;
import com.soutenances.soutenances.Repositories.ProjetRepository;
import com.soutenances.soutenances.Repositories.SalleRepository;
import com.soutenances.soutenances.Repositories.SoutenancesRepository;
import com.soutenances.soutenances.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class SoutenancesService {

    private final SoutenancesRepository soutenancesRepository;
    private  final UserRepository userRepo;

    private final SalleRepository salleRepo;

    private final ProjetRepository projetRepo;


    public SoutenancesService(SoutenancesRepository soutenancesRepository, UserRepository userRepo, SalleRepository salleRepo, ProjetRepository projetRepo) {
        this.soutenancesRepository = soutenancesRepository;
        this.userRepo = userRepo;
        this.salleRepo = salleRepo;
        this.projetRepo = projetRepo;
    }


    public Soutenance CreateSoutenance(SoutenanceDTO soutenanceDTO){

        try{

            User Rapporteur = userRepo.findById(soutenanceDTO.getRapporteur())
                    .orElseThrow(() -> new RuntimeException("this rapporteur does not exit"));

            User Jury = userRepo.findById(soutenanceDTO.getJury())
                    .orElseThrow(() -> new RuntimeException("this Jury does not exist "));

            Projet projet = projetRepo.findById(soutenanceDTO.getProjet())
                    .orElseThrow(() -> new RuntimeException("this projet does not exist "));

            Salle salle = salleRepo.findById(soutenanceDTO.getSalle())
                    .orElseThrow(() -> new RuntimeException("this salle does not exist "));


            Soutenance soutenance = new Soutenance();
            soutenance.setDateSoutenance(soutenanceDTO.getDate());
            soutenance.setHeureSoutenance(soutenanceDTO.getTime());
            soutenance.setSalle(salle);
            soutenance.setProjet(projet);
            soutenance.setJury(Jury);
            soutenance.setRapporteur(Rapporteur);

            return soutenancesRepository.save(soutenance);


        }catch(Exception e){

            throw new RuntimeException("Erro while creating"+e.getMessage(),e);
        }
    }

    public void deleteSoutenanceById(int id){
        try{
            soutenancesRepository.deleteById(id);
        }catch(Exception e){
            throw new RuntimeException("Error while deleteing"+e.getMessage(),e);
        }
    }

    public List<Soutenance>GetAllSoutenances(){
        try{
            return soutenancesRepository.findAll();
        }catch(Exception e){
            throw new RuntimeException("Error"+e.getMessage(),e);
        }
    }

    public Soutenance GetSoutenance(int id ){
        try{
            Optional<Soutenance> existingSoutenance = soutenancesRepository.findById(id);
            Soutenance soutenance = existingSoutenance.get();
            return (soutenance);

        }catch(Exception e){
            throw new RuntimeException("Error"+e.getMessage(),e);
        }
    }


    public Soutenance EditSoutenance(int id ,SoutenanceDTO soutenanceDTO){

        try{

            Optional<Soutenance> existingSoutenance = soutenancesRepository.findById(id);
            if (existingSoutenance.isEmpty()) {
                throw new IllegalArgumentException("There is no salles with this id  " +id);
            }

            User Rapporteur = userRepo.findById(soutenanceDTO.getRapporteur())
                    .orElseThrow(() -> new RuntimeException("this rapporteur does not exit"));

            User Jury = userRepo.findById(soutenanceDTO.getJury())
                    .orElseThrow(() -> new RuntimeException("this Jury does not exist "));

            Projet projet = projetRepo.findById(soutenanceDTO.getProjet())
                    .orElseThrow(() -> new RuntimeException("this projet does not exist "));

            Salle salle = salleRepo.findById(soutenanceDTO.getSalle())
                    .orElseThrow(() -> new RuntimeException("this salle does not exist "));


            Soutenance soutenance = existingSoutenance.get();
            soutenance.setDateSoutenance(soutenanceDTO.getDate());
            soutenance.setHeureSoutenance(soutenanceDTO.getTime());
            soutenance.setSalle(salle);
            soutenance.setProjet(projet);
            soutenance.setJury(Jury);
            soutenance.setRapporteur(Rapporteur);

            return soutenancesRepository.save(soutenance);


        }catch(Exception e){

            throw new RuntimeException("Erro while creating"+e.getMessage(),e);
        }
    }


}
