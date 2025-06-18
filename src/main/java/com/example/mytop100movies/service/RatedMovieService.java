package com.example.mytop100movies.service;

import com.example.mytop100movies.dto.MovieDetails;
import com.example.mytop100movies.dto.RatedMovieDto;
import com.example.mytop100movies.model.Movie;
import com.example.mytop100movies.model.RatedMovie;
import com.example.mytop100movies.model.User;
import com.example.mytop100movies.repository.MovieRepository;
import com.example.mytop100movies.repository.RatedMovieRepository;
import com.example.mytop100movies.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatedMovieService {
    private final TMDBService tmdbService;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final RatedMovieRepository ratedMovieRepository;

    public RatedMovieDto addRatedMovie(Long userId, Long tmdbId, int rating) {
        if (rating < 1 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 1 and 10.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Movie movie = movieRepository.findByTmdbId(tmdbId)
                .orElseGet(() -> {
                    MovieDetails md = tmdbService.getMovieDetails(tmdbId);
                    Movie m = new Movie();
                    m.setTmdbId(tmdbId);
                    m.setTitle(md.getTitle());
                    m.setOverview(md.getOverview());
                    m.setReleaseDate(LocalDate.parse(md.getReleaseDate()));
                    m.setPosterPath(md.getPosterPath());
                    return movieRepository.save(m);
                });

        RatedMovie rated = new RatedMovie();
        rated.setUser(user);
        rated.setMovie(movie);
        rated.setRating(rating);

        RatedMovie saved = ratedMovieRepository.save(rated);
        return toDto(saved);
    }

    public List<RatedMovieDto> getRatedMovies(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ratedMovieRepository.findByUser(user)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    private RatedMovieDto toDto(RatedMovie ratedMovie) {
        RatedMovieDto dto = new RatedMovieDto();
        dto.setTitle(ratedMovie.getMovie().getTitle());
        dto.setOverview(ratedMovie.getMovie().getOverview());
        dto.setReleaseDate(ratedMovie.getMovie().getReleaseDate().toString());
        dto.setPosterPath(ratedMovie.getMovie().getPosterPath());
        dto.setRating(ratedMovie.getRating());
        return dto;
    }
    public void deleteRatedMovie(Long userId, Long tmdbId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Movie movie = movieRepository.findByTmdbId(tmdbId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        RatedMovie ratedMovie = ratedMovieRepository.findByUserAndMovie(user, movie)
                .orElseThrow(() -> new RuntimeException("Rated movie not found"));

        ratedMovieRepository.delete(ratedMovie);
    }
    public RatedMovieDto updateRating(Long userId, Long tmdbId, int newRating) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Movie movie = movieRepository.findByTmdbId(tmdbId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        RatedMovie ratedMovie = ratedMovieRepository.findByUserAndMovie(user, movie)
                .orElseThrow(() -> new RuntimeException("Rated movie not found"));

        ratedMovie.setRating(newRating);
        RatedMovie updated = ratedMovieRepository.save(ratedMovie);

        return toDto(updated);
    }

}

