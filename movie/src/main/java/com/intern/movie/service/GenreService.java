package com.intern.movie.service;

import com.intern.movie.entity.UserEntity;
import com.intern.movie.model.dto.request.GenreRequest;
import com.intern.movie.model.dto.response.GenreResponse;
import com.intern.movie.model.dto.response.PageResponse;
import org.springframework.http.ResponseEntity;

public interface GenreService {

    ResponseEntity<GenreResponse> create(GenreRequest request);
    PageResponse<GenreResponse> findAllGenres(int page, int size);
    ResponseEntity<GenreResponse> findGenreById(Long id);
    ResponseEntity<GenreResponse> update(Long id, GenreRequest request);
    void delete(Long id);
    UserEntity getCurrentUser();
}
