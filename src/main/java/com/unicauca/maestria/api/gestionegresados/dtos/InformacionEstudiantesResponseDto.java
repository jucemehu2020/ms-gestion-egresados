package com.unicauca.maestria.api.gestionegresados.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.unicauca.maestria.api.gestionegresados.common.enums.estudiante.TipoIdentificacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacionEstudiantesResponseDto {

    private Long id;
    private TipoIdentificacion tipoIdentificacion;
    private Long identificacion;
    private String nombre;
    private String apellido;
}
