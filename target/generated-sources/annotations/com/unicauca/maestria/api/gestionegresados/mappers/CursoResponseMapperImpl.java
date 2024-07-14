package com.unicauca.maestria.api.gestionegresados.mappers;

import com.unicauca.maestria.api.gestionegresados.domain.Curso;
import com.unicauca.maestria.api.gestionegresados.domain.Curso.CursoBuilder;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursosResponseDto;
import com.unicauca.maestria.api.gestionegresados.dtos.curso.CursosResponseDto.CursosResponseDtoBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-14T03:09:58-0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class CursoResponseMapperImpl implements CursoResponseMapper {

    @Override
    public Curso toEntity(CursosResponseDto dto) {
        if ( dto == null ) {
            return null;
        }

        CursoBuilder curso = Curso.builder();

        curso.id( dto.getId() );
        curso.nombre( dto.getNombre() );
        curso.orientadoA( dto.getOrientadoA() );
        curso.fechaInicio( dto.getFechaInicio() );
        curso.fechaFin( dto.getFechaFin() );

        return curso.build();
    }

    @Override
    public CursosResponseDto toDto(Curso entity) {
        if ( entity == null ) {
            return null;
        }

        CursosResponseDtoBuilder cursosResponseDto = CursosResponseDto.builder();

        cursosResponseDto.id( entity.getId() );
        cursosResponseDto.nombre( entity.getNombre() );
        cursosResponseDto.orientadoA( entity.getOrientadoA() );
        cursosResponseDto.fechaInicio( entity.getFechaInicio() );
        cursosResponseDto.fechaFin( entity.getFechaFin() );

        return cursosResponseDto.build();
    }

    @Override
    public List<Curso> toEntityList(List<CursosResponseDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Curso> list = new ArrayList<Curso>( dtos.size() );
        for ( CursosResponseDto cursosResponseDto : dtos ) {
            list.add( toEntity( cursosResponseDto ) );
        }

        return list;
    }

    @Override
    public List<CursosResponseDto> toDtoList(List<Curso> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CursosResponseDto> list = new ArrayList<CursosResponseDto>( entities.size() );
        for ( Curso curso : entities ) {
            list.add( toDto( curso ) );
        }

        return list;
    }
}
