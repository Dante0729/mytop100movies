package com.example.mytop100movies.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatedMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A user can rate many movies
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    // Each rated movie is tied to one Movie record (from TMDB)
    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // Rating between 1 and 10
    @Column(nullable = false)
    private int rating;
}
