package com.unicauca.maestria.api.gestionegresados.dtos;


import java.time.LocalDate;
import java.util.List;

import com.unicauca.maestria.api.gestionegresados.domain.embeddables.Caracterizacion;
import com.unicauca.maestria.api.gestionegresados.domain.embeddables.InformacionMaestriaActual;
import com.unicauca.maestria.api.gestionegresados.dtos.common.PersonaDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class EstudianteResponseDto {
	private Long id;
	private String director;
	private String codirector;
	private PersonaDto persona;
	private String codigo;
	private String periodoIngreso;
	private Integer cohorte;
	private String correoUniversidad;
	private LocalDate fechaGrado;
	private String tituloPregrado;
	private Caracterizacion caracterizacion;
	private InformacionMaestriaActual informacionMaestria;
}
