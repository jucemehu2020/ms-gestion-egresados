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

    @Override
    @Transactional(readOnly = true)
    public List<ListadoAsignaturasDto> listarCursosExistentes() {
        List<ListadoAsignaturasDto> cursosRegistrados = archivoClientAsignaturas.listarAsignaturas();
        if (cursosRegistrados.isEmpty()) {
            throw new InformationException("No hay cursos registrados");
        }
        return cursosRegistrados;
    }

    @Override
    @Transactional
    public CursosResponseDto crear(CursoSaveDto cursoDto, BindingResult result) {

        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        EstudianteResponseDto informacionEstudiante = archivoClient
                .obtenerPorIdEstudiante(cursoDto.getIdEstudiante());

        ListadoAsignaturasDto informacionCurso = archivoClientAsignaturas.listarAsignaturaPorId(cursoDto.getIdCurso());

        Curso cursoTmp = cursoMapper.toEntity(cursoDto);
        cursoTmp.setIdEstudiante(informacionEstudiante.getId());
        cursoTmp.setNombre(informacionCurso.getNombreAsignatura());

        Curso cursoRes = cursoRepository.save(cursoTmp);
        return cursoResponseMapper.toDto(cursoRes);
    }

    @Override
    @Transactional(readOnly = true)
    public CursosResponseDto obtenerInformacionCurso(Long idCurso) {
        return cursoRepository.findById(idCurso)
                .map(cursoResponseMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Curso con id " + idCurso + " no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursosResponseDto> listarCursosDictados(Long idEstudiante) {
        archivoClient.obtenerPorIdEstudiante(idEstudiante);

        List<Curso> cursos = cursoRepository.findByEstudianteId(idEstudiante);

        return cursos.stream()
                .map(cursoResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CursosResponseDto actualizar(Long id, CursoSaveDto cursoDto, BindingResult result) {

        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        archivoClient.obtenerPorIdEstudiante(cursoDto.getIdEstudiante());

        Curso cursoTmp = cursoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Curso con id " + id + " no encontrado"));
        Curso responseCurso = null;
        
        if (cursoTmp != null) {
            updateCurso(cursoTmp, cursoDto);
            responseCurso = cursoRepository.save(cursoTmp);
        }
        return cursoResponseMapper.toDto(responseCurso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        cursoRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Curso con id: " + id + " no encontrado"));
        cursoRepository.deleteById(id);

    }

    private void updateCurso(Curso curso, CursoSaveDto cursoDto) {

        // Busca el curso
        ListadoAsignaturasDto informacionCurso = archivoClientAsignaturas.listarAsignaturaPorId(cursoDto.getIdCurso());

        curso.setNombre(informacionCurso.getNombreAsignatura());
        curso.setOrientadoA(cursoDto.getOrientadoA());
        curso.setFechaInicio(cursoDto.getFechaInicio());
        curso.setFechaFin(cursoDto.getFechaFin());
    }
}
