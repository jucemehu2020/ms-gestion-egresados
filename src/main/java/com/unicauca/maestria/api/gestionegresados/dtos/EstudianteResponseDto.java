package com.unicauca.maestria.api.gestionegresados.dtos;


import java.time.LocalDate;
import java.util.List;

import com.unicauca.maestria.api.gestionegresados.domain.embeddables.Caracterizacion;
import com.unicauca.maestria.api.gestionegresados.domain.embeddables.InformacionMaestriaActual;
import com.unicauca.maestria.api.gestionegresados.dtos.common.PersonaDto;
import com.unicauca.maestria.api.gestionegresados.dtos.estudiante.BecaDto;
import com.unicauca.maestria.api.gestionegresados.dtos.estudiante.ProrrogaDto;
import com.unicauca.maestria.api.gestionegresados.dtos.estudiante.ReingresoDto;
import com.unicauca.maestria.api.gestionegresados.dtos.estudiante.TrabajoGradoDto;

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
	private BecaDto beca;
	private String codigo;
	private String ciudadResidencia;
	private String correoUniversidad;
	private LocalDate fechaGrado;
	private String tituloPregrado;
	private Caracterizacion caracterizacion;
	private InformacionMaestriaActual informacionMaestria;
	private List<ProrrogaDto> prorrogas;
	private List<ReingresoDto> reingresos;
	private Boolean estadoTrabajoGrado;
	private List<TrabajoGradoDto> trabajoGrado;
}
