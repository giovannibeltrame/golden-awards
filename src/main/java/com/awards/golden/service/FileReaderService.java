package com.awards.golden.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

@Service
public class FileReaderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileReaderService.class);
	private static final String MOVIELIST_CSV = "movielist.csv";

	@Autowired
	private MovieService movieService;

	public void readFile(InputStream inputStream) throws Exception {
		try {
			if (Objects.isNull(inputStream)) {
				inputStream = this.getFileInputStream(MOVIELIST_CSV);
			}
			movieService.getMoviesFromRecords(this.readCsvFile(inputStream, Boolean.TRUE));
		} catch (Exception e) {
			LOGGER.error("ERROR", e);
			throw e;
		}
	}
	
	public List<Record> readCsvFile(InputStream inputStream, Boolean headerExtractionEnabled) {
		CsvParserSettings settings = new CsvParserSettings();
		settings.setHeaderExtractionEnabled(headerExtractionEnabled);
		settings.detectFormatAutomatically();
		CsvParser parser = new CsvParser(settings);
		return parser.parseAllRecords(inputStream);
	}

	public FileInputStream getFileInputStream(String filePath) throws FileNotFoundException {
		return new FileInputStream(new File(new FileSystemResource(filePath).getFile().getAbsolutePath()));
	}

}
