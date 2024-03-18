package com.unicauca.maestria.api.gestionegresados.dtos.estudiante;


import com.unicauca.maestria.api.gestionegresados.common.enums.estudiante.DedicacionBeca;
import com.unicauca.maestria.api.gestionegresados.common.enums.estudiante.TipoBeca;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class BecaDto {
	private Long id;
	private String titulo;
	private String entidadAsociada;
	private TipoBeca tipo;
	private Boolean esOfrecidaPorUnicauca;
	private DedicacionBeca dedicacion;
}
