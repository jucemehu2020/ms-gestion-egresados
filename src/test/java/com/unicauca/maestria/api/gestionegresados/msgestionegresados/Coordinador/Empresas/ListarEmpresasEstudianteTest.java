package com.unicauca.maestria.api.gestionegresados.msgestionegresados.Coordinador.Empresas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
public class ListarEmpresasEstudianteTest {
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
    void ListarEmpresasTest_ListadoExitoso() {

        Long idEstudiante = 1L;

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

        List<Empresa> listaEmpresa = new ArrayList<>();
        listaEmpresa.add(empresa);

        when(empresaRepository.findByEstudianteId(idEstudiante)).thenReturn(listaEmpresa);

        EmpresaResponseDto empresaResponseDto = new EmpresaResponseDto();
        empresaResponseDto.setId(empresa.getId());
        empresaResponseDto.setNombre(empresa.getNombre());
        empresaResponseDto.setUbicacion(empresa.getUbicacion());
        empresaResponseDto.setCargo(empresa.getCargo());
        empresaResponseDto.setJefeDirecto(empresa.getJefeDirecto());
        empresaResponseDto.setTelefono(empresa.getTelefono());
        empresaResponseDto.setCorreo(empresa.getCorreo());
        empresaResponseDto.setEstado(empresa.getEstado());

        List<EmpresaResponseDto> listaRetorno = new ArrayList<>();
        listaRetorno.add(empresaResponseDto);

        when(empresaResponseMapper.toDto(empresa)).thenReturn(empresaResponseDto);

        List<EmpresaResponseDto> resultado = empresaServiceImpl.listarEmpresasEstudiante(idEstudiante);

        assertNotNull(resultado);
        assertEquals(listaRetorno, resultado);
    }

    @Test
    void ListarEmpresasTest_NoExisteEstudiante() {
        Long idEstudiante = 2L;

        when(archivoClient.obtenerPorIdEstudiante(idEstudiante))
                .thenThrow(new ResourceNotFoundException("Estudiantes con id " + idEstudiante + " no encontrado"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            empresaServiceImpl.listarEmpresasEstudiante(idEstudiante);
        });

        assertNotNull(exception.getMessage());
        String expectedMessage = "Estudiantes con id 2 no encontrado";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}
