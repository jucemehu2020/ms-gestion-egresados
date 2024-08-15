package com.unicauca.maestria.api.gestionegresados.services.curso;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.gestionegresados.dtos.EstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.ListadoAsignaturasDto;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursoSaveDto;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursosResponseDto;
import com.unicauca.maestria.api.gestionegresados.exceptions.FieldErrorException;
import com.unicauca.maestria.api.gestionegresados.exceptions.InformationException;
import com.unicauca.maestria.api.gestionegresados.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.gestionegresados.mappers.CursoMapper;
import com.unicauca.maestria.api.gestionegresados.mappers.CursoResponseMapper;
import com.unicauca.maestria.api.gestionegresados.repositories.CursoRepository;
import com.unicauca.maestria.api.gestionegresados.common.client.ArchivoClient;
import com.unicauca.maestria.api.gestionegresados.common.client.ArchivoClientAsignaturas;
import com.unicauca.maestria.api.gestionegresados.domain.Curso;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;
    private final CursoResponseMapper cursoResponseMapper;

    private final ArchivoClient archivoClient;
    private final ArchivoClientAsignaturas archivoClientAsignaturas;

    /**
     * Lista todos los cursos registrados.
     *
     * @return Lista de ListadoAsignaturasDto que contiene la información de los
     *         cursos registrados.
     * @throws InformationException si no hay cursos registrados.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListadoAsignaturasDto> listarCursosExistentes() {
        // Obtener la lista de cursos desde el cliente de asignaturas
        List<ListadoAsignaturasDto> cursosRegistrados = archivoClientAsignaturas.listarAsignaturas();

        // Verificar si la lista está vacía
        if (cursosRegistrados.isEmpty()) {
            throw new InformationException("No hay cursos registrados");
        }

        // Devolver la lista de cursos
        return cursosRegistrados;
    }

    /**
     * Crea un nuevo curso.
     *
     * @param cursoDto DTO que contiene la información del curso a crear.
     * @param result   Resultado de la validación del formulario.
     * @return CursosResponseDto que contiene la información del curso creado.
     * @throws FieldErrorException si existen errores en el formulario de entrada.
     */
    @Override
    @Transactional
    public CursosResponseDto crear(CursoSaveDto cursoDto, BindingResult result) {
        // Verificar si hay errores de validación en el formulario
        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        // Obtener la información del estudiante y del curso
        EstudianteResponseDto informacionEstudiante = archivoClient.obtenerPorIdEstudiante(cursoDto.getIdEstudiante());
        ListadoAsignaturasDto informacionCurso = archivoClientAsignaturas.listarAsignaturaPorId(cursoDto.getIdCurso());

        // Mapear el DTO a la entidad Curso y asignar los valores correspondientes
        Curso cursoTmp = cursoMapper.toEntity(cursoDto);
        cursoTmp.setIdEstudiante(informacionEstudiante.getId());
        cursoTmp.setNombre(informacionCurso.getNombreAsignatura());

        // Guardar el curso en la base de datos y devolver la respuesta mapeada
        Curso cursoRes = cursoRepository.save(cursoTmp);
        return cursoResponseMapper.toDto(cursoRes);
    }

    /**
     * Obtiene la información de un curso específico por su ID.
     *
     * @param idCurso ID del curso a buscar.
     * @return CursosResponseDto que contiene la información del curso encontrado.
     * @throws ResourceNotFoundException si el curso con el ID proporcionado no es
     *                                   encontrado.
     */
    @Override
    @Transactional(readOnly = true)
    public CursosResponseDto obtenerInformacionCurso(Long idCurso) {
        // Buscar el curso por su ID y convertirlo a un DTO
        return cursoRepository.findById(idCurso)
                .map(cursoResponseMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Curso con id " + idCurso + " no encontrado"));
    }

    /**
     * Lista todos los cursos dictados por un estudiante.
     *
     * @param idEstudiante ID del estudiante cuyos cursos se listarán.
     * @return Una lista de CursosResponseDto con la información de los cursos
     *         dictados.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CursosResponseDto> listarCursosDictados(Long idEstudiante) {
        // Verificar la existencia del estudiante
        archivoClient.obtenerPorIdEstudiante(idEstudiante);

        // Buscar los cursos asociados al estudiante en la base de datos
        List<Curso> cursos = cursoRepository.findByEstudianteId(idEstudiante);

        // Convertir las entidades encontradas en una lista de DTOs de respuesta
        return cursos.stream()
                .map(cursoResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza la información de un curso existente.
     *
     * @param id       ID del curso a actualizar.
     * @param cursoDto DTO que contiene la información actualizada del curso.
     * @param result   Resultado de la validación del formulario.
     * @return CursosResponseDto que contiene la información del curso actualizado.
     * @throws FieldErrorException       si existen errores en el formulario de
     *                                   entrada.
     * @throws ResourceNotFoundException si el curso con el ID proporcionado no es
     *                                   encontrado.
     */
    @Override
    public CursosResponseDto actualizar(Long id, CursoSaveDto cursoDto, BindingResult result) {
        // Verificar si hay errores de validación en el formulario
        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        // Verificar la existencia del estudiante asociado al curso
        archivoClient.obtenerPorIdEstudiante(cursoDto.getIdEstudiante());

        // Buscar el curso en la base de datos por su ID
        Curso cursoTmp = cursoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Curso con id " + id + " no encontrado"));

        // Actualizar la información del curso con los nuevos datos del DTO
        updateCurso(cursoTmp, cursoDto);

        // Guardar el curso actualizado en la base de datos y devolver la respuesta
        // mapeada
        Curso responseCurso = cursoRepository.save(cursoTmp);
        return cursoResponseMapper.toDto(responseCurso);
    }

    /**
     * Elimina un curso por su ID.
     *
     * @param id ID del curso a eliminar.
     * @throws ResourceNotFoundException si el curso con el ID proporcionado no es
     *                                   encontrado.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        // Buscar el curso en la base de datos por su ID
        cursoRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Curso con id: " + id + " no encontrado"));

        // Eliminar el curso de la base de datos
        cursoRepository.deleteById(id);
    }

    /**
     * Actualiza los detalles de una entidad Curso.
     *
     * @param curso    La entidad Curso a actualizar.
     * @param cursoDto DTO que contiene los nuevos valores para la entidad Curso.
     */
    private void updateCurso(Curso curso, CursoSaveDto cursoDto) {
        // Obtener la información del curso desde el cliente de asignaturas
        ListadoAsignaturasDto informacionCurso = archivoClientAsignaturas.listarAsignaturaPorId(cursoDto.getIdCurso());

        // Asignar los nuevos valores a la entidad Curso
        curso.setNombre(informacionCurso.getNombreAsignatura());
        curso.setOrientadoA(cursoDto.getOrientadoA());
        curso.setFechaInicio(cursoDto.getFechaInicio());
        curso.setFechaFin(cursoDto.getFechaFin());
    }

}
