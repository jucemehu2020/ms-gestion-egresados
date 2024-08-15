package com.unicauca.maestria.api.gestionegresados.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListadoAsignaturasDto {

    private Long idAsignatura;
    private String nombreAsignatura;

}
