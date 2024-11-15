package it.palex.provaLogin.library.rest;

import it.palex.provaLogin.library.exceptions.StandardReturnCodesEnum;
import it.palex.provaLogin.library.service.ApplicationVersionService;
import it.palex.provaLogin.library.exceptions.BadDataException;
import it.palex.provaLogin.library.exceptions.ForbiddenException;
import it.palex.provaLogin.library.exceptions.InternalServerErrorException;
import it.palex.provaLogin.library.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


/**
*
* @author Alessandro Pagliaro
* 
*/
@ControllerAdvice
public class CustomExceptionHandler {

	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CustomExceptionHandler.class);
	
	@Autowired
	private ApplicationVersionService applicationVersionSrv;


	@ExceptionHandler(Throwable.class)
    public final ResponseEntity<GenericResponse<String>> handleAllExceptions(Exception ex, WebRequest request){
    	GenericResponse<String> res = new GenericResponse<String>();
    	res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		res.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		res.setSubcode(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    	res.setVersion(applicationVersionSrv.getVersion());
    	res.setOperationIdentifier(null); //cannot identify the operation id

        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public final ResponseEntity<GenericResponse<String>> handleAccessDeniedException(
    		org.springframework.security.access.AccessDeniedException ex, WebRequest request){
    	GenericResponse<String> res = new GenericResponse<String>();
    	res.setCode(HttpStatus.UNAUTHORIZED.value());
		res.setSubcode(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    	res.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    	res.setVersion(applicationVersionSrv.getVersion());
		res.setOperationIdentifier(null);

        return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler({org.springframework.data.mapping.PropertyReferenceException.class,
			org.springframework.http.converter.HttpMessageNotReadableException.class,
			org.springframework.web.bind.MissingPathVariableException.class,
			org.springframework.web.bind.ServletRequestBindingException.class,
			org.springframework.beans.TypeMismatchException.class,
			org.springframework.http.converter.HttpMessageNotReadableException.class,
			org.springframework.web.bind.MethodArgumentNotValidException.class,
			org.springframework.web.bind.MissingServletRequestParameterException.class,
			org.springframework.validation.BindException.class
	})
    public final ResponseEntity<GenericResponse<String>> handleBadRequest(Exception ex, WebRequest request){
    	GenericResponse<String> res = new GenericResponse<String>();
    	res.setCode(HttpStatus.BAD_REQUEST.value());
		res.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
		res.setSubcode(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    	res.setVersion(applicationVersionSrv.getVersion());
    	res.setOperationIdentifier(null); //cannot identify the operation id

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
	public final ResponseEntity<GenericResponse<String>> handleNotFound(Exception ex, WebRequest request){
    	GenericResponse<String> res = new GenericResponse<String>();
    	res.setCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
		res.setSubcode(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    	res.setVersion(applicationVersionSrv.getVersion());
    	res.setOperationIdentifier(null); //cannot identify the operation id

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(org.springframework.web.HttpMediaTypeNotSupportedException.class)
	public final ResponseEntity<GenericResponse<String>> handleMediaTypeNotSupported(Exception ex, WebRequest request){
    	GenericResponse<String> res = new GenericResponse<String>();
    	res.setCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
		res.setMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
		res.setSubcode(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    	res.setVersion(applicationVersionSrv.getVersion());
    	res.setOperationIdentifier(null); //cannot identify the operation id

        return new ResponseEntity<>(res, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
	
	
	@ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
	public final ResponseEntity<GenericResponse<String>> handleMethodNotSupportedException(Exception ex, WebRequest request){
    	GenericResponse<String> res = new GenericResponse<String>();
    	res.setCode(HttpStatus.METHOD_NOT_ALLOWED.value());
		res.setMessage(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
		res.setSubcode(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    	res.setVersion(applicationVersionSrv.getVersion());
    	res.setOperationIdentifier(null); //cannot identify the operation id

        return new ResponseEntity<>(res, HttpStatus.METHOD_NOT_ALLOWED);
    }
	
	
	@ExceptionHandler(org.springframework.web.HttpMediaTypeNotAcceptableException.class)
	public final ResponseEntity<GenericResponse<String>> handleMediaTypeNotAcceptableException(Exception ex, WebRequest request){
    	GenericResponse<String> res = new GenericResponse<String>();
    	res.setCode(HttpStatus.NOT_ACCEPTABLE.value());
    	res.setMessage(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
		res.setSubcode(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    	res.setVersion(applicationVersionSrv.getVersion());
    	res.setOperationIdentifier(null); //cannot identify the operation id

        return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
    }
	
	
	@ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<GenericResponse<String>> handleForbiddenException(
			ForbiddenException ex, WebRequest request){
    	GenericResponse<String> res = new GenericResponse<String>();
    	res.setCode(HttpStatus.FORBIDDEN.value());
    	res.setOperationIdentifier(ex.getOperationUUID());
    	res.setSubcode(ex.getCode());
    	res.setMessage(ex.getMessage());
    	res.setVersion(applicationVersionSrv.getVersion());
        
        return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(BadDataException.class)
    public final ResponseEntity<GenericResponse<String>> handleBadDataException(
			BadDataException ex, WebRequest request){
    	GenericResponse<String> res = new GenericResponse<String>();
    	res.setCode(HttpStatus.BAD_REQUEST.value());
    	res.setOperationIdentifier(ex.getOperationUUID());
    	res.setSubcode(ex.getCode());
    	res.setMessage(ex.getMessage());
    	res.setVersion(applicationVersionSrv.getVersion());
        
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(InternalServerErrorException.class)
	public final ResponseEntity<GenericResponse<String>> handleInternalServerException(
			InternalServerErrorException ex, WebRequest request){
    	GenericResponse<String> res = new GenericResponse<String>();
    	res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    	res.setOperationIdentifier(ex.getOperationUUID());
    	res.setSubcode(ex.getCode());
    	res.setMessage(ex.getMessage());
    	res.setVersion(applicationVersionSrv.getVersion());
        
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

	@ExceptionHandler(UnauthorizedException.class)
	public final ResponseEntity<GenericResponse<String>> handleUnauthorizedException(
			UnauthorizedException ex, WebRequest request){
    	GenericResponse<String> res = new GenericResponse<String>();
    	res.setCode(HttpStatus.UNAUTHORIZED.value());
    	res.setOperationIdentifier(ex.getOperationUUID());
    	res.setSubcode(ex.getCode());
    	res.setMessage(ex.getMessage());
    	res.setVersion(applicationVersionSrv.getVersion());
        
        return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
    }

	
}
