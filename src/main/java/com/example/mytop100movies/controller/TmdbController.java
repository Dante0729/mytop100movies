package com.example.mytop100movies.controller;

import com.example.mytop100movies.service.TmdbService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tmdb")
@RequiredArgsConstructor
public class TmdbController {

    private final TmdbService tmdbService;

    @GetMapping("/search")
    public JsonNode search(@RequestParam String query) {
        return tmdbService.searchMovie(query);
    }

    @GetMapping("/movie/{id}")
    public JsonNode getMovie(@PathVariable int id) {
        return tmdbService.getMovieDetails(id);
    }
}
