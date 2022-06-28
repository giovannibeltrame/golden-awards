package com.awards.golden.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.awards.golden.data.entity.MovieProducer;
import com.awards.golden.data.entity.Producer;

@Repository
public interface MovieProducerRepository extends JpaRepository<MovieProducer, Long> {
	
	List<MovieProducer> findByProducerAndMovieIsWinnerOrderByMovieYearDesc(Producer producer, Boolean isWinner);
	
	List<MovieProducer> findAllByWinningRangeNotNullOrderByWinningRange();
	
	List<MovieProducer> findAllByWinningRangeNotNullOrderByWinningRangeDesc();

}
