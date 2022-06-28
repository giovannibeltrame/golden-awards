package com.awards.golden.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

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
		LOGGER.info("calculateWinningRange");
		producerRepository.findAll().forEach(producer -> {
			List<MovieProducer> movieProducers = movieProducerRepository
					.findByProducerAndMovieIsWinnerOrderByMovieYearDesc(producer, Boolean.TRUE);
			if (movieProducers.size() > 1) {
				ListIterator<MovieProducer> listIterator = movieProducers.listIterator();
				MovieProducer lastMovieProducer = listIterator.next();
				while (listIterator.hasNext()) {
					Integer lastYear = lastMovieProducer.getMovie().getYear();
					MovieProducer nextMovieProducer = listIterator.next();
					Integer nextYear = nextMovieProducer.getMovie().getYear();
					
					lastMovieProducer.setWinningRange(lastYear - nextYear);
					lastMovieProducer.setLastWinYear(nextYear);
					movieProducerRepository.save(lastMovieProducer);
					
					lastMovieProducer = nextMovieProducer;
				}
			}
		});
	}

	public WinningRangeDTO getWinningRange() {
		WinningRangeDTO winningRangeDTO = new WinningRangeDTO();
		List<ProducerDTO> min = new ArrayList<>();
		List<ProducerDTO> max = new ArrayList<>();
		
//		List<Producer> minRange = producerRepository.findAllByMinWinningRangeNotNullOrderByMinWinningRange();
//		List<Producer> maxRange = producerRepository.findAllByMaxWinningRangeNotNullOrderByMaxWinningRangeDesc();
		List<MovieProducer> minRange = movieProducerRepository.findAllByWinningRangeNotNullOrderByWinningRange();
		List<MovieProducer> maxRange = movieProducerRepository.findAllByWinningRangeNotNullOrderByWinningRangeDesc();
//		
		ListIterator<MovieProducer> minRangeIterator = minRange.listIterator();
		MovieProducer previous = null;
		while (minRangeIterator.hasNext()) {
			MovieProducer movieProducer = minRangeIterator.next();
			if (Objects.nonNull(previous) && previous.getWinningRange() < movieProducer.getWinningRange()) {
				break;
			}
			ProducerDTO producerDTO = ProducerDTO
					.builder()
					.producer(movieProducer.getProducer().getName())
					.interval(movieProducer.getWinningRange())
					.previousWin(movieProducer.getLastWinYear())
					.followingWin(movieProducer.getMovie().getYear())
					.build();
			min.add(producerDTO);
			previous = movieProducer;
		}
		
		ListIterator<MovieProducer> maxRangeIterator = maxRange.listIterator();
		previous = null;
		while (maxRangeIterator.hasNext()) {
			MovieProducer movieProducer = maxRangeIterator.next();
			if (Objects.nonNull(previous) && previous.getWinningRange() > movieProducer.getWinningRange()) {
				break;
			}
			ProducerDTO producerDTO = ProducerDTO
										.builder()
										.producer(movieProducer.getProducer().getName())
										.interval(movieProducer.getWinningRange())
										.previousWin(movieProducer.getLastWinYear())
										.followingWin(movieProducer.getMovie().getYear())
										.build();
			max.add(producerDTO);
			previous = movieProducer;
		}
		
		winningRangeDTO.setMin(min);
		winningRangeDTO.setMax(max);
		return winningRangeDTO;
	}

}
