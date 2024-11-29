package com.soutenances.soutenances.Service;


import com.soutenances.soutenances.DTO.GroupeDTO;
import com.soutenances.soutenances.Models.Groupe;
import com.soutenances.soutenances.Models.Specialite;
import com.soutenances.soutenances.Repositories.GroupeRepository;
import com.soutenances.soutenances.Repositories.SpecialiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupeService {

    private final GroupeRepository GroupeRepo ;

    private final SpecialiteRepository SpecialiteRepo;


    public GroupeService(GroupeRepository groupeRepo, SpecialiteRepository specialiteRepo) {
        GroupeRepo = groupeRepo;
        SpecialiteRepo = specialiteRepo;

    }

    public Groupe CreateGroupe (GroupeDTO groupeDTO) {

        try {

            Optional<Groupe> existingGroupe = GroupeRepo.findByName(groupeDTO.getName());
            if (existingGroupe.isPresent()) {
                throw new IllegalArgumentException("groupe with name" + groupeDTO.getName() + "already exists");

            }


            Specialite specialite = SpecialiteRepo.findById(groupeDTO.getSpecialite_id())
                    .orElseThrow(() -> new RuntimeException("Specialite Not found"));


            Groupe groupe = new Groupe();
            groupe.setName(groupeDTO.getName());
            groupe.setGrp(specialite);
            return GroupeRepo.save(groupe);


        } catch (Exception e) {

            throw new RuntimeException("Error creating groupe:" + e.getMessage(), e);



        }

    }

        public List<Groupe> GetAllGroupes()
    {

            try{
                return GroupeRepo.findAll();

            }catch(Exception e){

                throw new RuntimeException("Error :"+e.getMessage(),e);
            }

    }

    public void DeleteGroupe(int id)
    {
        try{

            GroupeRepo.deleteById(id);

        }catch(Exception e){
            throw new RuntimeException("Error deleting groupe"+e.getMessage(),e);
        }

    }


    public Groupe EditGroupe(int id ,GroupeDTO groupeDTO){
        try{
            Optional<Groupe> existingGroupe= GroupeRepo.findById(id);
            if(existingGroupe.isEmpty()){
                throw new RuntimeException ("there is no Groupe with this id"+id);
            }

            Groupe groupe = existingGroupe.get();
            groupe.setName(groupeDTO.getName());
            Specialite specialite = SpecialiteRepo.findById(groupeDTO.getSpecialite_id())
                    .orElseThrow(() -> new RuntimeException("Specialite Not found"));
            groupe.setGrp(specialite);
            return GroupeRepo.save(groupe);


        }catch (Exception e){
             throw new RuntimeException("Erro = "+e.getMessage(),e);
        }
    }
















}
