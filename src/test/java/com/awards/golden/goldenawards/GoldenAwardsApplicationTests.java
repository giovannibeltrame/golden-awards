package com.awards.golden.goldenawards;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.awards.golden.service.FileReaderService;
import com.univocity.parsers.common.record.Record;

@SpringBootTest
class GoldenAwardsApplicationTests {
	
	@Autowired
	FileReaderService fileReaderService;

	@Test
	void contextLoads() {
	}
	
	@Test
	void isYearHeaderPresentInFile() throws FileNotFoundException {
		List<String> headers = this.getFileHeaders();
		assertTrue(headers.contains("year"));
	}
	
	@Test
	void isTitleHeaderPresentInFile() throws FileNotFoundException {
		List<String> headers = this.getFileHeaders();
		assertTrue(headers.contains("title"));
	}

	@Test
	void isStudiosHeaderPresentInFile() throws FileNotFoundException {
		List<String> headers = this.getFileHeaders();
		assertTrue(headers.contains("studios"));
	}
	
	@Test
	void isYearProducersPresentInFile() throws FileNotFoundException {
		List<String> headers = this.getFileHeaders();
		assertTrue(headers.contains("producers"));
	}
	
	@Test
	void isWinnerHeaderPresentInFile() throws FileNotFoundException {
		List<String> headers = this.getFileHeaders();
		assertTrue(headers.contains("winner"));
	}

	private List<String> getFileHeaders() throws FileNotFoundException {
		List<Record> fileRecords = fileReaderService.readCsvFile(fileReaderService.getFileInputStream(), Boolean.FALSE);
		List<String> headers = Arrays.asList(fileRecords.iterator().next().getValues());
		return headers;
	}

}
