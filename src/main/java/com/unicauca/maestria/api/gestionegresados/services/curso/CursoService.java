package com.unicauca.maestria.api.gestionegresados.services.curso;

import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.gestionegresados.dtos.CursoSaveDto;

public interface CursoService {

    public CursoSaveDto crear(CursoSaveDto oficio, BindingResult result);

    public CursoSaveDto buscarPorId(Long idTrabajoGrado);

    public CursoSaveDto actualizar(Long id, CursoSaveDto examenValoracionDto, BindingResult result);
}
