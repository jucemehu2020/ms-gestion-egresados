package com.unicauca.maestria.api.gestionegresados.msgestionegresados.Estudiante.Cursos;

import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursosResponseDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.unicauca.maestria.api.gestionegresados.common.client.ArchivoClientAsignaturas;
import com.unicauca.maestria.api.gestionegresados.domain.Curso;
import com.unicauca.maestria.api.gestionegresados.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.gestionegresados.mappers.CursoMapper;
import com.unicauca.maestria.api.gestionegresados.mappers.CursoResponseMapper;
import com.unicauca.maestria.api.gestionegresados.repositories.CursoRepository;
import com.unicauca.maestria.api.gestionegresados.services.curso.CursoServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ListarInformacionCursoTest {
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
    @Mock
    private BindingResult result;
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
    void ListarInformacionCursoTest_ListarExitoso() {

        Long idCurso = 1L;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("Proyecto I");
        curso.setOrientadoA("Pre-grado");
        curso.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        curso.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));

        CursosResponseDto cursosResponseDto = new CursosResponseDto();
        cursosResponseDto.setId(curso.getId());
        cursosResponseDto.setNombre(curso.getNombre());
        cursosResponseDto.setOrientadoA(curso.getOrientadoA());
        cursosResponseDto.setFechaInicio(curso.getFechaInicio());
        cursosResponseDto.setFechaFin(curso.getFechaFin());

        when(cursoResponseMapper.toDto(curso)).thenReturn(cursosResponseDto);

        CursosResponseDto resultado = cursoServiceImpl.obtenerInformacionCurso(idCurso);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Proyecto I", resultado.getNombre());
        assertEquals("Pre-grado", resultado.getOrientadoA());
        assertEquals(LocalDate.parse("2018-02-01", formatter), resultado.getFechaInicio());
        assertEquals(LocalDate.parse("2018-06-01", formatter), resultado.getFechaFin());
    }

    @Test
    void ListarInformacionCursoTest_CursoNoExiste() {
        Long idCurso = 2L;

        when(cursoRepository.findById(idCurso)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cursoServiceImpl.obtenerInformacionCurso(idCurso);
        });

        assertNotNull(exception.getMessage());
        String expectedMessage = "Curso con id 2 no encontrado";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
