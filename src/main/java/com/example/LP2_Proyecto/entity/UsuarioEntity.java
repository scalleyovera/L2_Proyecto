package com.example.LP2_Proyecto.entity;

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
import lombok.ToString;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@AllArgsConstructor

@ToString
public class UsuarioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;

	private String nombre;

	private String apellido;

	private Integer edad;

	private String usuario;

	private String clave;

	private String urlImagen;

	@ManyToOne
	@JoinColumn(name = "tipo", nullable = false)
	private TipoEntity tipo;

	// Constructor que inicializa el tipo de empleado por defecto
	public UsuarioEntity() {

		this.tipo = new TipoEntity(2, "Tipo de usuario por defecto");
	}
}
