package it.palex.provaLogin.library.rest;

import org.springframework.http.ResponseEntity;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
public abstract class RestEndpoint {

	private <T> ResponseEntity<GenericResponse<T>> buildResponse(T data, String version, int responseCode, String message){
		GenericResponse<T> park = new GenericResponse<>();
		park.setVersion(version);
		park.setData(data);
		park.setCode(responseCode);
		park.setMessage(message);
		
		return this.buildGenericResponse(park);
	}
	
	
	protected <T> ResponseEntity<GenericResponse<T>> buildGenericResponse(GenericResponse<T> response) {
		if(response==null){
			return ResponseEntity.ok().build();
		}
		return ResponseEntity
					.status(response.getCode())
						.body(response);
	}
	
	protected <T> ResponseEntity<GenericResponse<T>> buildGenericResponse(GenericResponse<T> response, String apiVersion) {
		if(response==null){
			return ResponseEntity.ok().build();
		}
		response.setVersion(apiVersion);
		
		return this.buildGenericResponse(response);
	}

}
