package com.awards.golden.data.dto;

import java.io.Serializable;
import java.util.List;

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
public class WinningRangeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ProducerDTO> min;
	private List<ProducerDTO> max;

}
