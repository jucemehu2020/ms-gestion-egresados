package com.unicauca.maestria.api.gestionegresados.msgestionegresados.Estudiante.Cursos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.unicauca.maestria.api.gestionegresados.common.client.ArchivoClientAsignaturas;
import com.unicauca.maestria.api.gestionegresados.domain.Curso;
import com.unicauca.maestria.api.gestionegresados.dtos.EstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.ListadoAsignaturasDto;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursoSaveDto;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursosResponseDto;
import com.unicauca.maestria.api.gestionegresados.exceptions.FieldErrorException;
import com.unicauca.maestria.api.gestionegresados.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.gestionegresados.exceptions.ServiceUnavailableException;
import com.unicauca.maestria.api.gestionegresados.mappers.CursoMapper;
import com.unicauca.maestria.api.gestionegresados.mappers.CursoResponseMapper;
import com.unicauca.maestria.api.gestionegresados.repositories.CursoRepository;
import com.unicauca.maestria.api.gestionegresados.services.curso.CursoServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RegistrarCursoTest {
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
    void RegistrarCursoTest_RegistroExitoso() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        CursoSaveDto cursoSaveDto = new CursoSaveDto();
        cursoSaveDto.setIdEstudiante(1L);
        cursoSaveDto.setIdCurso(1L);
        cursoSaveDto.setOrientadoA("Pre-grado");
        cursoSaveDto.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        cursoSaveDto.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        when(result.hasErrors()).thenReturn(false);

        EstudianteResponseDto estudianteResponseDto = new EstudianteResponseDto();
        estudianteResponseDto.setId(1L);

        when(archivoClient.obtenerPorIdEstudiante(cursoSaveDto.getIdEstudiante())).thenReturn(estudianteResponseDto);

        ListadoAsignaturasDto listadoAsignaturasDto = new ListadoAsignaturasDto();
        listadoAsignaturasDto.setNombreAsignatura("Proyecto I");

        when(archivoClientAsignaturas.listarAsignaturaPorId(cursoSaveDto.getIdCurso()))
                .thenReturn(listadoAsignaturasDto);

        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre(listadoAsignaturasDto.getNombreAsignatura());
        curso.setIdEstudiante(cursoSaveDto.getIdEstudiante());
        curso.setOrientadoA(cursoSaveDto.getOrientadoA());
        curso.setFechaInicio(cursoSaveDto.getFechaInicio());
        curso.setFechaFin(cursoSaveDto.getFechaFin());

        when(cursoMapper.toEntity(cursoSaveDto)).thenReturn(curso);

        when(cursoRepository.save(curso)).thenReturn(curso);

        CursosResponseDto cursosResponseDto = new CursosResponseDto();
        cursosResponseDto.setId(curso.getId());
        cursosResponseDto.setNombre(curso.getNombre());
        cursosResponseDto.setOrientadoA(curso.getOrientadoA());
        cursosResponseDto.setFechaInicio(curso.getFechaInicio());
        cursosResponseDto.setFechaFin(curso.getFechaFin());

        when(cursoResponseMapper.toDto(curso)).thenReturn(cursosResponseDto);

        CursosResponseDto resultado = cursoServiceImpl.crear(cursoSaveDto, result);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Proyecto I", resultado.getNombre());
        assertEquals("Pre-grado", resultado.getOrientadoA());
        assertEquals(LocalDate.parse("2018-02-01", formatter), resultado.getFechaInicio());
        assertEquals(LocalDate.parse("2018-06-01", formatter), resultado.getFechaFin());

    }

    @Test
    void RegistrarCursoTest_FaltanAtributos() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        CursoSaveDto cursoSaveDto = new CursoSaveDto();
        cursoSaveDto.setIdEstudiante(1L);
        cursoSaveDto.setIdCurso(1L);
        cursoSaveDto.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        cursoSaveDto.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        FieldError fieldError = new FieldError("CursoServiceImpl", "orientadoA",
                "no debe ser nulo");
        when(result.hasErrors()).thenReturn(true);
        when(result.getFieldErrors()).thenReturn(List.of(fieldError));

        FieldErrorException exception = assertThrows(FieldErrorException.class, () -> {
            cursoServiceImpl.crear(cursoSaveDto, result);
        });

        assertNotNull(exception.getResult());
        List<FieldError> fieldErrors = exception.getResult().getFieldErrors();
        assertFalse(fieldErrors.isEmpty());
        String actualMessage = "El campo: " + fieldErrors.get(0).getField() + ", "
                + fieldError.getDefaultMessage();
        String expectedMessage = "El campo: orientadoA, no debe ser nulo";
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void RegistrarCursoTest_EstudianteNoExiste() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        CursoSaveDto cursoSaveDto = new CursoSaveDto();
        cursoSaveDto.setIdEstudiante(4L);
        cursoSaveDto.setIdCurso(1L);
        cursoSaveDto.setOrientadoA("Pre-grado");
        cursoSaveDto.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        cursoSaveDto.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        when(result.hasErrors()).thenReturn(false);

        when(archivoClient.obtenerPorIdEstudiante(cursoSaveDto.getIdEstudiante()))
                .thenThrow(new ResourceNotFoundException("Estudiantes con id "
                        + cursoSaveDto.getIdEstudiante() + " no encontrado"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cursoServiceImpl.crear(cursoSaveDto, result);
        });

        assertNotNull(exception.getMessage());
        String expectedMessage = "Estudiantes con id 4 no encontrado";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void RegistrarCursoTest_CursoNoExiste() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        CursoSaveDto cursoSaveDto = new CursoSaveDto();
        cursoSaveDto.setIdEstudiante(1L);
        cursoSaveDto.setIdCurso(2L);
        cursoSaveDto.setOrientadoA("Pre-grado");
        cursoSaveDto.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        cursoSaveDto.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        when(result.hasErrors()).thenReturn(false);

        EstudianteResponseDto estudianteResponseDto = new EstudianteResponseDto();
        estudianteResponseDto.setId(1L);

        when(archivoClient.obtenerPorIdEstudiante(cursoSaveDto.getIdEstudiante())).thenReturn(estudianteResponseDto);

        when(archivoClientAsignaturas.listarAsignaturaPorId(cursoSaveDto.getIdCurso()))
                .thenThrow(new ResourceNotFoundException("Cursos con id "
                        + cursoSaveDto.getIdCurso() + " no encontrado"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cursoServiceImpl.crear(cursoSaveDto, result);
        });

        assertNotNull(exception.getMessage());
        String expectedMessage = "Cursos con id 2 no encontrado";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void RegistrarCursoTest_ServidorEstudianteCaido() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        CursoSaveDto cursoSaveDto = new CursoSaveDto();
        cursoSaveDto.setIdEstudiante(1L);
        cursoSaveDto.setIdCurso(2L);
        cursoSaveDto.setOrientadoA("Pre-grado");
        cursoSaveDto.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        cursoSaveDto.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        when(result.hasErrors()).thenReturn(false);

        when(archivoClient.obtenerPorIdEstudiante(cursoSaveDto.getIdEstudiante()))
                .thenThrow(new ServiceUnavailableException(
                        "Servidor externo actualmente fuera de servicio"));

        ServiceUnavailableException thrown = assertThrows(
                ServiceUnavailableException.class,
                () -> cursoServiceImpl.crear(cursoSaveDto, result),
                "Servidor externo actualmente fuera de servicio");

        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("Servidor externo actualmente fuera de servicio"));
    }

    @Test
    void RegistrarCursoTest_ServidorCursosCaido() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        CursoSaveDto cursoSaveDto = new CursoSaveDto();
        cursoSaveDto.setIdEstudiante(1L);
        cursoSaveDto.setIdCurso(2L);
        cursoSaveDto.setOrientadoA("Pre-grado");
        cursoSaveDto.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        cursoSaveDto.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        when(result.hasErrors()).thenReturn(false);

        EstudianteResponseDto estudianteResponseDto = new EstudianteResponseDto();
        estudianteResponseDto.setId(1L);

        when(archivoClient.obtenerPorIdEstudiante(cursoSaveDto.getIdEstudiante())).thenReturn(estudianteResponseDto);

        when(archivoClientAsignaturas.listarAsignaturaPorId(cursoSaveDto.getIdCurso()))
                .thenThrow(new ServiceUnavailableException(
                        "Servidor externo actualmente fuera de servicio"));

        ServiceUnavailableException thrown = assertThrows(
                ServiceUnavailableException.class,
                () -> cursoServiceImpl.crear(cursoSaveDto, result),
                "Servidor externo actualmente fuera de servicio");

        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("Servidor externo actualmente fuera de servicio"));
    }

}
