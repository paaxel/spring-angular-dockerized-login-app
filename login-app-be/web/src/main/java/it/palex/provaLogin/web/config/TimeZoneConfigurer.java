package it.palex.provaLogin.web.config;

import java.util.Date;
import java.util.TimeZone;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
@Configuration
public class TimeZoneConfigurer {

	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TimeZoneConfigurer.class);

	@Value("${application.time-zone}")
	private String timezone;
	
	@PostConstruct
    public void setTimeZone(){
        TimeZone.setDefault(TimeZone.getTimeZone(timezone));
        LOGGER.debug("Setted "+timezone+" timeZone to timezone :"+new Date());
    }
	
}
