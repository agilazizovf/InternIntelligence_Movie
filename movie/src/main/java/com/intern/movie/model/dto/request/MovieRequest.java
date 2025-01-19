package com.intern.movie.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieRequest {

    @NotBlank(message = "Title is required and cannot be blank.")
    @Size(max = 100, message = "Title must not exceed 100 characters.")
    private String title;

    @NotBlank(message = "Director name is required and cannot be blank.")
    @Size(max = 50, message = "Director name must not exceed 50 characters.")
    private String director;

    @NotNull(message = "Release year is required.")
    @PastOrPresent(message = "Release year must be in the past or present.")
    private LocalDate releaseYear;

    @Pattern(regexp = "tt\\d{7,8}", message = "IMDb must be in a valid format, e.g., tt1234567.")
    private String IMDb;

    @NotEmpty(message = "At least one genre ID must be provided.")
    private List<Long> genreIds;
}
