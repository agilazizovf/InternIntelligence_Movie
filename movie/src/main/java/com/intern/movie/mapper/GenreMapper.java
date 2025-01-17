package com.intern.movie.mapper;

import com.intern.movie.entity.GenreEntity;
import com.intern.movie.model.dto.response.GenreResponse;

public class GenreMapper {

    public static GenreResponse toGenreDTO(GenreEntity genre) {
        GenreResponse response = new GenreResponse();
        response.setId(genre.getId());
        response.setName(genre.getName());
        return response;
    }
}
