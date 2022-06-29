package com.awards.golden.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awards.golden.data.dto.WinningRangeDTO;
import com.awards.golden.data.entity.MovieProducer;
import com.awards.golden.data.repository.MovieProducerRepository;
import com.awards.golden.service.MovieProducerService;

@RestController
@RequestMapping("/movie-producer")
public class MovieProducerController {
	
	@Autowired
	private MovieProducerRepository movieProducerRepository;
	
	@Autowired
	private MovieProducerService movieProducerService;
	
	@GetMapping
	public List<MovieProducer> getMovieProducers() {
		return movieProducerRepository.findAll();
	}
	
	@GetMapping("/{idMovieProducer}")
	public ResponseEntity<MovieProducer> getMovieProducer(@PathVariable(value = "idMovieProducer") Long idMovieProducer) {
		Optional<MovieProducer> optMovieProducer = movieProducerRepository.findById(idMovieProducer);
		if (optMovieProducer.isPresent()) {
			return new ResponseEntity<>(optMovieProducer.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping
	public void createMovieProducer(@RequestBody MovieProducer movieProducer) {
		movieProducerRepository.save(movieProducer);
	}
	
	@PutMapping
	public void updateMovieProducer(@RequestBody MovieProducer movieProducer) {
		Optional<MovieProducer> optMovieProducer = movieProducerRepository.findById(movieProducer.getId());
		if (optMovieProducer.isPresent()) {
			movieProducerRepository.save(movieProducer);
		}
	}
	
	@DeleteMapping("/{idMovieProducer}")
	public void deleteMovieProducer(@PathVariable(value = "idMovieProducer") Long idMovieProducer) {
		Optional<MovieProducer> optMovieProducer = movieProducerRepository.findById(idMovieProducer);
		if (optMovieProducer.isPresent()) {
			movieProducerRepository.deleteById(idMovieProducer);
		}
	}
	
	@GetMapping("/winning-range")
	public WinningRangeDTO getWinningRange() {
		movieProducerService.calculateWinningRange();
		return movieProducerService.getWinningRange();
	}

}
