package it.palex.provaLogin.library.soap;

import it.palex.provaLogin.library.exceptions.BadDataException;
import it.palex.provaLogin.library.exceptions.ForbiddenException;
import it.palex.provaLogin.library.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;

public class CustomSoapExceptionHandler extends SoapFaultMappingExceptionResolver  {

    private static final QName CODE = new QName("statusCode");
    private static final QName SUBCODE = new QName("subcode");
    private static final QName DESCRIPTION = new QName("description");

    private static final int STANDARD_SUBCODE = 0;

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        SoapFaultDetail detail = fault.addFaultDetail();

        customizeUsingException(ex, detail);
    }
    private void customizeUsingException(Exception ex, SoapFaultDetail detail){
        if(ex instanceof UnauthorizedException){
            detail.addFaultDetailElement(CODE).addText( HttpStatus.UNAUTHORIZED.value()+"");
            detail.addFaultDetailElement(SUBCODE).addText(((UnauthorizedException) ex).getCode()+"");
            detail.addFaultDetailElement(DESCRIPTION).addText(ex.getMessage());
            return;
        }
        if(ex instanceof BadDataException){
            detail.addFaultDetailElement(CODE).addText( HttpStatus.BAD_REQUEST.value()+"");
            detail.addFaultDetailElement(SUBCODE).addText(((BadDataException) ex).getCode()+"");
            detail.addFaultDetailElement(DESCRIPTION).addText(ex.getMessage());
            return;
        }
        if(ex instanceof ForbiddenException){
            detail.addFaultDetailElement(CODE).addText( HttpStatus.FORBIDDEN.value()+"");
            detail.addFaultDetailElement(SUBCODE).addText(((ForbiddenException) ex).getCode()+"");
            detail.addFaultDetailElement(DESCRIPTION).addText(ex.getMessage());
            return;
        }
        if(ex instanceof org.springframework.security.access.AccessDeniedException){
            detail.addFaultDetailElement(CODE).addText( HttpStatus.UNAUTHORIZED.value()+"");
            detail.addFaultDetailElement(SUBCODE).addText(STANDARD_SUBCODE+"");
            detail.addFaultDetailElement(DESCRIPTION).addText(HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return;
        }
        detail.addFaultDetailElement(CODE).addText( HttpStatus.INTERNAL_SERVER_ERROR.value()+"");
        detail.addFaultDetailElement(SUBCODE).addText(STANDARD_SUBCODE+"");
        detail.addFaultDetailElement(DESCRIPTION).addText("Internal server error");
    }

}
