package it.palex.provaLogin.web.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import it.palex.provaLogin.library.rest.GenericResponse;
import it.palex.provaLogin.library.rest.RestEndpoint;
import it.palex.provaLogin.library.rest.aspects.Loggable;
import it.palex.provaLogin.web.rest.dto.AuthenticationDTO;
import it.palex.provaLogin.web.rest.dto.SuccessfullAuthenticationDTO;
import it.palex.provaLogin.web.service.AuthenticationService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author Alessandro Pagliaro
 *
 */
@RestController
@RequestMapping("/authentication")
@Tags(value ={@Tag(name = "Authentication Controller")})
public class AuthenticationRC extends RestEndpoint {

	@Autowired
	private AuthenticationService authService;

    @PostMapping()
    @PermitAll
	@Loggable(logExecutionTime = true, logParameters = false, logResponseParameter = false)
	@Operation(summary = "Execute the specified model and version.")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "OK"
			),
			@ApiResponse(
					responseCode = "400",
					description = "Bad data",
					content = @Content(schema = @Schema(implementation = GenericResponse.class))
			),
			@ApiResponse(
					responseCode = "401",
					description = "Bad credentials",
					content = @Content(schema = @Schema(implementation = GenericResponse.class))
			)
	})
    public ResponseEntity<GenericResponse<SuccessfullAuthenticationDTO>> authenticate(
    			   @RequestBody AuthenticationDTO auth){
		GenericResponse<SuccessfullAuthenticationDTO> response = authService.authenticate(auth);

		return this.buildGenericResponse(response);
    }

}
