package com.intern.movie.model.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieRequest {

    private String title;
    private String director;
    private LocalDate releaseYear;
    private String IMDb;
    private List<Long> genreIds;
}
