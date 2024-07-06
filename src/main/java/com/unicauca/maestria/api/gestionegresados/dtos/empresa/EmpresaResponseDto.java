package com.unicauca.maestria.api.gestionegresados.dtos.empresa;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaResponseDto {
    
    private Long id;
    private String nombre;
    private String ubicacion;
    private String cargo;
    private String jefeDirecto;
    private String telefono;
    private String correo;
    private String estado;
}
