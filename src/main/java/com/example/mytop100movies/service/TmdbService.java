package com.example.mytop100movies.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TmdbService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    public JsonNode searchMovie(String query) {
        String url = String.format("%s/search/movie?api_key=%s&query=%s", apiUrl, apiKey, query);
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public JsonNode getMovieDetails(int movieId) {
        String url = String.format("%s/movie/%d?api_key=%s", apiUrl, movieId, apiKey);
        return restTemplate.getForObject(url, JsonNode.class);
    }
}