package it.palex.provaLogin.web.config;

import it.palex.provaLogin.web.security.CustomAuthenticationTokenFilter;
import it.palex.provaLogin.web.security.FailedAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Value("#{'${security.cors.allowed_headers}'.split(',')}")
    private List<String> allowedHeaders;

    @Value("#{'${security.cors.exposed_headers}'.split(',')}")
    private List<String> exposedHeaders;

    @Value("#{'${security.cors.allowed_origins}'.split(',')}")
    private List<String> allowedOrigins;

    @Value("#{'${security.cors.allowed_methods}'.split(',')}")
    private List<String> allowedMethods;

    @Value("${security.cors.allow_credentials}")
    private boolean allowCredentials;

    @Value("${security.cors.max_age}")
    private Long maxAge;

    @Autowired
    private FailedAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll();


        // Custom JWT based security filter
        httpSecurity.addFilterBefore(authenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);


        httpSecurity
                // we don't need CSRF jwt token is are not vulnerable to this attack (client must add it in Authorization header)
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource());

        // disable page caching
        httpSecurity.headers()
                .frameOptions()
                .sameOrigin()
                .cacheControl();

        return httpSecurity.build();
    }

    @Bean
    public CustomAuthenticationTokenFilter authenticationFilterBean() throws Exception {
        return new CustomAuthenticationTokenFilter();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setExposedHeaders(exposedHeaders);
        if(allowedOrigins.contains("*")){
            configuration.addAllowedOriginPattern("*");
        }else{
            configuration.setAllowedOrigins(allowedOrigins);
        }

        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowCredentials(allowCredentials);
        configuration.setMaxAge(maxAge);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
