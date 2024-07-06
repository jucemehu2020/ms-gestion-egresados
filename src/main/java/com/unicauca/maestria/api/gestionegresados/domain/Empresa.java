package com.unicauca.maestria.api.gestionegresados.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "empresas")
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	private String ubicacion;

	private String cargo;

	private String jefeDirecto;

	private String telefono;

	private String correo;

	private String estado;

	@Column(name = "id_estudiante")
	private Long idEstudiante;

}
