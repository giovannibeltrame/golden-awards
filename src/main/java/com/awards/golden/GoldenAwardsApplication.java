package com.awards.golden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.awards.golden.service.FileReaderService;

@EnableWebMvc
@SpringBootApplication
public class GoldenAwardsApplication implements CommandLineRunner {
	
	@Autowired
	private FileReaderService fileReaderService;
	
	public static void main(String[] args) {
		SpringApplication.run(GoldenAwardsApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		fileReaderService.readFile(null);
	}

}
