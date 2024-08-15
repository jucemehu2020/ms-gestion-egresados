package com.unicauca.maestria.api.gestionegresados.services.curso;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.gestionegresados.dtos.ListadoAsignaturasDto;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursoSaveDto;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursosResponseDto;

public interface CursoService {

    public List<ListadoAsignaturasDto> listarCursosExistentes();

    public CursosResponseDto crear(CursoSaveDto oficio, BindingResult result);

    public CursosResponseDto obtenerInformacionCurso(Long idTrabajoGrado);

    public List<CursosResponseDto> listarCursosDictados(Long id);

    public CursosResponseDto actualizar(Long id, CursoSaveDto examenValoracionDto, BindingResult result);

    public void eliminar(Long id);
}
