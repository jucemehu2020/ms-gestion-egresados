package com.unicauca.maestria.api.gestionegresados.services.empresa;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.gestionegresados.dtos.CursoSaveDto;
import com.unicauca.maestria.api.gestionegresados.dtos.EmpresaSaveDto;

public interface EmpresaService {

    public EmpresaSaveDto crear(EmpresaSaveDto oficio, BindingResult result);

    public EmpresaSaveDto buscarPorId(Long idTrabajoGrado);

    public EmpresaSaveDto actualizar(Long id, EmpresaSaveDto examenValoracionDto, BindingResult result);

    public List<EmpresaSaveDto> listar();

    public void eliminar(Long id);

    public List<EmpresaSaveDto> listarEmpresas(Long id);
}
