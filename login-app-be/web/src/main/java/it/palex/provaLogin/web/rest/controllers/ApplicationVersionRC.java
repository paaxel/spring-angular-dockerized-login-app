package it.palex.provaLogin.web.rest.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import it.palex.provaLogin.library.rest.GenericResponse;
import it.palex.provaLogin.library.rest.RestEndpoint;
import it.palex.provaLogin.library.rest.aspects.Loggable;
import it.palex.provaLogin.library.service.ApplicationVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * @author Alessandro Pagliaro
 *
 */
@RequestMapping(path = "version")
@RestController
@Tags(value ={@Tag(name = "ApplicationVersion Controller")})
public class ApplicationVersionRC extends RestEndpoint {

	@Autowired
	private ApplicationVersionService versionSrv;
	
	@GetMapping()
	@Loggable(logExecutionTime = true, logParameters = true, logResponseParameter = true, gdprCompliant = true)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "OK"
			)
	})
	public ResponseEntity<GenericResponse<String>> getApplicationVersion(){
		GenericResponse<String> appVersion = new GenericResponse<>(this.versionSrv.getVersion(),
				HttpStatus.OK.value(), "OK");

		return this.buildGenericResponse(appVersion);
	}

}
