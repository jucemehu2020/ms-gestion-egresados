package com.unicauca.maestria.api.gestionegresados.msgestionegresados.Coordinador.Cursos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.unicauca.maestria.api.gestionegresados.domain.Curso;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursosResponseDto;
import com.unicauca.maestria.api.gestionegresados.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.gestionegresados.mappers.CursoMapper;
import com.unicauca.maestria.api.gestionegresados.mappers.CursoResponseMapper;
import com.unicauca.maestria.api.gestionegresados.repositories.CursoRepository;
import com.unicauca.maestria.api.gestionegresados.services.curso.CursoServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ListarCursosEstudianteTest {
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
    void ListarCursosDictadosTest_ListadoExitoso() {

        Long idEstudiante = 1L;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("Proyecto I");
        curso.setOrientadoA("Pre-grado");
        curso.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        curso.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        Curso curso2 = new Curso();
        curso2.setId(1L);
        curso2.setNombre("Ingenieria de requisitos");
        curso2.setOrientadoA("Pre-grado");
        curso2.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        curso2.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        List<Curso> cursos = new ArrayList<>();
        cursos.add(curso);
        cursos.add(curso2);

        when(cursoRepository.findByEstudianteId(idEstudiante)).thenReturn(cursos);

        CursosResponseDto cursosResponseDto = new CursosResponseDto();
        cursosResponseDto.setId(curso.getId());
        cursosResponseDto.setNombre(curso.getNombre());
        cursosResponseDto.setOrientadoA(curso.getOrientadoA());
        cursosResponseDto.setFechaInicio(curso.getFechaInicio());
        cursosResponseDto.setFechaFin(curso.getFechaFin());

        CursosResponseDto cursosResponseDto2 = new CursosResponseDto();
        cursosResponseDto2.setId(curso2.getId());
        cursosResponseDto2.setNombre(curso2.getNombre());
        cursosResponseDto2.setOrientadoA(curso2.getOrientadoA());
        cursosResponseDto2.setFechaInicio(curso2.getFechaInicio());
        cursosResponseDto2.setFechaFin(curso2.getFechaFin());

        List<CursosResponseDto> listaRetorno = new ArrayList<>();
        listaRetorno.add(cursosResponseDto);
        listaRetorno.add(cursosResponseDto2);

        when(cursoResponseMapper.toDto(curso)).thenReturn(cursosResponseDto);
        when(cursoResponseMapper.toDto(curso2)).thenReturn(cursosResponseDto2);

        List<CursosResponseDto> resultado = cursoServiceImpl.listarCursosDictados(idEstudiante);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(cursosResponseDto, resultado.get(0));
        assertEquals(cursosResponseDto2, resultado.get(1));
    }

    @Test
    void ListarCursosDictadosTest_NoExisteEstudiante() {
        Long idEstudiante = 2L;

        when(archivoClient.obtenerPorIdEstudiante(idEstudiante))
                .thenThrow(new ResourceNotFoundException("Estudiantes con id " + idEstudiante + " no encontrado"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cursoServiceImpl.listarCursosDictados(idEstudiante);
        });

        assertNotNull(exception.getMessage());
        String expectedMessage = "Estudiantes con id 2 no encontrado";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}
