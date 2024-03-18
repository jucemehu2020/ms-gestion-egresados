package com.unicauca.maestria.api.gestionegresados.domain.trabajo_grado;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rta_examen_valoracion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RtaExamenValoracion {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRtaExamenValoracion;

    private String linkFormatoB;

    private String linkFormatoC;

    private String observaciones;

    private String respuestaExamenValoracion;

    private LocalDate fechaMaxmiaEntrega;

    private Boolean estadoFinalizado;

    private String observacion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_trabajo_grado")
    private TrabajoGrado idTrabajoGrado;

}
