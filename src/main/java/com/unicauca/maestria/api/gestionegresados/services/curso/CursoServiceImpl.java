package com.unicauca.maestria.api.gestionegresados.services.curso;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.gestionegresados.dtos.CursoSaveDto;
import com.unicauca.maestria.api.gestionegresados.exceptions.FieldErrorException;
import com.unicauca.maestria.api.gestionegresados.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.gestionegresados.mappers.CursoMapper;
import com.unicauca.maestria.api.gestionegresados.repositories.CursoRepository;

import com.unicauca.maestria.api.gestionegresados.domain.Curso;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;

    @Override
    @Transactional
    public CursoSaveDto crear(CursoSaveDto cursoDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        Curso cursoRes = cursoRepository.save(cursoMapper.toEntity(cursoDto));
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

    // Funciones privadas
    private void updateCurso(Curso curso, CursoSaveDto cursoDto) {
        curso.setNombre(cursoDto.getNombre());
        curso.setOrientadoA(cursoDto.getOrientadoA());
        curso.setFechaInicio(curso.getFechaInicio());
        curso.setFechaFin(cursoDto.getFechaFin());
    }
}
