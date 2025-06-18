package com.example.mytop100movies.controller;

import com.example.mytop100movies.dto.MovieDetails;
import com.example.mytop100movies.service.TMDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tmdb")
@RequiredArgsConstructor
public class TmdbController {

    private final TMDBService tmdbService;

    @GetMapping("/movie/{id}")
    public MovieDetails getMovie(@PathVariable int id) {
        return tmdbService.getMovieDetails((long) id);
    }
}