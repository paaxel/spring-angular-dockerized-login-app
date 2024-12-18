package it.palex.provaLogin.library.exceptions;

/**
 *
 * @author Alessandro Pagliaro
 *
 */

public class BadDataException extends CustomHttpException {

	private static final long serialVersionUID = 6379965730765578414L;
	
	public BadDataException() {
        super();
    }
    
    public BadDataException(StandardReturnCode error) {
		super(error);
	}
    
    public BadDataException(String message) {
        super(message);
    }

    public BadDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadDataException(Throwable cause) {
        super(cause);
    }

    protected BadDataException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}