package com.unicauca.maestria.api.gestionegresados.msgestionegresados.Coordinador.InformacionEstudiantes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.unicauca.maestria.api.gestionegresados.common.enums.estudiante.TipoIdentificacion;
import com.unicauca.maestria.api.gestionegresados.dtos.EstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.InformacionEstudiantesResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.common.PersonaDto;
import com.unicauca.maestria.api.gestionegresados.exceptions.InformationException;
import com.unicauca.maestria.api.gestionegresados.exceptions.ServiceUnavailableException;
import com.unicauca.maestria.api.gestionegresados.services.informacion_general.InformacionGeneralServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ListarEstudiantesTest {

    @Mock
    private ArchivoClient archivoClient;
    @InjectMocks
    private InformacionGeneralServiceImpl informacionGeneralServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        informacionGeneralServiceImpl = new InformacionGeneralServiceImpl(
                archivoClient);
    }

    @Test
    void ListarEstudiantesTest_ListadoExitoso() {
        PersonaDto personaDto = new PersonaDto();
        personaDto.setId(1L);
        personaDto.setTipoIdentificacion(TipoIdentificacion.CEDULA_CIUDADANIA);
        personaDto.setIdentificacion(123L);
        personaDto.setNombre("Julio");
        personaDto.setApellido("Mellizo");

        PersonaDto personaDto2 = new PersonaDto();
        personaDto2.setId(2L);
        personaDto2.setTipoIdentificacion(TipoIdentificacion.CEDULA_CIUDADANIA);
        personaDto2.setIdentificacion(456L);
        personaDto2.setNombre("Luis");
        personaDto2.setApellido("Perez");

        EstudianteResponseDto estudianteResponseDto = new EstudianteResponseDto();
        estudianteResponseDto.setId(1L);
        estudianteResponseDto.setPersona(personaDto);

        EstudianteResponseDto estudianteResponseDto2 = new EstudianteResponseDto();
        estudianteResponseDto2.setId(2L);
        estudianteResponseDto2.setPersona(personaDto2);

        List<EstudianteResponseDto> listaEstudiantes = new ArrayList<>();
        listaEstudiantes.add(estudianteResponseDto);
        listaEstudiantes.add(estudianteResponseDto2);

        when(archivoClient.obtenerEstudiantes()).thenReturn(listaEstudiantes);

        InformacionEstudiantesResponseDto informacionEstudiante1 = new InformacionEstudiantesResponseDto(
                personaDto.getId(),
                personaDto.getTipoIdentificacion(),
                personaDto.getIdentificacion(),
                personaDto.getNombre(),
                personaDto.getApellido());

        InformacionEstudiantesResponseDto informacionEstudiante2 = new InformacionEstudiantesResponseDto(
                personaDto2.getId(),
                personaDto2.getTipoIdentificacion(),
                personaDto2.getIdentificacion(),
                personaDto2.getNombre(),
                personaDto2.getApellido());

        List<InformacionEstudiantesResponseDto> listaResEstudiantes = Arrays.asList(informacionEstudiante1,
                informacionEstudiante2);

        List<InformacionEstudiantesResponseDto> resultado = informacionGeneralServiceImpl.obtenerEstudiantes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(listaResEstudiantes, resultado);
    }

    @Test
    void ListarEstudiantesTest_NoExistenEstudiantes() {
        when(archivoClient.obtenerEstudiantes()).thenReturn(new ArrayList<>());

        InformationException exception = assertThrows(InformationException.class, () -> {
            informacionGeneralServiceImpl.obtenerEstudiantes();
        });

        assertNotNull(exception.getMessage());
        String expectedMessage = "No hay estudiantes registrados";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void ListarEstudiantesTest_ServidorCaido() {
        when(archivoClient.obtenerEstudiantes()).thenThrow(new ServiceUnavailableException(
                "Servidor externo actualmente fuera de servicio"));

        ServiceUnavailableException thrown = assertThrows(
                ServiceUnavailableException.class,
                () -> informacionGeneralServiceImpl.obtenerEstudiantes(),
                "Servidor externo actualmente fuera de servicio");

        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("Servidor externo actualmente fuera de servicio"));
    }
}
