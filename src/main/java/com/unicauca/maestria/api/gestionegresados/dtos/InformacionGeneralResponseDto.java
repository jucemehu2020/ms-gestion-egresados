package com.unicauca.maestria.api.gestionegresados.dtos;

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
public class InformacionGeneralResponseDto {

    @NotBlank
    private String codigo;

    @NotNull
    private Long identificacion;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotNull
    private LocalDate fechaGrado;

    @NotBlank
    private String telefono;

    @NotBlank
    private String correo;

}
