package it.palex.provaLogin.web.soap;

import it.palex.provaLogin.dbaccess.entities.enums.AuthoritiesValues;
import it.palex.provaLogin.web.service.UserProfileWebService;
import it.palex.provaLogin.web.soap.GetProfileRequest;
import it.palex.provaLogin.web.soap.GetProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class UserProfileEndpoint {

    private static final String NAMESPACE_URI = "http://provaLogin.palex.it/web/soap";

    @Autowired
    private UserProfileWebService userProfileWebService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProfileRequest")
    @ResponsePayload
    @PreAuthorize("hasAuthority('"+ AuthoritiesValues.LOGGED_USER_PERMISSION+"')")
    public GetProfileResponse getProfileRequest(@RequestPayload GetProfileRequest request) {
        GetProfileResponse profileRes = this.userProfileWebService.getCurrentLoggedUserProfile();

        return profileRes;
    }

}
