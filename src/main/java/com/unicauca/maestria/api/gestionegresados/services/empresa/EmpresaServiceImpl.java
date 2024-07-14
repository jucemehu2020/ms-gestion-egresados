package com.unicauca.maestria.api.gestionegresados.services.empresa;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.gestionegresados.common.client.ArchivoClient;
import com.unicauca.maestria.api.gestionegresados.domain.Empresa;
import com.unicauca.maestria.api.gestionegresados.dtos.EstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaSaveDto;
import com.unicauca.maestria.api.gestionegresados.exceptions.FieldErrorException;
import com.unicauca.maestria.api.gestionegresados.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.gestionegresados.mappers.EmpresaMapper;
import com.unicauca.maestria.api.gestionegresados.mappers.EmpresaResponseMapper;
import com.unicauca.maestria.api.gestionegresados.repositories.EmpresaRepository;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;
    private final EmpresaResponseMapper empresaResponseMapper;


    private final ArchivoClient archivoClient;

    @Override
    @Transactional
    public EmpresaResponseDto crear(EmpresaSaveDto empresaDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        EstudianteResponseDto informacionEstudiante = archivoClient
                .obtenerPorIdEstudiante(empresaDto.getIdEstudiante());

        Empresa empresaTmp = empresaMapper.toEntity(empresaDto);
        empresaTmp.setIdEstudiante(informacionEstudiante.getId());

        Empresa cursoRes = empresaRepository.save(empresaTmp);
        return empresaResponseMapper.toDto(cursoRes);
    }

    @Override
    @Transactional(readOnly = true)
    public EmpresaResponseDto buscarPorId(Long idEmpresa) {
        return empresaRepository.findById(idEmpresa)
                .map(empresaResponseMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Empresa con id " + idEmpresa + " no encontrada"));
    }

    @Override
    public EmpresaResponseDto actualizar(Long id, EmpresaSaveDto empresaDto, BindingResult result) {

        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        archivoClient.obtenerPorIdEstudiante(empresaDto.getIdEstudiante());

        Empresa empresaTmp = empresaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Empresa con id " + id + " no encontrado"));
        Empresa responseEmpresa = null;
        if (empresaTmp != null) {
            updateEmpresa(empresaTmp, empresaDto);
            responseEmpresa = empresaRepository.save(empresaTmp);
        }
        return empresaResponseMapper.toDto(responseEmpresa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpresaResponseDto> listarEmpresasEstudiante(Long idEstudiante) {
        archivoClient.obtenerPorIdEstudiante(idEstudiante);

        List<Empresa> empresa = empresaRepository.findByEstudianteId(idEstudiante);

        return empresa.stream()
                .map(empresaResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    // Funciones privadas
    private void updateEmpresa(Empresa empresa, EmpresaSaveDto empresaDto) {
        empresa.setNombre(empresaDto.getNombre());
        empresa.setUbicacion(empresaDto.getUbicacion());
        empresa.setCargo(empresa.getCargo());
        empresa.setJefeDirecto(empresaDto.getJefeDirecto());
        empresa.setTelefono(empresaDto.getTelefono());
        empresa.setCorreo(empresaDto.getCorreo());
        empresa.setEstado(empresaDto.getEstado());
    }
}
