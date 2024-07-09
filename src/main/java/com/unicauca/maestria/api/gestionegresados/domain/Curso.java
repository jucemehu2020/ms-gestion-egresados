package com.unicauca.maestria.api.gestionegresados.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "cursos_dictados")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String orientadoA;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @Column(name = "id_estudiante")
    private Long idEstudiante;

}
