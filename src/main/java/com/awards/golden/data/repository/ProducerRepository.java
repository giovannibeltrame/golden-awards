package com.awards.golden.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.awards.golden.data.entity.Producer;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
	
	Optional<Producer> findByNameIgnoreCase(String name);
	
}
