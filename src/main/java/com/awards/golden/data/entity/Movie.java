package com.awards.golden.data.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "movies")
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "release_year")
	private Integer year;
	
	@Column(length = 256)
	private String title;
	
	@Column(length = 256)
	private String studios;
	
    @Column(name = "winner")
	private Boolean isWinner;
    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
	private List<MovieProducer> movieProducer;

}
