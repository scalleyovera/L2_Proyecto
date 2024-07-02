package com.example.LP2_Proyecto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_tipo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TipoEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idtipo;
    
    private String descripcion;
}
