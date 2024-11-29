package com.soutenances.soutenances.DTO;

import com.soutenances.soutenances.Models.Projet;
import com.soutenances.soutenances.Models.Salle;
import com.soutenances.soutenances.Models.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SoutenanceDTO {

    private LocalDate date ;

    private LocalTime time ;

    private int salle ;

    private int rapporteur ;

    private int jury ;

    private int projet ;



}
