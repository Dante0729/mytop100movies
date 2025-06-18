package com.example.mytop100movies.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tmdbId;

    @Column(length = 512)
    private String title;

    @Column(length = 5000)
    private String overview;

    @Column(length = 1024)
    private String posterPath;

    private LocalDate releaseDate;

    @Column(length = 64)
    private String imdbId;
}
