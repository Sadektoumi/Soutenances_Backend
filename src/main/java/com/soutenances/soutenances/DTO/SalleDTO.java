package com.soutenances.soutenances.DTO;

import com.soutenances.soutenances.Models.ModelsEnum.SalleType;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class SalleDTO {





    private String name ;


    private SalleType type;





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SalleType getType() {
        return type;
    }

    public void setType(SalleType type) {
        this.type = type;
    }


}
