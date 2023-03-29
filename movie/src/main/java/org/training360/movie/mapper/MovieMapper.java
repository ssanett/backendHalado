package org.training360.movie.mapper;

import org.mapstruct.Mapper;
import org.training360.movie.dtos.MovieDto;
import org.training360.movie.model.Movie;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDto toDto(Movie movie);

    List<MovieDto> toDto(List<Movie> movies);
}
