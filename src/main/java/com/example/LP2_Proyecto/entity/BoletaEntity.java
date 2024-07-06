package com.example.LP2_Proyecto.entity;

<<<<<<< HEAD
import java.time.LocalDate;

=======
>>>>>>> pedro
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
@Table(name = "tb_boleta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoletaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer boletaId;
	
	private Integer cantidad;
	
<<<<<<< HEAD
	
	@ManyToOne
	@JoinColumn(name = "pedido_id", nullable = false)
	private PedidoEntity pedidoEntity;
	
=======
>>>>>>> pedro
	@ManyToOne
	@JoinColumn(name = "torta_id", nullable = false)
	private TortaEntity tortaEntity;
	
<<<<<<< HEAD
	
	@ManyToOne
	@JoinColumn(name = "bocadito_id", nullable = false)
	private BocaditoEntity bocaditoEntity;
}
=======
	@ManyToOne
	@JoinColumn(name = "pedido_id", nullable = false)
	private PedidoEntity pedidoEntity;
}
>>>>>>> pedro
