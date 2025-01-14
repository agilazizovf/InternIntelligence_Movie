package com.intern.movie.mapper;

import com.intern.movie.entity.MovieEntity;
import com.intern.movie.model.dto.response.MovieResponse;

public class MovieMapper {

    public static MovieResponse toMovieDTO(MovieEntity movie) {
        MovieResponse response = new MovieResponse();
        response.setId(movie.getId());
        response.setTitle(movie.getTitle());
        response.setDirector(movie.getDirector());
        response.setGenre(movie.getGenre());
        response.setIMDb(movie.getIMDb());
        response.setReleaseYear(movie.getReleaseYear());
        return response;
    }
}
