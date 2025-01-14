package com.intern.movie.model.dto.response;

import com.intern.movie.entity.UserEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieResponse {

    private Long id;
    private String title;
    private String director;
    private LocalDate releaseYear;
    private String genre;
    private String IMDb;
}
