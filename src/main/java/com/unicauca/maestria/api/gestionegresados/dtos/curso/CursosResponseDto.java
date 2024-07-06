package com.unicauca.maestria.api.gestionegresados.dtos.curso;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursosResponseDto {

    private Long id;
    private String nombre;
    private String orientadoA;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
