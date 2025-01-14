package com.intern.movie.model.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieRequest {

    private String title;
    private String director;
    private LocalDate releaseYear;
    private String genre;
    private String IMDb;
}
