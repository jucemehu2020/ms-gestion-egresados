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
     * Obtiene la lista de cursos existentes.
     *
     * @return Una lista de objetos ListadoAsignaturasDto que representan los cursos
     *         registrados.
     * @throws InformationException si no hay cursos registrados.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListadoAsignaturasDto> listarCursosExistentes() {
        List<ListadoAsignaturasDto> cursosRegistrados = archivoClientAsignaturas.listarAsignaturas();
        if (cursosRegistrados.isEmpty()) {
            throw new InformationException("No hay cursos registrados");
        }
        return cursosRegistrados;
    }

    /**
     * Crea un nuevo curso utilizando la información proporcionada.
     *
     * @param cursoDto Objeto CursoSaveDto que contiene los datos del curso a crear.
     * @param result   Objeto BindingResult que contiene los resultados de la
     *                 validación del formulario.
     * @return Objeto CursosResponseDto con la información del curso creado.
     * @throws FieldErrorException si hay errores de validación en el formulario.
     */
    @Override
    @Transactional
    public CursosResponseDto crear(CursoSaveDto cursoDto, BindingResult result) {

        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        // Obtener información del estudiante
        EstudianteResponseDto informacionEstudiante = archivoClient
                .obtenerPorIdEstudiante(cursoDto.getIdEstudiante());

        // Obtener información del curso
        ListadoAsignaturasDto informacionCurso = archivoClientAsignaturas.listarAsignaturaPorId(cursoDto.getIdCurso());

        // Mapear el DTO a la entidad Curso y asignar los valores obtenidos
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
     * @return Objeto CursosResponseDto con la información del curso encontrado.
     * @throws ResourceNotFoundException si no se encuentra el curso con el ID
     *                                   proporcionado.
     */
    @Override
    @Transactional(readOnly = true)
    public CursosResponseDto obtenerInformacionCurso(Long idCurso) {
        return cursoRepository.findById(idCurso)
                .map(cursoResponseMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Curso con id " + idCurso + " no encontrado"));
    }

    /**
     * Lista los cursos dictados por un estudiante específico.
     *
     * @param idEstudiante ID del estudiante cuyos cursos se desean listar.
     * @return Una lista de objetos CursosResponseDto que representan los cursos
     *         dictados por el estudiante.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CursosResponseDto> listarCursosDictados(Long idEstudiante) {
        archivoClient.obtenerPorIdEstudiante(idEstudiante);

        List<Curso> cursos = cursoRepository.findByEstudianteId(idEstudiante);

        return cursos.stream()
                .map(cursoResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza la información de un curso existente.
     *
     * @param id       ID del curso a actualizar.
     * @param cursoDto Objeto CursoSaveDto que contiene los datos del curso a
     *                 actualizar.
     * @param result   Objeto BindingResult que contiene los resultados de la
     *                 validación del formulario.
     * @return Objeto CursosResponseDto con la información del curso actualizado.
     * @throws FieldErrorException       si hay errores de validación en el
     *                                   formulario.
     * @throws ResourceNotFoundException si no se encuentra el curso con el ID
     *                                   proporcionado.
     */
    @Override
    public CursosResponseDto actualizar(Long id, CursoSaveDto cursoDto, BindingResult result) {

        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        archivoClient.obtenerPorIdEstudiante(cursoDto.getIdEstudiante());

        Curso cursoTmp = cursoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Curso con id " + id + " no encontrado"));

        updateCurso(cursoTmp, cursoDto);
        Curso responseCurso = cursoRepository.save(cursoTmp);

        return cursoResponseMapper.toDto(responseCurso);
    }

    /**
     * Elimina un curso por su ID.
     *
     * @param id ID del curso a eliminar.
     * @throws ResourceNotFoundException si no se encuentra el curso con el ID
     *                                   proporcionado.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        cursoRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Curso con id: " + id + " no encontrado"));
        cursoRepository.deleteById(id);
    }

    /**
     * Actualiza los detalles de un curso existente.
     *
     * @param curso    Entidad Curso que se desea actualizar.
     * @param cursoDto Objeto CursoSaveDto que contiene los nuevos datos del curso.
     */
    private void updateCurso(Curso curso, CursoSaveDto cursoDto) {

        ListadoAsignaturasDto informacionCurso = archivoClientAsignaturas.listarAsignaturaPorId(cursoDto.getIdCurso());

        curso.setNombre(informacionCurso.getNombreAsignatura());
        curso.setOrientadoA(cursoDto.getOrientadoA());
        curso.setFechaInicio(cursoDto.getFechaInicio());
        curso.setFechaFin(cursoDto.getFechaFin());
    }

}
