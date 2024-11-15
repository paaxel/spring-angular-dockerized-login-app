package it.palex.provaLogin.library;

import it.palex.provaLogin.library.exceptions.*;
import it.palex.provaLogin.library.rest.CustomExceptionHandler;
import it.palex.provaLogin.library.rest.GenericResponse;
import it.palex.provaLogin.library.service.ApplicationVersionService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomExceptionHandlerTests {

    private static final String APP_VERSION = "1.0.0.0.0";
    @Mock
    private ServletWebRequest mockRequest;

    @Mock
    private ApplicationVersionService mockApplicationService;
    @InjectMocks
    @Resource
    private CustomExceptionHandler handler;

    @BeforeEach
    public void setupMocks(){
        MockitoAnnotations.initMocks(this);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        when(mockApplicationService.getVersion()).thenReturn(APP_VERSION);

        this.mockRequest = new ServletWebRequest(request);
    }

    @Test
    public void checkHandleForbiddenException() {

        ForbiddenException ex = new ForbiddenException(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE);

        ResponseEntity<GenericResponse<String>> err = handler.handleForbiddenException(ex, this.mockRequest);

        Assertions.assertNull(err.getBody().getData());
        Assertions.assertTrue(err.getStatusCode()==HttpStatus.FORBIDDEN);
        Assertions.assertNull(err.getBody().getOperationIdentifier());
        Assertions.assertTrue(err.getBody().getCode()==HttpStatus.FORBIDDEN.value());
        Assertions.assertTrue(err.getBody().getMessage().equals(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getMess()));
        Assertions.assertTrue(err.getBody().getSubcode()==StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    }


    @Test
    public void checkHandleUnauthorizedException() {

        UnauthorizedException ex = new UnauthorizedException(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE);

        ResponseEntity<GenericResponse<String>> err = handler.handleUnauthorizedException(ex, this.mockRequest);

        Assertions.assertNull(err.getBody().getData());
        Assertions.assertTrue(err.getStatusCode()==HttpStatus.UNAUTHORIZED);
        Assertions.assertNull(err.getBody().getOperationIdentifier());
        Assertions.assertTrue(err.getBody().getCode()==HttpStatus.UNAUTHORIZED.value());
        Assertions.assertTrue(err.getBody().getMessage().equals(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getMess()));
        Assertions.assertTrue(err.getBody().getSubcode()==StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    }


    @Test
    public void checkHandleInternalServerErrorException() {

        InternalServerErrorException ex = new InternalServerErrorException(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE);

        ResponseEntity<GenericResponse<String>> err = handler.handleInternalServerException(ex, this.mockRequest);

        Assertions.assertNull(err.getBody().getData());
        Assertions.assertTrue(err.getStatusCode()==HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertNull(err.getBody().getOperationIdentifier());
        Assertions.assertTrue(err.getBody().getCode()==HttpStatus.INTERNAL_SERVER_ERROR.value());
        Assertions.assertTrue(err.getBody().getMessage().equals(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getMess()));
        Assertions.assertTrue(err.getBody().getSubcode()==StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    }

    @Test
    public void checkHandleInternalBadDataException() {

        BadDataException ex = new BadDataException(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE);

        ResponseEntity<GenericResponse<String>> err = handler.handleBadDataException(ex, this.mockRequest);

        Assertions.assertNull(err.getBody().getData());
        Assertions.assertTrue(err.getStatusCode()==HttpStatus.BAD_REQUEST);
        Assertions.assertNull(err.getBody().getOperationIdentifier());
        Assertions.assertTrue(err.getBody().getCode()==HttpStatus.BAD_REQUEST.value());
        Assertions.assertTrue(err.getBody().getMessage().equals(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getMess()));
        Assertions.assertTrue(err.getBody().getSubcode()==StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    }

    @Test
    public void checkHandleAccessDeniedException() {
        final String mess = "bad req";
        AccessDeniedException ex = new AccessDeniedException(mess);

        ResponseEntity<GenericResponse<String>> err = handler.handleAccessDeniedException(ex, this.mockRequest);

        Assertions.assertNull(err.getBody().getData());
        Assertions.assertTrue(err.getStatusCode()==HttpStatus.UNAUTHORIZED);
        Assertions.assertNull(err.getBody().getOperationIdentifier());
        Assertions.assertTrue(err.getBody().getCode()==HttpStatus.UNAUTHORIZED.value());
        Assertions.assertFalse(err.getBody().getMessage().equals(mess));
        Assertions.assertTrue(err.getBody().getSubcode()==StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    }


    @Test
    public void checkHandleMissingServletRequestParameterException() {
        final String mess = "bad req";
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException(mess, "String");

        ResponseEntity<GenericResponse<String>> err = handler.handleBadRequest(ex, this.mockRequest);

        Assertions.assertNull(err.getBody().getData());
        Assertions.assertTrue(err.getStatusCode()==HttpStatus.BAD_REQUEST);
        Assertions.assertNull(err.getBody().getOperationIdentifier());
        Assertions.assertTrue(err.getBody().getCode()==HttpStatus.BAD_REQUEST.value());
        Assertions.assertFalse(err.getBody().getMessage().equals(mess));
        Assertions.assertTrue(err.getBody().getSubcode()==StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    }

    @Test
    public void checkHandleAllExceptions() {
        final String mess = "bad req";
        RuntimeException ex = new RuntimeException(mess);

        ResponseEntity<GenericResponse<String>> err = handler.handleAllExceptions(ex, this.mockRequest);

        Assertions.assertNull(err.getBody().getData());
        Assertions.assertTrue(err.getStatusCode()==HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertNull(err.getBody().getOperationIdentifier());
        Assertions.assertTrue(err.getBody().getCode()==HttpStatus.INTERNAL_SERVER_ERROR.value());
        Assertions.assertFalse(err.getBody().getMessage().equals(mess));
        Assertions.assertTrue(err.getBody().getSubcode()==StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    }

    @Test
    public void checkHandleNoHandlerFoundException() {
        NoHandlerFoundException ex = new NoHandlerFoundException("POST", "report", new HttpHeaders());

        ResponseEntity<GenericResponse<String>> err = handler.handleNotFound(ex, this.mockRequest);

        Assertions.assertNull(err.getBody().getData());
        Assertions.assertTrue(err.getStatusCode()==HttpStatus.NOT_FOUND);
        Assertions.assertNull(err.getBody().getOperationIdentifier());
        Assertions.assertTrue(err.getBody().getCode()==HttpStatus.NOT_FOUND.value());
        Assertions.assertNotNull(err.getBody().getMessage());
        Assertions.assertTrue(err.getBody().getSubcode()==StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    }

    @Test
    public void checkHandleHttpMediaTypeNotSupportedException() {
        final String mess = "bad req";
        HttpMediaTypeNotSupportedException ex = new HttpMediaTypeNotSupportedException(mess);

        ResponseEntity<GenericResponse<String>> err = handler.handleMediaTypeNotSupported(ex, this.mockRequest);

        Assertions.assertNull(err.getBody().getData());
        Assertions.assertTrue(err.getStatusCode()==HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        Assertions.assertNull(err.getBody().getOperationIdentifier());
        Assertions.assertTrue(err.getBody().getCode()==HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        Assertions.assertFalse(err.getBody().getMessage().equals(mess));
        Assertions.assertTrue(err.getBody().getSubcode()==StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    }

    @Test
    public void checkHandleHttpRequestMethodNotSupportedException() {
        final String mess = "bad req";
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException(mess);

        ResponseEntity<GenericResponse<String>> err = handler.handleMethodNotSupportedException(ex, this.mockRequest);

        Assertions.assertNull(err.getBody().getData());
        Assertions.assertTrue(err.getStatusCode()==HttpStatus.METHOD_NOT_ALLOWED);
        Assertions.assertNull(err.getBody().getOperationIdentifier());
        Assertions.assertTrue(err.getBody().getCode()==HttpStatus.METHOD_NOT_ALLOWED.value());
        Assertions.assertFalse(err.getBody().getMessage().equals(mess));
        Assertions.assertTrue(err.getBody().getSubcode()==StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    }

    @Test
    public void checkHandleHttpMediaTypeNotAcceptableException() {
        final String mess = "bad req";
        HttpMediaTypeNotAcceptableException ex = new HttpMediaTypeNotAcceptableException(mess);

        ResponseEntity<GenericResponse<String>> err = handler.handleMediaTypeNotAcceptableException(ex, this.mockRequest);

        Assertions.assertNull(err.getBody().getData());
        Assertions.assertTrue(err.getStatusCode()==HttpStatus.NOT_ACCEPTABLE);
        Assertions.assertNull(err.getBody().getOperationIdentifier());
        Assertions.assertTrue(err.getBody().getCode()==HttpStatus.NOT_ACCEPTABLE.value());
        Assertions.assertFalse(err.getBody().getMessage().equals(mess));
        Assertions.assertTrue(err.getBody().getSubcode()==StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
    }


}
