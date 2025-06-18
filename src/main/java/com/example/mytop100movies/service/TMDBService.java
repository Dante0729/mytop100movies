package com.example.mytop100movies.service;

import com.example.mytop100movies.dto.MovieDetails;
import com.example.mytop100movies.model.Movie;
import com.example.mytop100movies.model.TMDBMovieResponse;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TMDBService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Movie fetchMovieFromTMDB(Long tmdbId) {
        String url = baseUrl + "/movie/" + tmdbId + "?api_key=" + apiKey;

        ResponseEntity<TMDBMovieResponse> response = restTemplate.getForEntity(url, TMDBMovieResponse.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            TMDBMovieResponse tmdbMovie = response.getBody();

            Movie movie = new Movie();
            movie.setTmdbId(tmdbMovie.getId());
            movie.setTitle(tmdbMovie.getTitle());
            movie.setOverview(tmdbMovie.getOverview());
            movie.setPosterPath(tmdbMovie.getPosterPath());

            return movie;
        } else {
            throw new RuntimeException("TMDB movie not found");
        }
    }
    public MovieDetails getMovieDetails(Long tmdbId) {
        String url = baseUrl + "/movie/" + tmdbId + "?api_key=" + apiKey;
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);

        JsonNode body = response.getBody();
        if (body == null) {
            throw new RuntimeException("No response from TMDB.");
        }

        MovieDetails details = new MovieDetails();
        details.setId(body.path("id").asLong());
        details.setTitle(body.path("title").asText());
        details.setOverview(body.path("overview").asText());
        details.setReleaseDate(body.path("release_date").asText());
        details.setPosterPath(body.path("poster_path").asText());
        details.setImdbId(body.path("imdb_id").asText());

        return details;
    }





}
