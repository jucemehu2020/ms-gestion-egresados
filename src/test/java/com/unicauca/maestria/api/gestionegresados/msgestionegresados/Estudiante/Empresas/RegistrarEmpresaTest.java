package com.unicauca.maestria.api.gestionegresados.msgestionegresados.Estudiante.Empresas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

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
public class RegistrarEmpresaTest {
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
    void RegistrarEmpresaTest_RegistroExitoso() {

        EmpresaSaveDto empresaSaveDto = new EmpresaSaveDto();
        empresaSaveDto.setIdEstudiante(1L);
        empresaSaveDto.setNombre("GSE");
        empresaSaveDto.setUbicacion("Bogota");
        empresaSaveDto.setCargo("Desarrollador Junior");
        empresaSaveDto.setJefeDirecto("Juan M.");
        empresaSaveDto.setTelefono("31674832734");
        empresaSaveDto.setCorreo("julio@gse.com.co");
        empresaSaveDto.setEstado("Activo");

        when(result.hasErrors()).thenReturn(false);

        EstudianteResponseDto estudianteResponseDto = new EstudianteResponseDto();
        estudianteResponseDto.setId(1L);

        when(archivoClient.obtenerPorIdEstudiante(empresaSaveDto.getIdEstudiante())).thenReturn(estudianteResponseDto);

        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setIdEstudiante(empresaSaveDto.getIdEstudiante());
        empresa.setNombre(empresaSaveDto.getNombre());
        empresa.setUbicacion(empresaSaveDto.getUbicacion());
        empresa.setCargo(empresaSaveDto.getCargo());
        empresa.setJefeDirecto(empresaSaveDto.getJefeDirecto());
        empresa.setTelefono(empresaSaveDto.getTelefono());
        empresa.setCorreo(empresaSaveDto.getCorreo());
        empresa.setEstado(empresaSaveDto.getEstado());

        when(empresaMapper.toEntity(empresaSaveDto)).thenReturn(empresa);

        when(empresaRepository.save(empresa)).thenReturn(empresa);

        EmpresaResponseDto empresaResponseDto = new EmpresaResponseDto();
        empresaResponseDto.setId(empresa.getId());
        empresaResponseDto.setNombre(empresa.getNombre());
        empresaResponseDto.setUbicacion(empresa.getUbicacion());
        empresaResponseDto.setCargo(empresa.getCargo());
        empresaResponseDto.setJefeDirecto(empresa.getJefeDirecto());
        empresaResponseDto.setTelefono(empresa.getTelefono());
        empresaResponseDto.setCorreo(empresa.getCorreo());
        empresaResponseDto.setEstado(empresa.getEstado());

        when(empresaResponseMapper.toDto(empresa)).thenReturn(empresaResponseDto);

        EmpresaResponseDto resultado = empresaServiceImpl.crear(empresaSaveDto, result);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("GSE", resultado.getNombre());
        assertEquals("Bogota", resultado.getUbicacion());
        assertEquals("Desarrollador Junior", resultado.getCargo());
        assertEquals("Juan M.", resultado.getJefeDirecto());
        assertEquals("31674832734", resultado.getTelefono());
        assertEquals("julio@gse.com.co", resultado.getCorreo());
        assertEquals("Activo", resultado.getEstado());

    }

    @Test
    void RegistrarEmpresaTest_FaltanAtributos() {

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
            empresaServiceImpl.crear(empresaSaveDto, result);
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
    void RegistrarEmpresaTest_EstudianteNoExiste() {

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
            empresaServiceImpl.crear(empresaSaveDto, result);
        });

        assertNotNull(exception.getMessage());
        String expectedMessage = "Estudiantes con id 4 no encontrado";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void RegistrarEmpresaTest_ServidorEstudianteCaido() {

        EmpresaSaveDto empresaSaveDto = new EmpresaSaveDto();
        empresaSaveDto.setIdEstudiante(1L);
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
                () -> empresaServiceImpl.crear(empresaSaveDto, result),
                "Servidor externo actualmente fuera de servicio");

        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("Servidor externo actualmente fuera de servicio"));
    }
}
