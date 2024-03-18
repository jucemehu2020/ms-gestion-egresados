package com.unicauca.maestria.api.gestionegresados.dtos;

import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaSaveDto {
    @NotNull
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String ubicacion;

    @NotBlank
    private String cargo;

    @NotBlank
    private String jefeDirecto;

    @NotBlank
    private String telefono;

    @NotBlank
    private String correo;

    @NotBlank
    private String estado;
}
