package com.awards.golden.data.dto;

import java.io.Serializable;

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
public class ProducerDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String producer;
	private Integer interval;
	private Integer previousWin;
	private Integer followingWin;

}
