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
@Table(name = "tb_bocadito")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BocaditoEntity {

	 @Id
	    private String id_boca;
	    
	    private String nombre_boca;
	    
	    private String descripcion_boca;
	    
	    private Integer stock_boca;
	    
	    private Double precio_boca;
}
