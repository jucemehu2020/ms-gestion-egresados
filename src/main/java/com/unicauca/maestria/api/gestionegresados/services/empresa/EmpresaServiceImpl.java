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
     * Crea una nueva empresa asociada a un estudiante.
     *
     * @param empresaDto Objeto EmpresaSaveDto que contiene los datos de la empresa
     *                   a crear.
     * @param result     Objeto BindingResult que contiene los resultados de la
     *                   validación del formulario.
     * @return Objeto EmpresaResponseDto con la información de la empresa creada.
     * @throws FieldErrorException si hay errores de validación en el formulario.
     */
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

    /**
     * Busca una empresa por su ID.
     *
     * @param idEmpresa ID de la empresa a buscar.
     * @return Objeto EmpresaResponseDto con la información de la empresa
     *         encontrada.
     * @throws ResourceNotFoundException si no se encuentra la empresa con el ID
     *                                   proporcionado.
     */
    @Override
    @Transactional(readOnly = true)
    public EmpresaResponseDto buscarPorId(Long idEmpresa) {
        return empresaRepository.findById(idEmpresa)
                .map(empresaResponseMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Empresa con id " + idEmpresa + " no encontrada"));
    }

    /**
     * Actualiza la información de una empresa existente.
     *
     * @param id         ID de la empresa a actualizar.
     * @param empresaDto Objeto EmpresaSaveDto que contiene los datos de la empresa
     *                   a actualizar.
     * @param result     Objeto BindingResult que contiene los resultados de la
     *                   validación del formulario.
     * @return Objeto EmpresaResponseDto con la información de la empresa
     *         actualizada.
     * @throws FieldErrorException       si hay errores de validación en el
     *                                   formulario.
     * @throws ResourceNotFoundException si no se encuentra la empresa con el ID
     *                                   proporcionado.
     */
    @Override
    public EmpresaResponseDto actualizar(Long id, EmpresaSaveDto empresaDto, BindingResult result) {

        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        archivoClient.obtenerPorIdEstudiante(empresaDto.getIdEstudiante());

        Empresa empresaTmp = empresaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Empresa con id " + id + " no encontrado"));

        updateEmpresa(empresaTmp, empresaDto);
        Empresa responseEmpresa = empresaRepository.save(empresaTmp);

        return empresaResponseMapper.toDto(responseEmpresa);
    }

    /**
     * Lista las empresas asociadas a un estudiante.
     *
     * @param idEstudiante ID del estudiante cuyos datos de empresas se desean
     *                     listar.
     * @return Una lista de objetos EmpresaResponseDto que representan las empresas
     *         asociadas al estudiante.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmpresaResponseDto> listarEmpresasEstudiante(Long idEstudiante) {
        archivoClient.obtenerPorIdEstudiante(idEstudiante);

        List<Empresa> empresas = empresaRepository.findByEstudianteId(idEstudiante);

        return empresas.stream()
                .map(empresaResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza los detalles de una empresa existente.
     *
     * @param empresa    Entidad Empresa que se desea actualizar.
     * @param empresaDto Objeto EmpresaSaveDto que contiene los nuevos datos de la
     *                   empresa.
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
