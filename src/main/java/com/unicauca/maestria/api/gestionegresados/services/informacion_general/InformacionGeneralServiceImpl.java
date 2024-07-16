package com.unicauca.maestria.api.gestionegresados.services.informacion_general;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.unicauca.maestria.api.gestionegresados.common.client.ArchivoClient;
import com.unicauca.maestria.api.gestionegresados.dtos.EstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.InformacionEstudiantesResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.InformacionGeneralResponseDto;
import com.unicauca.maestria.api.gestionegresados.exceptions.InformationException;

@Service
@RequiredArgsConstructor
public class InformacionGeneralServiceImpl implements InformacionGeneralService {

    private final ArchivoClient archivoClient;

    @Override
    @Transactional(readOnly = true)
    public List<InformacionEstudiantesResponseDto> obtenerEstudiantes() {
        List<EstudianteResponseDto> informacionEstudiantes = archivoClient.obtenerEstudiantes();

        if(informacionEstudiantes.isEmpty()){
            throw new InformationException("No hay estudiantes registrados");
        }

        List<InformacionEstudiantesResponseDto> estudiantesReducidos = informacionEstudiantes.stream()
                .map(estudiante -> new InformacionEstudiantesResponseDto(
                        estudiante.getId(),
                        estudiante.getPersona().getTipoIdentificacion(),
                        estudiante.getPersona().getIdentificacion(),
                        estudiante.getPersona().getNombre(),
                        estudiante.getPersona().getApellido()))
                .collect(Collectors.toList());
        return estudiantesReducidos;
    }

    @Override
    @Transactional(readOnly = true)
    public InformacionGeneralResponseDto buscarEstudiante(Long idEstudiante) {
        EstudianteResponseDto informacionEstudiantes = archivoClient.obtenerPorIdEstudiante(idEstudiante);
        InformacionGeneralResponseDto informacionGeneralResponseDto = InformacionGeneralResponseDto.builder()
                .id(informacionEstudiantes.getId())
                .codigo(informacionEstudiantes.getCodigo())
                .identificacion(informacionEstudiantes.getPersona().getIdentificacion())
                .nombre(informacionEstudiantes.getPersona().getNombre())
                .apellido(informacionEstudiantes.getPersona().getApellido())
                .cohorte(informacionEstudiantes.getInformacionMaestria().getCohorte())
                .periodoIngreso(informacionEstudiantes.getInformacionMaestria().getPeriodoIngreso())
                .fechaGrado(informacionEstudiantes.getFechaGrado())
                .telefono(informacionEstudiantes.getPersona().getTelefono())
                .correo(informacionEstudiantes.getPersona().getCorreoElectronico())
                .build();

        return informacionGeneralResponseDto;
    }
}
