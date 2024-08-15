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
import com.unicauca.maestria.api.gestionegresados.exceptions.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class InformacionGeneralServiceImpl implements InformacionGeneralService {

    private final ArchivoClient archivoClient;

    /**
     * Obtiene una lista de estudiantes registrados y mapea la información a un DTO
     * reducido.
     * 
     * @return Una lista de InformacionEstudiantesResponseDto que contiene la
     *         información básica de los estudiantes.
     * @throws InformationException si no hay estudiantes registrados.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InformacionEstudiantesResponseDto> obtenerEstudiantes() {
        // Obtener la información de los estudiantes desde un cliente externo
        List<EstudianteResponseDto> informacionEstudiantes = archivoClient.obtenerEstudiantes();

        // Verifica si la lista de estudiantes está vacía y lanza una excepción si es el
        // caso
        if (informacionEstudiantes.isEmpty()) {
            throw new InformationException("No hay estudiantes registrados");
        }

        // Mapea la información de los estudiantes a un DTO reducido
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

    /**
     * Busca la información completa de un estudiante por su ID.
     * 
     * @param idEstudiante El ID del estudiante a buscar.
     * @return Un InformacionGeneralResponseDto con la información completa del
     *         estudiante.
     * @throws ResourceNotFoundException si el estudiante no es encontrado.
     */
    @Override
    @Transactional(readOnly = true)
    public InformacionGeneralResponseDto buscarEstudiante(Long idEstudiante) {
        // Obtiene la información detallada de un estudiante por su ID desde un cliente
        // externo
        EstudianteResponseDto informacionEstudiantes = archivoClient.obtenerPorIdEstudiante(idEstudiante);

        // Mapea la información obtenida a un DTO para su uso en la capa de respuesta
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
