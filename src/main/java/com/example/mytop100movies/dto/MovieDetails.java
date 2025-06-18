package com.example.mytop100movies.dto;

import lombok.Data;

@Data
public class MovieDetails {
    private Long id;
    private String title;
    private String overview;
    private String releaseDate;
    private String posterPath;
    private String imdbId;
}