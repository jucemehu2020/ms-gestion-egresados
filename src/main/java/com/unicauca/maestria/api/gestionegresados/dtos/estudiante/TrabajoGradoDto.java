package com.unicauca.maestria.api.gestionegresados.dtos.estudiante;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class TrabajoGradoDto {
    private Long id;
	
	@NotBlank
	private String estado;
	
	@NotNull
	private LocalDate fechaCreacion;
	
	@NotBlank	
	private String numeroEstado;
	
}
