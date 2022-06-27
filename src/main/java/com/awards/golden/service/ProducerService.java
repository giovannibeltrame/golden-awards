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
import com.awards.golden.data.entity.Producer;
import com.awards.golden.data.repository.MovieProducerRepository;
import com.awards.golden.data.repository.ProducerRepository;

@Service
public class ProducerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);

	@Autowired
	private ProducerRepository producerRepository;

	@Autowired
	private MovieProducerRepository movieProducerRepository;

	public void calculateWinningRange() {
		LOGGER.info("calculateWinningRange");
		producerRepository.findAll().forEach(producer -> {
			List<MovieProducer> movieProducers = movieProducerRepository
					.findByProducerAndMovieIsWinnerOrderByMovieYearDesc(producer, Boolean.TRUE);
			if (movieProducers.size() <= 1) {
				producer.setMinWinningRange(null);
				producer.setMaxWinningRange(null);
			} else {
				Integer minWinRange = null, maxWinRange = null, minPreviousWin = null, minFollowingWin = null,
						maxPreviousWin = null, maxFollowingWin = null;

				ListIterator<MovieProducer> listIterator = movieProducers.listIterator();
				Integer year1 = listIterator.next().getMovie().getYear();
				while (listIterator.hasNext()) {
					Integer year2 = listIterator.next().getMovie().getYear();
					if (Objects.isNull(minWinRange) || (year1 - year2) < minWinRange) {
						minWinRange = year1 - year2;
						minPreviousWin = year2;
						minFollowingWin = year1;
					}
					if (Objects.isNull(maxWinRange) || (year1 - year2) > maxWinRange) {
						maxWinRange = year1 - year2;
						maxPreviousWin = year2;
						maxFollowingWin = year1;
					}
					if (listIterator.hasNext()) {
						year1 = year2;
					}
				}

				producer.setMinWinningRange(minWinRange);
				producer.setMaxWinningRange(maxWinRange);
				producer.setMinPreviousWin(minPreviousWin);
				producer.setMinFollowingWin(minFollowingWin);
				producer.setMaxPreviousWin(maxPreviousWin);
				producer.setMaxFollowingWin(maxFollowingWin);
			}
			producerRepository.save(producer);
		});
	}

	public WinningRangeDTO getWinningRange() {
		WinningRangeDTO winningRangeDTO = new WinningRangeDTO();
		List<ProducerDTO> min = new ArrayList<>();
		List<ProducerDTO> max = new ArrayList<>();
		
		List<Producer> minRange = producerRepository.findAllByMinWinningRangeNotNullOrderByMinWinningRange();
		List<Producer> maxRange = producerRepository.findAllByMaxWinningRangeNotNullOrderByMaxWinningRangeDesc();
		
		ListIterator<Producer> minRangeIterator = minRange.listIterator();
		Producer previous = null;
		while (minRangeIterator.hasNext()) {
			Producer producer = minRangeIterator.next();
			if (Objects.nonNull(previous) && previous.getMinWinningRange() < producer.getMinWinningRange()) {
				break;
			}
			ProducerDTO producerDTO = ProducerDTO
					.builder()
					.producer(producer.getName())
					.interval(producer.getMinWinningRange())
					.previousWin(producer.getMinPreviousWin())
					.followingWin(producer.getMinFollowingWin())
					.build();
			min.add(producerDTO);
			previous = producer;
		}
		
		ListIterator<Producer> maxRangeIterator = maxRange.listIterator();
		previous = null;
		while (maxRangeIterator.hasNext()) {
			Producer producer = maxRangeIterator.next();
			if (Objects.nonNull(previous) && previous.getMaxWinningRange() > producer.getMaxWinningRange()) {
				break;
			}
			ProducerDTO producerDTO = ProducerDTO
										.builder()
										.producer(producer.getName())
										.interval(producer.getMaxWinningRange())
										.previousWin(producer.getMaxPreviousWin())
										.followingWin(producer.getMaxFollowingWin())
										.build();
			max.add(producerDTO);
			previous = producer;
		}
		
		winningRangeDTO.setMin(min);
		winningRangeDTO.setMax(max);
		return winningRangeDTO;
	}

}
