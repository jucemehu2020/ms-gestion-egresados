package com.unicauca.maestria.api.gestionegresados.services.informacion_general;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.unicauca.maestria.api.gestionegresados.common.client.ArchivoClient;
import com.unicauca.maestria.api.gestionegresados.dtos.EstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.InformacionEstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.InformacionGeneralResponseDto;

@Service
@RequiredArgsConstructor
public class InformacionGeneralServiceImpl implements InformacionGeneralService {

    private final ArchivoClient archivoClient;

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteResponseDto> obtenerEstudiantes() {
        List<EstudianteResponseDto> informacionEstudiantes = archivoClient.obtenerEstudiantes();
        return informacionEstudiantes;
    }

    @Override
    @Transactional(readOnly = true)
    public InformacionGeneralResponseDto buscarEstudiante(Long idEstudiante) {
        InformacionGeneralResponseDto informacionEstudiante = archivoClient.obtenerPorIdEstudiante(idEstudiante);
        return informacionEstudiante;
    }
}
