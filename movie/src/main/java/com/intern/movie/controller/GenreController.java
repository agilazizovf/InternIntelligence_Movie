package com.intern.movie.controller;

import com.intern.movie.model.dto.request.GenreRequest;
import com.intern.movie.model.dto.response.GenreResponse;
import com.intern.movie.model.dto.response.PageResponse;
import com.intern.movie.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/create")
    public ResponseEntity<GenreResponse> create(@RequestBody @Valid GenreRequest request) {
        return genreService.create(request);
    }

    @GetMapping("/get-all")
    public PageResponse<GenreResponse> findAllGenres(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return genreService.findAllGenres(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponse> findGenreById(@PathVariable Long id) {
        return genreService.findGenreById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GenreResponse> update(@PathVariable Long id, @RequestBody @Valid GenreRequest request) {
        return genreService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        genreService.delete(id);
    }
}