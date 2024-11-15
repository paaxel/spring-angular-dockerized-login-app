package it.palex.provaLogin.library.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/**
 *
 * @author Alessandro Pagliaro
 *
 */

public class UnauthorizedException extends CustomHttpException {

	private static final long serialVersionUID = 6379965730765578414L;
	
	public UnauthorizedException() {
        super();
    }
    
    public UnauthorizedException(StandardReturnCode error) {
		super(error);
	}
    
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    protected UnauthorizedException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}