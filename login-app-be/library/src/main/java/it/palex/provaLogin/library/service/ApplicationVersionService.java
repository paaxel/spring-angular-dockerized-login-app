package it.palex.provaLogin.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
@Component
public class ApplicationVersionService implements GenericService {

	@Autowired
	private Environment env;

	/**
	 * 
	 * @return the string 'null' if the version is not set otherwise the version
	 */
	public String getVersion() {
		return ""+env.getProperty("application.formatted-version");
	}

}
