package com.awards.golden.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies_producers")
public class MovieProducer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producer_id")
	private Producer producer;

	@Column
	private Integer winningRange;

	@Column
	private Integer lastWinYear;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public Integer getWinningRange() {
		return winningRange;
	}

	public void setWinningRange(Integer winningRange) {
		this.winningRange = winningRange;
	}

	public Integer getLastWinYear() {
		return lastWinYear;
	}

	public void setLastWinYear(Integer lastWinYear) {
		this.lastWinYear = lastWinYear;
	}

}
