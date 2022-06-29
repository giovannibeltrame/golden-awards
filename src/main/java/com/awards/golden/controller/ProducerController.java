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
import com.awards.golden.data.entity.Producer;
import com.awards.golden.data.repository.ProducerRepository;
import com.awards.golden.service.MovieProducerService;

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
		Optional<Producer> optProducer = producerRepository.findById(idProducer);
		if (optProducer.isPresent()) {
			return new ResponseEntity<>(optProducer.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping
	public void createProducer(@RequestBody Producer producer) {
		producerRepository.save(producer);
	}
	
	@PutMapping
	public void updateProducer(@RequestBody Producer producer) {
		Optional<Producer> optProducer = producerRepository.findById(producer.getId());
		if (optProducer.isPresent()) {
			producerRepository.save(producer);
		}
	}
	
	@DeleteMapping("/{idProducer}")
	public void deleteProducer(@PathVariable(value = "idProducer") Long idProducer) {
		Optional<Producer> optProducer = producerRepository.findById(idProducer);
		if (optProducer.isPresent()) {
			producerRepository.deleteById(idProducer);
		}
	}
	
}
