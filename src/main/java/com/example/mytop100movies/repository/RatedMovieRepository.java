package com.example.mytop100movies.repository;

import com.example.mytop100movies.model.Movie;
import com.example.mytop100movies.model.RatedMovie;
import com.example.mytop100movies.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatedMovieRepository extends JpaRepository<RatedMovie, Long> {
    List<RatedMovie> findByUser(User user);
    Optional<RatedMovie> findByUserAndMovie(User user, Movie movie);

}
