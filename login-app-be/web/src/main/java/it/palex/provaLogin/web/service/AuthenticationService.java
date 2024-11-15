package it.palex.provaLogin.web.service;

import it.palex.provaLogin.dbaccess.entities.UsersAccessToken;
import it.palex.provaLogin.dbaccess.entities.UsersAuthDetails;
import it.palex.provaLogin.dbservices.service.UsersAccessTokenService;
import it.palex.provaLogin.dbservices.service.UsersAuthDetailsService;
import it.palex.provaLogin.library.exceptions.UnauthorizedException;
import it.palex.provaLogin.library.rest.GenericResponse;
import it.palex.provaLogin.web.security.JwtTokenUtil;
import it.palex.provaLogin.library.service.GenericService;
import it.palex.provaLogin.library.utils.DateUtility;
import it.palex.provaLogin.web.rest.dto.AuthenticationDTO;
import it.palex.provaLogin.web.rest.dto.SuccessfullAuthenticationDTO;
import it.palex.provaLogin.web.security.CustomAuthenticationProvider;
import it.palex.provaLogin.web.security.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
@Component
public class AuthenticationService implements GenericService {

    @Value("${security.jwt.duration}")
    private int jwtDuration;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsersAuthDetailsService usersAuthDetailsService;

    @Autowired
    private CustomAuthenticationProvider authenticationManager;

    @Autowired
    private UsersAccessTokenService userAccessTokenService;


    @Transactional(rollbackFor = {Throwable.class})
    public GenericResponse<SuccessfullAuthenticationDTO> authenticate(AuthenticationDTO auth) {
        if (auth == null) {
            return this.buildBadDataResponse();
        }
        if (auth.getUsername() == null || auth.getPassword() == null) {
            return this.buildUnauthorizedResponse("Bad credentials");
        }

        try {
            String username = auth.getUsername();

            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            auth.getUsername(), auth.getPassword()
                    )
            );

            UsersAuthDetails userDetails = this.usersAuthDetailsService.findByUsername(username);

            final Date currentDate = DateUtility.getCurrentDateInUTC();
            final Date expirationDate = DateUtility.addSecondsToDate(currentDate, this.jwtDuration);

            TokenInfo token = this.jwtTokenUtil.generateToken(userDetails, expirationDate);

            UsersAccessToken accessToken = new UsersAccessToken();

            accessToken.setExpirationDate(expirationDate);
            accessToken.setIssuedDate(currentDate);
            accessToken.setFkIdUsersAuthDetails(userDetails);
            accessToken.setTokenId(token.getTokenId());

            this.userAccessTokenService.saveOrUpdate(accessToken);

            SuccessfullAuthenticationDTO res = new SuccessfullAuthenticationDTO();
            res.setAuthToken(token.getJwtToken());

            return this.buildOkResponse(res);

        } catch (BadCredentialsException e) {
            return this.buildUnauthorizedResponse("Bad credentials");
        } catch (UnauthorizedException e) {
            return this.buildUnauthorizedResponse(e.getMessage(), e.getCode());
        }
    }

}
