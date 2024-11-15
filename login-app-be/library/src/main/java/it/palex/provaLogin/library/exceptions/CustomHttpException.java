package it.palex.provaLogin.library.exceptions;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
public abstract class CustomHttpException extends RuntimeException {

	private static final long serialVersionUID = -8852455049339365295L;
	
	protected int code;
	protected String operationUUID;
	
	public CustomHttpException() {
		super();
	}
	
	public CustomHttpException(StandardReturnCode error) {
		super(error.getMess());
		
		this.code = error.getCode();
	}
	
	public CustomHttpException(String mess) {
		super(mess);
	}
	
	public CustomHttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomHttpException(Throwable cause) {
        super(cause);
    }

    protected CustomHttpException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
	public String getOperationUUID() {
		return operationUUID;
	}

	public void setOperationUUID(String operationUUID) {
		this.operationUUID = operationUUID;
	}
	
	public int getCode() {
		return code;
	}
	
}

