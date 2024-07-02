package com.example.LP2_Proyecto.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "tb_pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer boletaId;
	
	private LocalDate fechacom;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private UsuarioEntity usuarioEntity;
}
