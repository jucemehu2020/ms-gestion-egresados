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
public class ActualizarCursoTest {
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
    void ActualizarCursoTest_ActualizacionExitosa() {

        Long idCurso = 1L;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        CursoSaveDto cursoSaveDto = new CursoSaveDto();
        cursoSaveDto.setIdEstudiante(1L);
        cursoSaveDto.setIdCurso(1L);
        cursoSaveDto.setOrientadoA("Posgrado");
        cursoSaveDto.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        cursoSaveDto.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        when(result.hasErrors()).thenReturn(false);

        EstudianteResponseDto estudianteResponseDto = new EstudianteResponseDto();
        estudianteResponseDto.setId(1L);

        when(archivoClient.obtenerPorIdEstudiante(cursoSaveDto.getIdEstudiante())).thenReturn(estudianteResponseDto);

        Curso cursoOld = new Curso();
        cursoOld.setId(1L);
        cursoOld.setIdEstudiante(1L);
        cursoOld.setOrientadoA("Posgrado");
        cursoOld.setNombre("Proyecto 1");
        cursoOld.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        cursoOld.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(cursoOld));

        ListadoAsignaturasDto listadoAsignaturasDto = new ListadoAsignaturasDto();
        listadoAsignaturasDto.setNombreAsignatura("Proyecto I");

        when(archivoClientAsignaturas.listarAsignaturaPorId(cursoSaveDto.getIdCurso()))
                .thenReturn(listadoAsignaturasDto);

        Curso cursoNew = new Curso();
        cursoNew.setId(1L);
        cursoNew.setNombre(listadoAsignaturasDto.getNombreAsignatura());
        cursoNew.setIdEstudiante(cursoSaveDto.getIdEstudiante());
        cursoNew.setOrientadoA(cursoSaveDto.getOrientadoA());
        cursoNew.setFechaInicio(cursoSaveDto.getFechaInicio());
        cursoNew.setFechaFin(cursoSaveDto.getFechaFin());

        when(cursoRepository.save(cursoNew)).thenReturn(cursoNew);

        CursosResponseDto cursosResponseDto = new CursosResponseDto();
        cursosResponseDto.setId(cursoNew.getId());
        cursosResponseDto.setNombre(cursoNew.getNombre());
        cursosResponseDto.setOrientadoA(cursoNew.getOrientadoA());
        cursosResponseDto.setFechaInicio(cursoNew.getFechaInicio());
        cursosResponseDto.setFechaFin(cursoNew.getFechaFin());

        when(cursoResponseMapper.toDto(cursoNew)).thenReturn(cursosResponseDto);

        CursosResponseDto resultado = cursoServiceImpl.actualizar(idCurso, cursoSaveDto, result);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Proyecto I", resultado.getNombre());
        assertEquals("Posgrado", resultado.getOrientadoA());
        assertEquals(LocalDate.parse("2018-02-01", formatter), resultado.getFechaInicio());
        assertEquals(LocalDate.parse("2018-06-01", formatter), resultado.getFechaFin());

    }

    @Test
    void ActualizarCursoTest_FaltanAtributos() {
        Long idCurso = 1L;

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
            cursoServiceImpl.actualizar(idCurso, cursoSaveDto, result);
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
    void ActualizarCursoTest_EstudianteNoExiste() {
        Long idCurso = 1L;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        CursoSaveDto cursoSaveDto = new CursoSaveDto();
        cursoSaveDto.setIdEstudiante(4L);
        cursoSaveDto.setIdCurso(1L);
        cursoSaveDto.setOrientadoA("Postgrado");
        cursoSaveDto.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        cursoSaveDto.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        when(result.hasErrors()).thenReturn(false);

        when(archivoClient.obtenerPorIdEstudiante(cursoSaveDto.getIdEstudiante()))
                .thenThrow(new ResourceNotFoundException("Estudiantes con id "
                        + cursoSaveDto.getIdEstudiante() + " no encontrado"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cursoServiceImpl.actualizar(idCurso, cursoSaveDto, result);
        });

        assertNotNull(exception.getMessage());
        String expectedMessage = "Estudiantes con id 4 no encontrado";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void ActualizarCursoTest_CursoNoExiste() {
        Long idCurso = 1L;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        CursoSaveDto cursoSaveDto = new CursoSaveDto();
        cursoSaveDto.setIdEstudiante(1L);
        cursoSaveDto.setIdCurso(2L);
        cursoSaveDto.setOrientadoA("Postgrado");
        cursoSaveDto.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        cursoSaveDto.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        when(result.hasErrors()).thenReturn(false);

        EstudianteResponseDto estudianteResponseDto = new EstudianteResponseDto();
        estudianteResponseDto.setId(1L);

        when(archivoClient.obtenerPorIdEstudiante(cursoSaveDto.getIdEstudiante())).thenReturn(estudianteResponseDto);

        Curso cursoOld = new Curso();
        cursoOld.setId(1L);
        cursoOld.setIdEstudiante(1L);
        cursoOld.setOrientadoA("Posgrado");
        cursoOld.setNombre("Proyecto 1");
        cursoOld.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        cursoOld.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(cursoOld));

        when(archivoClientAsignaturas.listarAsignaturaPorId(cursoSaveDto.getIdCurso()))
                .thenThrow(new ResourceNotFoundException("Cursos con id "
                        + cursoSaveDto.getIdCurso() + " no encontrado"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cursoServiceImpl.actualizar(idCurso, cursoSaveDto, result);
        });

        assertNotNull(exception.getMessage());
        String expectedMessage = "Cursos con id 2 no encontrado";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void ActualizarCursoTest_ServidorEstudianteCaido() {
        Long idCurso = 1L;

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
                () -> cursoServiceImpl.actualizar(idCurso, cursoSaveDto, result),
                "Servidor externo actualmente fuera de servicio");

        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("Servidor externo actualmente fuera de servicio"));
    }

    @Test
    void ActualizarCursoTest_ServidorCursosCaido() {
        Long idCurso = 1L;

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

        Curso cursoOld = new Curso();
        cursoOld.setId(1L);
        cursoOld.setIdEstudiante(1L);
        cursoOld.setOrientadoA("Posgrado");
        cursoOld.setNombre("Proyecto 1");
        cursoOld.setFechaInicio(LocalDate.parse("2018-02-01", formatter));
        cursoOld.setFechaFin(LocalDate.parse("2018-06-01", formatter));

        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(cursoOld));

        when(archivoClientAsignaturas.listarAsignaturaPorId(cursoSaveDto.getIdCurso()))
                .thenThrow(new ServiceUnavailableException(
                        "Servidor externo actualmente fuera de servicio"));

        ServiceUnavailableException thrown = assertThrows(
                ServiceUnavailableException.class,
                () -> cursoServiceImpl.actualizar(idCurso, cursoSaveDto, result),
                "Servidor externo actualmente fuera de servicio");

        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("Servidor externo actualmente fuera de servicio"));
    }

}
