package com.example.LP2_Proyecto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_usuarios")
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

	@Column(name = "urlImagen")
	private String urlImagen;

	@ManyToOne
	@JoinColumn(name = "tipo", nullable = false)
	private TipoEntity tipo;

	// Constructor que inicializa el tipo de empleado por defecto
	public UsuarioEntity() {

		this.tipo = new TipoEntity(2, "Tipo de usuario por defecto");
	}
}
