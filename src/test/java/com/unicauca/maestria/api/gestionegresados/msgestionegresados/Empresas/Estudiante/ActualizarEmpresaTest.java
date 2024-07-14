package com.unicauca.maestria.api.gestionegresados.msgestionegresados.Empresas.Estudiante;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.unicauca.maestria.api.gestionegresados.common.client.ArchivoClient;
import com.unicauca.maestria.api.gestionegresados.domain.Empresa;
import com.unicauca.maestria.api.gestionegresados.dtos.EstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaSaveDto;
import com.unicauca.maestria.api.gestionegresados.exceptions.FieldErrorException;
import com.unicauca.maestria.api.gestionegresados.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.gestionegresados.exceptions.ServiceUnavailableException;
import com.unicauca.maestria.api.gestionegresados.mappers.EmpresaMapper;
import com.unicauca.maestria.api.gestionegresados.mappers.EmpresaResponseMapper;
import com.unicauca.maestria.api.gestionegresados.repositories.EmpresaRepository;
import com.unicauca.maestria.api.gestionegresados.services.empresa.EmpresaServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ActualizarEmpresaTest {
    @Mock
    private EmpresaRepository empresaRepository;
    @Mock
    private EmpresaMapper empresaMapper;
    @Mock
    private EmpresaResponseMapper empresaResponseMapper;
    @Mock
    private ArchivoClient archivoClient;
    @Mock
    private BindingResult result;
    @InjectMocks
    private EmpresaServiceImpl empresaServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        empresaServiceImpl = new EmpresaServiceImpl(
                empresaRepository,
                empresaMapper,
                empresaResponseMapper,
                archivoClient);
    }

    @Test
    void ActualizarEmpresaTest_ActualizacionExitosa() {

        Long idEmpresa = 1L;

        EmpresaSaveDto empresaSaveDto = new EmpresaSaveDto();
        empresaSaveDto.setIdEstudiante(1L);
        empresaSaveDto.setNombre("GSE");
        empresaSaveDto.setUbicacion("Bogota");
        empresaSaveDto.setCargo("Desarrollador Junior Nivel 1");
        empresaSaveDto.setJefeDirecto("Juan M.");
        empresaSaveDto.setTelefono("31674832734");
        empresaSaveDto.setCorreo("julio@gse.com.co");
        empresaSaveDto.setEstado("Activo");

        when(result.hasErrors()).thenReturn(false);

        EstudianteResponseDto estudianteResponseDto = new EstudianteResponseDto();
        estudianteResponseDto.setId(1L);

        when(archivoClient.obtenerPorIdEstudiante(empresaSaveDto.getIdEstudiante())).thenReturn(estudianteResponseDto);

        Empresa empresaOld = new Empresa();
        empresaOld.setId(1L);
        empresaOld.setIdEstudiante(1L);
        empresaOld.setNombre("GSE");
        empresaOld.setUbicacion("Bogota");
        empresaOld.setCargo("Desarrollador Junior");
        empresaOld.setJefeDirecto("Juan M.");
        empresaOld.setTelefono("31674832734");
        empresaOld.setCorreo("julio@gse.com.co");
        empresaOld.setEstado("Activo");

        when(empresaRepository.findById(idEmpresa)).thenReturn(Optional.of(empresaOld));

        Empresa empresaNew = new Empresa();
        empresaNew.setId(empresaOld.getId());
        empresaNew.setIdEstudiante(empresaSaveDto.getIdEstudiante());
        empresaNew.setNombre(empresaSaveDto.getNombre());
        empresaNew.setUbicacion(empresaSaveDto.getUbicacion());
        empresaNew.setCargo(empresaSaveDto.getCargo());
        empresaNew.setJefeDirecto(empresaSaveDto.getJefeDirecto());
        empresaNew.setTelefono(empresaSaveDto.getTelefono());
        empresaNew.setCorreo(empresaSaveDto.getCorreo());
        empresaNew.setEstado(empresaSaveDto.getEstado());

        when(empresaRepository.save(empresaOld)).thenReturn(empresaNew);

        EmpresaResponseDto empresaResponseDto = new EmpresaResponseDto();
        empresaResponseDto.setId(empresaNew.getId());
        empresaResponseDto.setNombre(empresaNew.getNombre());
        empresaResponseDto.setUbicacion(empresaNew.getUbicacion());
        empresaResponseDto.setCargo(empresaNew.getCargo());
        empresaResponseDto.setJefeDirecto(empresaNew.getJefeDirecto());
        empresaResponseDto.setTelefono(empresaNew.getTelefono());
        empresaResponseDto.setCorreo(empresaNew.getCorreo());
        empresaResponseDto.setEstado(empresaNew.getEstado());

        when(empresaResponseMapper.toDto(empresaNew)).thenReturn(empresaResponseDto);

        EmpresaResponseDto resultado = empresaServiceImpl.actualizar(idEmpresa, empresaSaveDto, result);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("GSE", resultado.getNombre());
        assertEquals("Bogota", resultado.getUbicacion());
        assertEquals("Desarrollador Junior Nivel 1", resultado.getCargo());
        assertEquals("Juan M.", resultado.getJefeDirecto());
        assertEquals("31674832734", resultado.getTelefono());
        assertEquals("julio@gse.com.co", resultado.getCorreo());
        assertEquals("Activo", resultado.getEstado());

    }

    @Test
    void ActualizarEmpresaTest_FaltanAtributos() {
        Long idEmpresa = 1L;

        EmpresaSaveDto empresaSaveDto = new EmpresaSaveDto();
        empresaSaveDto.setIdEstudiante(1L);
        empresaSaveDto.setNombre("GSE");
        empresaSaveDto.setUbicacion("Bogota");
        empresaSaveDto.setCargo("Desarrollador Junior");
        empresaSaveDto.setTelefono("31674832734");
        empresaSaveDto.setCorreo("julio@gse.com.co");
        empresaSaveDto.setEstado("Activo");

        FieldError fieldError = new FieldError("EmpresaServiceImpl", "jefeDirecto",
                "no debe ser nulo");
        when(result.hasErrors()).thenReturn(true);
        when(result.getFieldErrors()).thenReturn(List.of(fieldError));

        FieldErrorException exception = assertThrows(FieldErrorException.class, () -> {
            empresaServiceImpl.actualizar(idEmpresa, empresaSaveDto, result);
        });

        assertNotNull(exception.getResult());
        List<FieldError> fieldErrors = exception.getResult().getFieldErrors();
        assertFalse(fieldErrors.isEmpty());
        String actualMessage = "El campo: " + fieldErrors.get(0).getField() + ", "
                + fieldError.getDefaultMessage();
        String expectedMessage = "El campo: jefeDirecto, no debe ser nulo";
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ActualizarEmpresaTest_EstudianteNoExiste() {
        Long idEmpresa = 1L;

        EmpresaSaveDto empresaSaveDto = new EmpresaSaveDto();
        empresaSaveDto.setIdEstudiante(4L);
        empresaSaveDto.setNombre("GSE");
        empresaSaveDto.setUbicacion("Bogota");
        empresaSaveDto.setCargo("Desarrollador Junior");
        empresaSaveDto.setTelefono("31674832734");
        empresaSaveDto.setCorreo("julio@gse.com.co");
        empresaSaveDto.setEstado("Activo");

        when(result.hasErrors()).thenReturn(false);

        when(archivoClient.obtenerPorIdEstudiante(empresaSaveDto.getIdEstudiante()))
                .thenThrow(new ResourceNotFoundException("Estudiantes con id "
                        + empresaSaveDto.getIdEstudiante() + " no encontrado"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            empresaServiceImpl.actualizar(idEmpresa, empresaSaveDto, result);
        });

        assertNotNull(exception.getMessage());
        String expectedMessage = "Estudiantes con id 4 no encontrado";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void ActualizarEmpresaTest_ServidorEstudianteCaido() {
        Long idEmpresa = 1L;

        EmpresaSaveDto empresaSaveDto = new EmpresaSaveDto();
        empresaSaveDto.setIdEstudiante(4L);
        empresaSaveDto.setNombre("GSE");
        empresaSaveDto.setUbicacion("Bogota");
        empresaSaveDto.setCargo("Desarrollador Junior");
        empresaSaveDto.setTelefono("31674832734");
        empresaSaveDto.setCorreo("julio@gse.com.co");
        empresaSaveDto.setEstado("Activo");

        when(result.hasErrors()).thenReturn(false);

        when(archivoClient.obtenerPorIdEstudiante(empresaSaveDto.getIdEstudiante()))
                .thenThrow(new ServiceUnavailableException(
                        "Servidor externo actualmente fuera de servicio"));

        ServiceUnavailableException thrown = assertThrows(
                ServiceUnavailableException.class,
                () -> empresaServiceImpl.actualizar(idEmpresa, empresaSaveDto, result),
                "Servidor externo actualmente fuera de servicio");

        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("Servidor externo actualmente fuera de servicio"));
    }

}
