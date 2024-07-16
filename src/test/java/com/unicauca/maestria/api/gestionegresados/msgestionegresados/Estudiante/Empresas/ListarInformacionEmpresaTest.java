package com.unicauca.maestria.api.gestionegresados.msgestionegresados.Estudiante.Empresas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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

import com.unicauca.maestria.api.gestionegresados.common.client.ArchivoClient;
import com.unicauca.maestria.api.gestionegresados.domain.Empresa;
import com.unicauca.maestria.api.gestionegresados.dtos.empresa.EmpresaResponseDto;
import com.unicauca.maestria.api.gestionegresados.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.gestionegresados.mappers.EmpresaMapper;
import com.unicauca.maestria.api.gestionegresados.mappers.EmpresaResponseMapper;
import com.unicauca.maestria.api.gestionegresados.repositories.EmpresaRepository;
import com.unicauca.maestria.api.gestionegresados.services.empresa.EmpresaServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ListarInformacionEmpresaTest {
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
    void ListarInformacionEmpresaTest_ListadoExitoso() {

        Long idEmpresa = 1L;

        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setIdEstudiante(1L);
        empresa.setNombre("GSE");
        empresa.setUbicacion("Bogota");
        empresa.setCargo("Desarrollador Junior");
        empresa.setJefeDirecto("Juan M.");
        empresa.setTelefono("31674832734");
        empresa.setCorreo("julio@gse.com.co");
        empresa.setEstado("Activo");

        when(empresaRepository.findById(idEmpresa)).thenReturn(Optional.of(empresa));

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

        EmpresaResponseDto resultado = empresaServiceImpl.buscarPorId(idEmpresa);

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
    void ListarInformacionEmpresaTest_EmpresaNoExiste() {
        Long idEmpresa = 2L;

        when(empresaRepository.findById(idEmpresa)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            empresaServiceImpl.buscarPorId(idEmpresa);
        });

        assertNotNull(exception.getMessage());
        String expectedMessage = "Empresa con id 2 no encontrada";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}
