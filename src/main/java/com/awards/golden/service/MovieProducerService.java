package com.awards.golden.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awards.golden.data.dto.ProducerDTO;
import com.awards.golden.data.dto.WinningRangeDTO;
import com.awards.golden.data.entity.MovieProducer;
import com.awards.golden.data.repository.MovieProducerRepository;
import com.awards.golden.data.repository.ProducerRepository;

@Service
public class MovieProducerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieProducerService.class);

	@Autowired
	private ProducerRepository producerRepository;

	@Autowired
	private MovieProducerRepository movieProducerRepository;

	public void calculateWinningRange() {
		LOGGER.debug("calculateWinningRange");
		producerRepository.findAll().forEach(producer -> {
			var movieProducers = movieProducerRepository
					.findByProducerAndMovieIsWinnerOrderByMovieYearDesc(producer, Boolean.TRUE);
			if (movieProducers.size() > 1) {
				var listIterator = movieProducers.listIterator();
				var lastMovieProducer = listIterator.next();
				while (listIterator.hasNext()) {
					var lastYear = lastMovieProducer.getMovie().getYear();
					var nextMovieProducer = listIterator.next();
					var nextYear = nextMovieProducer.getMovie().getYear();

					lastMovieProducer.setWinningRange(lastYear - nextYear);
					lastMovieProducer.setLastWinYear(nextYear);
					movieProducerRepository.save(lastMovieProducer);

					lastMovieProducer = nextMovieProducer;
				}
			}
		});
	}

	public WinningRangeDTO getWinningRange() {
		var min = collectRange(movieProducerRepository.findAllByWinningRangeNotNullOrderByWinningRange(),
				(previousRange, currentRange) -> previousRange < currentRange);
		var max = collectRange(movieProducerRepository.findAllByWinningRangeNotNullOrderByWinningRangeDesc(),
				(previousRange, currentRange) -> previousRange > currentRange);
		return new WinningRangeDTO(min, max);
	}

	private List<ProducerDTO> collectRange(List<MovieProducer> range, BiPredicate<Integer, Integer> stop) {
		var result = new ArrayList<ProducerDTO>();
		Integer previousRange = null;
		for (var movieProducer : range) {
			if (previousRange != null && stop.test(previousRange, movieProducer.getWinningRange())) {
				break;
			}
			result.add(new ProducerDTO(
					movieProducer.getProducer().getName(),
					movieProducer.getWinningRange(),
					movieProducer.getLastWinYear(),
					movieProducer.getMovie().getYear()));
			previousRange = movieProducer.getWinningRange();
		}
		return result;
	}

}
