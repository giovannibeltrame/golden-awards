package com.awards.golden.controller;

import java.util.List;

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

import com.awards.golden.data.entity.Producer;
import com.awards.golden.data.repository.ProducerRepository;

@RestController
@RequestMapping("/producers")
public class ProducerController {

	@Autowired
	private ProducerRepository producerRepository;

	@GetMapping
	public List<Producer> getProducers() {
		return producerRepository.findAll();
	}

	@GetMapping("/{idProducer}")
	public ResponseEntity<Producer> getProducer(@PathVariable(value = "idProducer") Long idProducer) {
		return producerRepository.findById(idProducer)
				.map(producer -> new ResponseEntity<>(producer, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
	}

	@PostMapping
	public void createProducer(@RequestBody Producer producer) {
		producerRepository.save(producer);
	}

	@PutMapping
	public void updateProducer(@RequestBody Producer producer) {
		producerRepository.findById(producer.getId()).ifPresent(existing -> producerRepository.save(producer));
	}

	@DeleteMapping("/{idProducer}")
	public void deleteProducer(@PathVariable(value = "idProducer") Long idProducer) {
		producerRepository.findById(idProducer).ifPresent(producer -> producerRepository.deleteById(idProducer));
	}

}
