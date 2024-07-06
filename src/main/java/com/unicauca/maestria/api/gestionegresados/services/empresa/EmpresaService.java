package com.unicauca.maestria.api.gestionegresados.services.empresa;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaSaveDto;

public interface EmpresaService {

    public EmpresaResponseDto crear(EmpresaSaveDto oficio, BindingResult result);

    public EmpresaResponseDto buscarPorId(Long idTrabajoGrado);

    public EmpresaResponseDto actualizar(Long id, EmpresaSaveDto examenValoracionDto, BindingResult result);

    // public List<EmpresaResponseDto> listar();

    // public void eliminar(Long id);

    public List<EmpresaResponseDto> listarEmpresasEstudiante(Long id);
}
