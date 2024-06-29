package com.example.LP2_Proyecto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_torta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TortaEntity {
	
	@Id
    private String idtorta;
    
    private String nombre;
    
    private String descripcion;
    
    private Integer stock;
    
    private double precio;

}
