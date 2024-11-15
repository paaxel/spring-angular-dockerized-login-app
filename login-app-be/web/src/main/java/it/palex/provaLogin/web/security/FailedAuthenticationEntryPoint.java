package it.palex.provaLogin.web.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Alessandro Pagliaro
 *
 */
@Component
public class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FailedAuthenticationEntryPoint.class);

    private static final long serialVersionUID = 345345435679027364L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        LOGGER.debug("FailedAuthenticationEntryPoint called with a none authenticated request");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
