package com.example.LP2_Proyecto.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_cab_boleta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CabBoletaEntity {
	
	@Id
    private String num_bol;
    
    private LocalDate fch_bol;
    
    @ManyToOne
    @JoinColumn(name = "cod_cliente", nullable = false)
    private UsuarioEntity usuarioEntity;

}
