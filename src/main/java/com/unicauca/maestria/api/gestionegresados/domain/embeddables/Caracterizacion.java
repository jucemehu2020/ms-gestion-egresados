package com.unicauca.maestria.api.gestionegresados.domain.embeddables;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.unicauca.maestria.api.gestionegresados.common.enums.estudiante.Discapacidad;
import com.unicauca.maestria.api.gestionegresados.common.enums.estudiante.Etnia;
import com.unicauca.maestria.api.gestionegresados.common.enums.estudiante.TipoPoblacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor @AllArgsConstructor @Builder //pediente de cambios
@Embeddable
public class Caracterizacion {
	
	@Enumerated(EnumType.STRING)
	private TipoPoblacion tipoPoblacion;
	 	
	@Enumerated(EnumType.STRING)
	private Etnia etnia;
	
	@Enumerated(EnumType.STRING)
	private Discapacidad discapacidad;
	 
}
