package com.unicauca.maestria.api.gestionegresados.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacionGeneralResponseDto {

    private Long id;
    private String codigo;
    private Long identificacion;
    private String nombre;
    private String apellido;
    private Integer cohorte;
    private String periodoIngreso;
    private LocalDate fechaGrado;
    private String telefono;
    private String correo;

}
