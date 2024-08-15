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

    /**
     * Crea una nueva entidad Empresa asociada a un estudiante.
     *
     * @param empresaDto DTO que contiene la información de la empresa a crear.
     * @param result     Resultado de la validación del formulario.
     * @return EmpresaResponseDto que contiene la información de la empresa creada.
     * @throws FieldErrorException si existen errores en el formulario de entrada.
     */
    @Override
    @Transactional
    public EmpresaResponseDto crear(EmpresaSaveDto empresaDto, BindingResult result) {
        // Verificar si hay errores de validación en el formulario
        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        // Obtener la información del estudiante asociado a la empresa
        EstudianteResponseDto informacionEstudiante = archivoClient
                .obtenerPorIdEstudiante(empresaDto.getIdEstudiante());

        // Convertir el DTO de la empresa en una entidad y asociar el estudiante
        Empresa empresaTmp = empresaMapper.toEntity(empresaDto);
        empresaTmp.setIdEstudiante(informacionEstudiante.getId());

        // Guardar la empresa en la base de datos
        Empresa empresaGuardada = empresaRepository.save(empresaTmp);

        // Convertir la entidad guardada en un DTO de respuesta
        return empresaResponseMapper.toDto(empresaGuardada);
    }

    /**
     * Busca una empresa por su ID.
     *
     * @param idEmpresa ID de la empresa a buscar.
     * @return EmpresaResponseDto que contiene la información de la empresa
     *         encontrada.
     * @throws ResourceNotFoundException si la empresa con el ID proporcionado no es
     *                                   encontrada.
     */
    @Override
    @Transactional(readOnly = true)
    public EmpresaResponseDto buscarPorId(Long idEmpresa) {
        // Buscar la empresa por su ID en la base de datos y convertirla en un DTO
        return empresaRepository.findById(idEmpresa)
                .map(empresaResponseMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Empresa con id " + idEmpresa + " no encontrada"));
    }

    /**
     * Actualiza la información de una empresa existente.
     *
     * @param id         ID de la empresa a actualizar.
     * @param empresaDto DTO que contiene la información actualizada de la empresa.
     * @param result     Resultado de la validación del formulario.
     * @return EmpresaResponseDto que contiene la información de la empresa
     *         actualizada.
     * @throws FieldErrorException       si existen errores en el formulario de
     *                                   entrada.
     * @throws ResourceNotFoundException si la empresa con el ID proporcionado no es
     *                                   encontrada.
     */
    @Override
    public EmpresaResponseDto actualizar(Long id, EmpresaSaveDto empresaDto, BindingResult result) {
        // Verificar si hay errores de validación en el formulario
        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        // Verificar la existencia del estudiante asociado a la empresa
        archivoClient.obtenerPorIdEstudiante(empresaDto.getIdEstudiante());

        // Buscar la empresa en la base de datos por su ID
        Empresa empresaTmp = empresaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Empresa con id " + id + " no encontrado"));

        // Actualizar la información de la empresa con los nuevos datos del DTO
        updateEmpresa(empresaTmp, empresaDto);

        // Guardar la empresa actualizada en la base de datos
        Empresa empresaActualizada = empresaRepository.save(empresaTmp);

        // Convertir la entidad actualizada en un DTO de respuesta
        return empresaResponseMapper.toDto(empresaActualizada);
    }

    /**
     * Lista todas las empresas asociadas a un estudiante.
     *
     * @param idEstudiante ID del estudiante cuyas empresas se listarán.
     * @return Una lista de EmpresaResponseDto con la información de las empresas
     *         asociadas al estudiante.
     * @throws ResourceNotFoundException si el estudiante no es encontrado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmpresaResponseDto> listarEmpresasEstudiante(Long idEstudiante) {
        // Verificar la existencia del estudiante
        archivoClient.obtenerPorIdEstudiante(idEstudiante);

        // Buscar todas las empresas asociadas al estudiante en la base de datos
        List<Empresa> empresas = empresaRepository.findByEstudianteId(idEstudiante);

        // Convertir las entidades encontradas en una lista de DTOs de respuesta
        return empresas.stream()
                .map(empresaResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza los detalles de una entidad Empresa.
     *
     * @param empresa    La entidad Empresa a actualizar.
     * @param empresaDto DTO que contiene los nuevos valores para la entidad
     *                   Empresa.
     */
    private void updateEmpresa(Empresa empresa, EmpresaSaveDto empresaDto) {
        empresa.setNombre(empresaDto.getNombre());
        empresa.setUbicacion(empresaDto.getUbicacion());
        empresa.setCargo(empresaDto.getCargo());
        empresa.setJefeDirecto(empresaDto.getJefeDirecto());
        empresa.setTelefono(empresaDto.getTelefono());
        empresa.setCorreo(empresaDto.getCorreo());
        empresa.setEstado(empresaDto.getEstado());
    }

}
