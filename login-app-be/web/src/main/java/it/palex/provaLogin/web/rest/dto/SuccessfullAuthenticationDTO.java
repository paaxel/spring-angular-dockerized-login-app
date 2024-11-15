package it.palex.provaLogin.web.rest.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import it.palex.provaLogin.library.rest.dtos.DTO;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
@Schema(description = "SuccessfullAuthenticationDTO")
public class SuccessfullAuthenticationDTO implements DTO {

    private static final long serialVersionUID = 3252811914354566L;

    @Schema(description = "Authentication Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
            "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ." +
            "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
