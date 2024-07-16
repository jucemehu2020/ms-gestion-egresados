package com.unicauca.maestria.api.gestionegresados.msgestionegresados.Coordinador.InformacionEstudiantes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.unicauca.maestria.api.gestionegresados.common.client.ArchivoClient;
import com.unicauca.maestria.api.gestionegresados.domain.embeddables.InformacionMaestriaActual;
import com.unicauca.maestria.api.gestionegresados.dtos.EstudianteResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.InformacionGeneralResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.common.PersonaDto;
import com.unicauca.maestria.api.gestionegresados.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.gestionegresados.exceptions.ServiceUnavailableException;
import com.unicauca.maestria.api.gestionegresados.services.informacion_general.InformacionGeneralServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ListarInformacionEstudianteTest {

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
    void ListarInformacionEstudianteTest_ListadoExitoso() {

        Long idEstudiante = 1L;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        PersonaDto personaDto = new PersonaDto();
        personaDto.setId(1L);
        personaDto.setIdentificacion(123L);
        personaDto.setNombre("Julio");
        personaDto.setApellido("Mellizo");
        personaDto.setTelefono("316874954");
        personaDto.setCorreoElectronico("julioc10@gmail.com");

        InformacionMaestriaActual informacionMaestriaActual = new InformacionMaestriaActual();
        informacionMaestriaActual.setCohorte(2);
        informacionMaestriaActual.setPeriodoIngreso("2017-02");

        EstudianteResponseDto estudianteResponseDto = new EstudianteResponseDto();
        estudianteResponseDto.setId(1L);
        estudianteResponseDto.setCodigo("1829345");
        estudianteResponseDto.setFechaGrado(LocalDate.parse("2019-02-01", formatter));
        estudianteResponseDto.setPersona(personaDto);
        estudianteResponseDto.setInformacionMaestria(informacionMaestriaActual);

        when(archivoClient.obtenerPorIdEstudiante(idEstudiante)).thenReturn(estudianteResponseDto);

        InformacionGeneralResponseDto informacionGeneralResponseDtoEsperado = InformacionGeneralResponseDto.builder()
                .id(estudianteResponseDto.getId())
                .codigo(estudianteResponseDto.getCodigo())
                .identificacion(estudianteResponseDto.getPersona().getIdentificacion())
                .nombre(estudianteResponseDto.getPersona().getNombre())
                .apellido(estudianteResponseDto.getPersona().getApellido())
                .cohorte(estudianteResponseDto.getInformacionMaestria().getCohorte())
                .periodoIngreso(estudianteResponseDto.getInformacionMaestria().getPeriodoIngreso())
                .fechaGrado(estudianteResponseDto.getFechaGrado())
                .telefono(estudianteResponseDto.getPersona().getTelefono())
                .correo(estudianteResponseDto.getPersona().getCorreoElectronico())
                .build();

        InformacionGeneralResponseDto resultado = informacionGeneralServiceImpl.buscarEstudiante(idEstudiante);

        assertNotNull(resultado);
        assertEquals(informacionGeneralResponseDtoEsperado, resultado);
    }

    @Test
    void ListarInformacionEstudianteTest_EstudianteNoExiste() {
        Long idEstudiante = 4L;

        when(archivoClient.obtenerPorIdEstudiante(idEstudiante))
                .thenThrow(new ResourceNotFoundException("Estudiantes con id "
                        + idEstudiante + " no encontrado"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            informacionGeneralServiceImpl.buscarEstudiante(idEstudiante);
        });

        assertNotNull(exception.getMessage());
        String expectedMessage = "Estudiantes con id 4 no encontrado";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void ListarInformacionEstudianteTest_ServidorCaido() {
        Long idEstudiante = 1L;

        when(archivoClient.obtenerPorIdEstudiante(idEstudiante)).thenThrow(new ServiceUnavailableException(
                "Servidor externo actualmente fuera de servicio"));

        ServiceUnavailableException thrown = assertThrows(
                ServiceUnavailableException.class,
                () -> informacionGeneralServiceImpl.buscarEstudiante(idEstudiante),
                "Servidor externo actualmente fuera de servicio");

        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("Servidor externo actualmente fuera de servicio"));
    }

}
