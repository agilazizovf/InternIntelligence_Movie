package com.intern.movie.service;

import com.intern.movie.entity.UserEntity;
import com.intern.movie.model.dto.request.MovieRequest;
import com.intern.movie.model.dto.response.MovieResponse;
import com.intern.movie.model.dto.response.PageResponse;
import org.springframework.http.ResponseEntity;

public interface MovieService {

    ResponseEntity<MovieResponse> create(MovieRequest request);
    PageResponse<MovieResponse> findAllMovies(int page, int size);
    ResponseEntity<MovieResponse> findMovieById(Long id);
    ResponseEntity<MovieResponse> update(Long id, MovieRequest request);
    void delete(Long id);
    UserEntity getCurrentUser();
}
