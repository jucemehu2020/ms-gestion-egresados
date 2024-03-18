package com.unicauca.maestria.api.gestionegresados.domain.trabajo_grado;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sustentacion_proyecto_investigacion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SustentacionTI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSustentacionTI;

    private String linkRemisionDocumentoFinal;

    private String urlDocumentacion;

    // CF: Concejo de facultad
    private String linkRemisionDocumentoFinalCF;

    private String linkConstanciaDocumentoFinal;

    private String linkActaSustentacion;

    private String linkActaSustentacionPublica;

    private Boolean respuestaSustentacion;

    private String linkEstudioHojaVidaAcademica;

    private String numeroActaTrabajoFinal;

    private LocalDate fechaActa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_trabajo_grado")
    private TrabajoGrado idTrabajoGrado;
}
