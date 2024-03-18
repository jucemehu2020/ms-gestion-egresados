package com.unicauca.maestria.api.gestionegresados.services.informacion_general;

import java.util.List;

import com.unicauca.maestria.api.gestionegresados.dtos.EstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.InformacionEstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.InformacionGeneralResponseDto;

public interface InformacionGeneralService {

    List<EstudianteResponseDto>  obtenerEstudiantes();
    InformacionGeneralResponseDto buscarEstudiante(Long id);
}
