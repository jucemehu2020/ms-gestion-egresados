package com.unicauca.maestria.api.gestionegresados.services.informacion_general;

import java.util.List;

import com.unicauca.maestria.api.gestionegresados.dtos.InformacionEstudiantesResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.InformacionGeneralResponseDto;

public interface InformacionGeneralService {

    List<InformacionEstudiantesResponseDto>  obtenerEstudiantes();
    InformacionGeneralResponseDto buscarEstudiante(Long id);
}
