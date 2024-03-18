package com.unicauca.maestria.api.gestionegresados.domain.trabajo_grado;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "examen_valoracion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamenValoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExamenValoracion;

    private String titulo;

    private String linkFormatoA;

    private String linkFormatoD;

    private String linkFormatoE;

    private String evaluadorExterno;

    private String evaluadorInterno;

    private String actaAprobacionExamen;

    private LocalDate fechaActa;

    private String linkOficioDirigidoEvaluadores;

    private LocalDate fechaMaximaEvaluacion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_trabajo_grado")
    private TrabajoGrado idTrabajoGrado;
}
