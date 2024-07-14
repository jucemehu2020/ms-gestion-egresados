package com.unicauca.maestria.api.gestionegresados.msgestionegresados.Cursos.Estudiante;

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

import com.unicauca.maestria.api.gestionegresados.common.client.ArchivoClient;
import com.unicauca.maestria.api.gestionegresados.common.client.ArchivoClientAsignaturas;
import com.unicauca.maestria.api.gestionegresados.dtos.ListadoAsignaturasDto;
import com.unicauca.maestria.api.gestionegresados.exceptions.InformationException;
import com.unicauca.maestria.api.gestionegresados.exceptions.ServiceUnavailableException;
import com.unicauca.maestria.api.gestionegresados.mappers.CursoMapper;
import com.unicauca.maestria.api.gestionegresados.mappers.CursoResponseMapper;
import com.unicauca.maestria.api.gestionegresados.repositories.CursoRepository;
import com.unicauca.maestria.api.gestionegresados.services.curso.CursoServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ListarCursosTest {
    @Mock
    private CursoRepository cursoRepository;
    @Mock
    private CursoMapper cursoMapper;
    @Mock
    private CursoResponseMapper cursoResponseMapper;
    @Mock
    private ArchivoClient archivoClient;
    @Mock
    private ArchivoClientAsignaturas archivoClientAsignaturas;
    @InjectMocks
    private CursoServiceImpl cursoServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cursoServiceImpl = new CursoServiceImpl(
                cursoRepository,
                cursoMapper,
                cursoResponseMapper,
                archivoClient,
                archivoClientAsignaturas);
    }

    @Test
    void ListarCursosTest_ListadoExitoso() {
        ListadoAsignaturasDto listadoAsignaturasDto1 = new ListadoAsignaturasDto();
        listadoAsignaturasDto1.setIdAsignatura(1L);
        listadoAsignaturasDto1.setNombreAsignatura("Ingenieria requisitos");

        ListadoAsignaturasDto listadoAsignaturasDto2 = new ListadoAsignaturasDto();
        listadoAsignaturasDto2.setIdAsignatura(2L);
        listadoAsignaturasDto2.setNombreAsignatura("Proyecto I");

        ListadoAsignaturasDto listadoAsignaturasDto3 = new ListadoAsignaturasDto();
        listadoAsignaturasDto3.setIdAsignatura(3L);
        listadoAsignaturasDto3.setNombreAsignatura("Metodologia investigacion");

        List<ListadoAsignaturasDto> listaCursos = new ArrayList<>();
        listaCursos.add(listadoAsignaturasDto1);
        listaCursos.add(listadoAsignaturasDto2);
        listaCursos.add(listadoAsignaturasDto3);

        when(archivoClientAsignaturas.listarAsignaturas()).thenReturn(listaCursos);

        List<ListadoAsignaturasDto> resultado = cursoServiceImpl.listarCursosExistentes();

        assertNotNull(resultado);
        assertEquals(listadoAsignaturasDto1, resultado.get(0));
        assertEquals(listadoAsignaturasDto2, resultado.get(1));
        assertEquals(listadoAsignaturasDto3, resultado.get(2));

    }

    @Test
    void ListarCursosTest_NoHayCursos() {
        when(archivoClientAsignaturas.listarAsignaturas()).thenReturn(new ArrayList<>());

        InformationException exception = assertThrows(InformationException.class, () -> {
            cursoServiceImpl.listarCursosExistentes();
        });

        assertNotNull(exception.getMessage());
        String expectedMessage = "No hay cursos registrados";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void ListarCursosTest_ServidorCursosCaido() {
        when(archivoClientAsignaturas.listarAsignaturas()).thenThrow(new ServiceUnavailableException(
                "Servidor externo actualmente fuera de servicio"));

        ServiceUnavailableException thrown = assertThrows(
                ServiceUnavailableException.class,
                () -> cursoServiceImpl.listarCursosExistentes(),
                "Servidor externo actualmente fuera de servicio");

        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("Servidor externo actualmente fuera de servicio"));
    }
}
