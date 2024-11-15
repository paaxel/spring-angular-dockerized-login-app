package it.palex.provaLogin.library.rest.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
public interface DTO extends Serializable {

	default String toJsonString() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(this);
	}
	
}