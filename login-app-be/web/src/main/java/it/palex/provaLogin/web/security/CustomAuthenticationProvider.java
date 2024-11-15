package it.palex.provaLogin.web.security;

import it.palex.provaLogin.dbaccess.entities.UsersAuthDetails;
import it.palex.provaLogin.dbservices.service.UsersAuthDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Alessandro Pagliaro
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${security.login.bcrypt.streght}")
    private Integer bcryptStrenght;

    @Autowired
    private UsersAuthDetailsService authDetailsService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        if (auth == null || auth.getCredentials() == null) {
            throw new BadCredentialsException("Username or password are wrong!");
        }

        final String username = auth.getName();

        final String passwordPlainText = auth.getCredentials().toString();

        UsersAuthDetails user = getUserDetailsByUsername(username);


        if (user == null || passwordPlainText == null || user.getPassword() == null || !checkBCryptPassword(passwordPlainText, user.getPassword())) {
            throw new BadCredentialsException("Username or password are wrong!");
        }
        if (checkOtherPrerequisiteToLogin(user)) {
            throw new BadCredentialsException("Account currently deactivated");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();


        if (authorities == null) {
            authorities = new LinkedList<>();
        }

        return new UsernamePasswordAuthenticationToken(user, passwordPlainText, authorities);
    }

    private UsersAuthDetails getUserDetailsByUsername(String username){
        if(username==null){
            return null;
        }

        return authDetailsService.findByUsername(username);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
    public boolean checkBCryptPassword(String plainTextPSW, String hashedPSW) {
        if (plainTextPSW == null || hashedPSW == null) {
            throw new NullPointerException("Cannot apply bcrypt on null password");
        }
        return BCrypt.checkpw(plainTextPSW, hashedPSW);
    }

    private boolean checkOtherPrerequisiteToLogin(UserDetails user) {
        return !user.isEnabled() || !user.isCredentialsNonExpired() ||
                !user.isAccountNonLocked() || !user.isAccountNonExpired();
    }

}
