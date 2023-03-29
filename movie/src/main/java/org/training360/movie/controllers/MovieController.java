package org.training360.movie.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.training360.movie.dtos.CreateMovieCommand;
import org.training360.movie.dtos.MovieDto;
import org.training360.movie.dtos.UpdateRatingCommand;
import org.training360.movie.services.MovieService;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
public class MovieController {

    private MovieService movieService;

    @GetMapping
    public List<MovieDto> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public MovieDto findMovieById(@PathVariable("id") long id) {
        return movieService.findMovieById(id);
    }

    @GetMapping("/{id}/ratings")
    public List<Integer> findMovieRatings(@PathVariable ("id") long id){
        return movieService.getMoviesRating(id);
    }

    @PostMapping
    public MovieDto createMovie(@RequestBody CreateMovieCommand command){
        return movieService.createMovie(command);
    }

    @PostMapping("/{id}/ratings")
    public List<Integer> addMovieRating(@PathVariable ("id") long id, @RequestBody UpdateRatingCommand command){
        return movieService.addMovieRating(id,command);
    }


}
