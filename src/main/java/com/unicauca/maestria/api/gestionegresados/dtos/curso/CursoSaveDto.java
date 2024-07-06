package com.unicauca.maestria.api.gestionegresados.dtos.curso;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoSaveDto {

    @NotNull
    private Long idEstudiante;

    @NotNull
    private Long idCurso;

    @NotNull
    private String orientadoA;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaFin;

}
