package com.intern.movie.controller;

import com.intern.movie.model.dto.request.MovieRequest;
import com.intern.movie.model.dto.response.MovieResponse;
import com.intern.movie.model.dto.response.PageResponse;
import com.intern.movie.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/create")
    public ResponseEntity<MovieResponse> create(@RequestBody @Valid MovieRequest request) {
        return movieService.create(request);
    }
    @GetMapping("/get-all")
    public PageResponse<MovieResponse> findAllMovies(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return movieService.findAllMovies(page, size);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findMovieById(@PathVariable Long id) {
        return movieService.findMovieById(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<MovieResponse> update(@PathVariable Long id, @RequestBody @Valid MovieRequest request) {
        return movieService.update(id, request);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        movieService.delete(id);
    }
}
