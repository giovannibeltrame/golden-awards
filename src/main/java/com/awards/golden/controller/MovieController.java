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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.awards.golden.data.entity.Movie;
import com.awards.golden.data.repository.MovieRepository;
import com.awards.golden.service.FileReaderService;

@RestController
@RequestMapping("/movies")
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private FileReaderService fileReaderService;
	
	@GetMapping
	public List<Movie> getMovies() {
		return movieRepository.findAll();
	}
	
	@GetMapping("/{idMovie}")
	public ResponseEntity<Movie> getMovie(@PathVariable(value = "idMovie") Long idMovie) {
		Optional<Movie> optMovie = movieRepository.findById(idMovie);
		if (optMovie.isPresent()) {
			return new ResponseEntity<>(optMovie.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping
	public void createMovie(@RequestBody Movie movie) {
		movieRepository.save(movie);
	}
	
	@PutMapping
	public void updateMovie(@RequestBody Movie movie) {
		Optional<Movie> optMovie = movieRepository.findById(movie.getId());
		if (optMovie.isPresent()) {
			movieRepository.save(movie);
		}
	}
	
	@DeleteMapping("/{idMovie}")
	public void deleteMovie(@PathVariable(value = "idMovie") Long idMovie) {
		Optional<Movie> optMovie = movieRepository.findById(idMovie);
		if (optMovie.isPresent()) {
			movieRepository.deleteById(idMovie);
		}
	}
	
	@PostMapping("upload-file")
	public void uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
		fileReaderService.readFile(file.getInputStream());
	}

}
