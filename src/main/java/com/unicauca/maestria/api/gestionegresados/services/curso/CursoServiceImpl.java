package com.unicauca.maestria.api.gestionegresados.services.curso;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.gestionegresados.dtos.CursoSaveDto;
import com.unicauca.maestria.api.gestionegresados.exceptions.FieldErrorException;
import com.unicauca.maestria.api.gestionegresados.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.gestionegresados.mappers.CursoMapper;
import com.unicauca.maestria.api.gestionegresados.repositories.CursoRepository;
import com.unicauca.maestria.api.gestionegresados.repositories.estudiante.EstudianteRepository;
import com.unicauca.maestria.api.gestionegresados.domain.Curso;
import com.unicauca.maestria.api.gestionegresados.domain.estudiante.Estudiante;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;

    @Override
    @Transactional
    public CursoSaveDto crear(CursoSaveDto cursoDto, BindingResult result) {

        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        // Obtener el estudiante
        Estudiante estudianteBD = estudianteRepository.findById(cursoDto.getIdEstudiante())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Estudiante con id: " + cursoDto.getIdEstudiante() + " No encontrado"));

        Curso cursoTmp = cursoMapper.toEntity(cursoDto);
        cursoTmp.setEstudiante(estudianteBD);

        Curso cursoRes = cursoRepository.save(cursoTmp);
        return cursoMapper.toDto(cursoRes);
    }

    @Override
    @Transactional(readOnly = true)
    public CursoSaveDto buscarPorId(Long idCurso) {
        return cursoRepository.findById(idCurso)
                .map(cursoMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Curso con id: " + idCurso + " no encontrado"));
    }

    @Override
    public CursoSaveDto actualizar(Long id, CursoSaveDto cursoDto, BindingResult result) {
        Curso cursoTmp = cursoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Examen de valoracion con id: " + id + " no encontrado"));
        Curso responseCurso = null;
        if (cursoTmp != null) {
            updateCurso(cursoTmp, cursoDto);
            responseCurso = cursoRepository.save(cursoTmp);
        }
        return cursoMapper.toDto(responseCurso);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursoSaveDto> listar() {
        return cursoMapper.toDtoList(this.cursoRepository.findAll());
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

    // Funciones privadas
    private void updateCurso(Curso curso, CursoSaveDto cursoDto) {
        curso.setNombre(cursoDto.getNombre());
        curso.setOrientadoA(cursoDto.getOrientadoA());
        curso.setFechaInicio(curso.getFechaInicio());
        curso.setFechaFin(cursoDto.getFechaFin());
    }
}
