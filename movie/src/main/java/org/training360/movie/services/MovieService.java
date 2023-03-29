package org.training360.movie.services;


import org.springframework.stereotype.Service;
import org.training360.movie.dtos.CreateMovieCommand;
import org.training360.movie.dtos.MovieDto;
import org.training360.movie.dtos.UpdateRatingCommand;
import org.training360.movie.mapper.MovieMapper;
import org.training360.movie.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MovieService {

   private MovieMapper movieMapper;
    private AtomicLong id = new AtomicLong(0);
    private List<Movie> movies = new ArrayList<>(); //miért nem movieDto-kat tárolunk

    public MovieService(MovieMapper movieMapper) {
        this.movieMapper = movieMapper;
    }


    public List<MovieDto> getAllMovies() {
        return movieMapper.toDto(movies);
    }

    public MovieDto findMovieById(long id) {
        Movie movie = findById(id);
        return movieMapper.toDto(movie);
    }

    private Movie findById(long id) {
        return movies.stream()
                .filter(m->m.getId().equals(id))
                .findFirst().orElseThrow(()->new IllegalStateException("No movie has found with id" + id));
    }

    public List<Integer> getMoviesRating(long id) {
        Movie movie = findById(id);
        return movie.getRatings();
    }

    public List<Integer> addMovieRating(long id, UpdateRatingCommand command) {
        Movie movie = findById(id);
        movie.addRating(command.getRating());
        return movie.getRatings();
    }

    public MovieDto createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(command.getTitle(),command.getLength());
        movie.setId(id.incrementAndGet());
        movies.add(movie);
        return movieMapper.toDto(movie);
    }
}
