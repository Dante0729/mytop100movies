package com.example.mytop100movies.controller;

import com.example.mytop100movies.dto.RatedMovieDto;
import com.example.mytop100movies.service.RatedMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatedMovieController {
    private final RatedMovieService ratedMovieService;

    @PostMapping("/{userId}/{tmdbId}/{rating}")
    public RatedMovieDto addRating(@PathVariable Long userId,
                                   @PathVariable Long tmdbId,
                                   @PathVariable int rating) {
        return ratedMovieService.addRatedMovie(userId, tmdbId, rating);
    }

    @GetMapping("/{userId}")
    public List<RatedMovieDto> getRatedMovies(@PathVariable Long userId) {
        return ratedMovieService.getRatedMovies(userId);
    }

    @DeleteMapping("/{userId}/{tmdbId}")
    public ResponseEntity<String> deleteRatedMovie(@PathVariable Long userId, @PathVariable Long tmdbId) {
        try {
            ratedMovieService.deleteRatedMovie(userId, tmdbId);
            return ResponseEntity.ok("Rated movie deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("/{userId}/{tmdbId}/{rating}")
    public RatedMovieDto updateRating(@PathVariable Long userId,
                                      @PathVariable Long tmdbId,
                                      @PathVariable int rating) {
        return ratedMovieService.updateRating(userId, tmdbId, rating);
    }


}

