package com.example.LP2_Proyecto.entity;

import java.time.LocalDate;
<<<<<<< HEAD

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
=======
import java.util.List;

import jakarta.persistence.*;
>>>>>>> pedro
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD

=======
import lombok.ToString;
>>>>>>> pedro

@Entity
@Table(name = "tb_pedido")
@Getter
@Setter
<<<<<<< HEAD
=======
@ToString
>>>>>>> pedro
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
	private Integer boletaId;
	
	private LocalDate fechacom;
=======
	private Long pedidoId;
	
	private LocalDate fechaCompra;
>>>>>>> pedro
	
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private UsuarioEntity usuarioEntity;
<<<<<<< HEAD
=======
	
	@OneToMany(mappedBy = "pedidoEntity", cascade = CascadeType.ALL)
	private List<BoletaEntity>boleta;
>>>>>>> pedro
}
