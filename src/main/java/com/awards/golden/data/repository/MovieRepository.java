package com.awards.golden.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.awards.golden.data.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
