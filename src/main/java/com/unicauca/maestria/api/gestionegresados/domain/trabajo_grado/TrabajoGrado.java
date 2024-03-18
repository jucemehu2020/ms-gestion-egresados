package com.unicauca.maestria.api.gestionegresados.domain.trabajo_grado;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.unicauca.maestria.api.gestionegresados.domain.estudiante.Estudiante;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trabajo_grado")
public class TrabajoGrado {

    @Id
    @Column(name = "id_trabajo_grado")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    private String estado;

    private LocalDate fechaCreacion;

    private String numeroEstado;

    @OneToOne(mappedBy = "idTrabajoGrado", cascade = CascadeType.ALL)
    private ExamenValoracion examenValoracion;

    @OneToOne(mappedBy = "idTrabajoGrado", cascade = CascadeType.ALL)
    private RtaExamenValoracion idRtaExamenValoracion;

    @OneToOne(mappedBy = "idTrabajoGrado", cascade = CascadeType.ALL)
    private GeneracionResolucionE idGeneracionResolucion;

    @OneToOne(mappedBy = "idTrabajoGrado", cascade = CascadeType.ALL)
    private SustentacionTI idSustentacionProyectoInvestigacion;

}
