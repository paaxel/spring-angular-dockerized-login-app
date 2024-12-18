package it.palex.provaLogin.library.exceptions;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
public class ForbiddenException extends CustomHttpException {

	private static final long serialVersionUID = 6379965730765578414L;
	
	public ForbiddenException() {
        super();
    }
    
    public ForbiddenException(StandardReturnCode error) {
		super(error);
	}
    
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

    protected ForbiddenException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}