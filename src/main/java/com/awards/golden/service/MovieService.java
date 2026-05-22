package com.awards.golden.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awards.golden.data.entity.Movie;
import com.awards.golden.data.entity.MovieProducer;
import com.awards.golden.data.entity.Producer;
import com.awards.golden.data.repository.MovieProducerRepository;
import com.awards.golden.data.repository.MovieRepository;
import com.awards.golden.data.repository.ProducerRepository;
import com.google.common.base.Splitter;
import com.univocity.parsers.common.record.Record;

@Service
public class MovieService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
	private static final String YES = "yes";

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ProducerRepository producerRepository;

	@Autowired
	private MovieProducerRepository movieProducerRepository;

	public void getMoviesFromRecords(List<Record> records) {
		records.forEach(record -> {
			LOGGER.debug("RECORD: {}", record);

			var movie = new Movie();
			movie.setYear(record.getInt("year"));
			movie.setTitle(record.getString("title"));
			movie.setStudios(record.getString("studios"));
			movie.setIsWinner(YES.equalsIgnoreCase(record.getString("winner")));
			movieRepository.save(movie);

			var producers = this.replaceCommas(Objects.requireNonNullElse(record.getString("producers"), ""));

			this.getMovieProducersFromProducers(movie, producers);
		});
	}

	private void getMovieProducersFromProducers(Movie movie, String producers) {
		Splitter.on(",").trimResults().split(producers).forEach(producer -> {
			LOGGER.debug("Producer: {}", producer);
			var optProducer = producerRepository.findByNameIgnoreCase(producer);
			var resolvedProducer = optProducer.orElseGet(() -> {
				var newProducer = new Producer();
				newProducer.setName(producer);
				return producerRepository.save(newProducer);
			});
			var movieProducer = new MovieProducer();
			movieProducer.setMovie(movie);
			movieProducer.setProducer(resolvedProducer);
			movieProducerRepository.save(movieProducer);
		});
	}

	private String replaceCommas(String producers) {
		LOGGER.debug("Producers: {}", producers);
		producers = producers.replace(",and ", ", ");
		producers = producers.replace(", and ", ", ");
		producers = producers.replace(" and ", ", ");
		LOGGER.debug("Producers: {}", producers);
		return producers;
	}

}
