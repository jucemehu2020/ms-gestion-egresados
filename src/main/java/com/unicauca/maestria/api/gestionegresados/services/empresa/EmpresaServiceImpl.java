package com.unicauca.maestria.api.gestionegresados.services.empresa;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.gestionegresados.domain.Curso;
import com.unicauca.maestria.api.gestionegresados.domain.Empresa;
import com.unicauca.maestria.api.gestionegresados.domain.estudiante.Estudiante;
import com.unicauca.maestria.api.gestionegresados.dtos.CursoSaveDto;
import com.unicauca.maestria.api.gestionegresados.dtos.EmpresaSaveDto;
import com.unicauca.maestria.api.gestionegresados.exceptions.FieldErrorException;
import com.unicauca.maestria.api.gestionegresados.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.gestionegresados.mappers.EmpresaMapper;
import com.unicauca.maestria.api.gestionegresados.repositories.EmpresaRepository;
import com.unicauca.maestria.api.gestionegresados.repositories.estudiante.EstudianteRepository;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EstudianteRepository estudianteRepository;
    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;

    @Override
    @Transactional
    public EmpresaSaveDto crear(EmpresaSaveDto empresaDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        // Obtener el estudiante
        Estudiante estudianteBD = estudianteRepository.findById(empresaDto.getIdEstudiante())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Estudiante con id: " + empresaDto.getIdEstudiante() + " No encontrado"));

        Empresa empresaTmp = empresaMapper.toEntity(empresaDto);
        empresaTmp.setEstudiante(estudianteBD);

        Empresa cursoRes = empresaRepository.save(empresaTmp);
        return empresaMapper.toDto(cursoRes);
    }

    @Override
    @Transactional(readOnly = true)
    public EmpresaSaveDto buscarPorId(Long idEmpresa) {
        return empresaRepository.findById(idEmpresa)
                .map(empresaMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Empresa con id: " + idEmpresa + " no encontrado"));
    }

    @Override
    public EmpresaSaveDto actualizar(Long id, EmpresaSaveDto empresaDto, BindingResult result) {
        Empresa empresaTmp = empresaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Empresa con id: " + id + " no encontrado"));
        Empresa responseEmpresa = null;
        if (empresaTmp != null) {
            updateEmpresa(empresaTmp, empresaDto);
            responseEmpresa = empresaRepository.save(empresaTmp);
        }
        return empresaMapper.toDto(responseEmpresa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpresaSaveDto> listar() {
        return empresaMapper.toDtoList(this.empresaRepository.findAll());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        empresaRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Empresa con id: " + id + " no encontrado"));
        empresaRepository.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpresaSaveDto> listarEmpresas(Long idEstudiante) {
        List<Empresa> empresa = empresaRepository.findByEstudianteId(idEstudiante);

        return empresa.stream()
                .map(empresaMapper::toDto)
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
