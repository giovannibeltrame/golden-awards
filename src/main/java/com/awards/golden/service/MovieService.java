package com.awards.golden.service;

import java.util.List;
import java.util.Optional;

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
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ProducerRepository producerRepository;
	
	@Autowired
	private MovieProducerRepository movieProducerRepository;

	public void getMoviesFromRecords(List<Record> records) {
		records.forEach(record -> {
			LOGGER.info("RECORD: " + record);
			
			Movie movie = Movie
					.builder()
					.year(record.getInt("year"))
					.title(record.getString("title"))
					.studios(record.getString("studios"))
					.isWinner("yes".equalsIgnoreCase(record.getString("winner")) ? Boolean.TRUE : Boolean.FALSE)
					.build();
			movieRepository.save(movie);

			String producers = this.replaceCommas(record.getString("producers"));
			
			this.getMovieProducersFromProducers(movie, producers);
		});
	}

	private void getMovieProducersFromProducers(Movie movie, String producers) {
		Splitter.on(",").trimResults().split(producers).forEach(producer -> {
			LOGGER.info("Producer: " + producer);
			Optional<Producer> optProducer = producerRepository.findByNameIgnoreCase(producer);
			MovieProducer movieProducer;
			if (optProducer.isPresent()) {
				movieProducer = MovieProducer.builder().movie(movie).producer(optProducer.get()).build();
			} else {
				Producer newProducer = Producer.builder().name(producer).build();
				producerRepository.save(newProducer);
				movieProducer = MovieProducer.builder().movie(movie).producer(newProducer).build();
			}
			movieProducerRepository.save(movieProducer);
		});
	}

	private String replaceCommas(String producers) {
		LOGGER.info("Producers: " + producers);
		producers = producers.replace(",and ", ", ");
		producers = producers.replace(", and ", ", ");
		producers = producers.replace(" and ", ", ");
		LOGGER.info("Producers: " + producers);
		return producers;
	}

}
