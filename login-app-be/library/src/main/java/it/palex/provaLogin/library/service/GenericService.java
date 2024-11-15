package it.palex.provaLogin.library.service;

import it.palex.provaLogin.library.exceptions.StandardReturnCode;
import it.palex.provaLogin.library.rest.GenericResponse;
import org.springframework.http.HttpStatus;

/**
 * @author Alessandro Pagliaro
 *
 */
public interface GenericService extends BasicGenericService {

	default <T> GenericResponse<T> buildOkResponse(T data, int subcode){
		return new GenericResponse<T>(data, HttpStatus.OK.value(), "Success", subcode);
	}
	
	default <T> GenericResponse<T> buildOkResponse(T data, StandardReturnCode error) {
		if(error==null) {
			return this.buildOkResponse(data);
		}
		return this.buildOkResponse(data, error.getMess(), error.getCode());
	}
	
	default <T> GenericResponse<T> buildOkResponse(T data){
		return new GenericResponse<T>(data, HttpStatus.OK.value(), "Success");
	}
	
	default <T> GenericResponse<T> buildOkResponse(T data, String msg, int subcode){
		return new GenericResponse<T>(data, HttpStatus.OK.value(), msg, subcode);
	}
	
	default <T> GenericResponse<T> buildOkResponse(T data, String msg){
		return new GenericResponse<T>(data, HttpStatus.OK.value(), msg);
	}
	
	default <T> GenericResponse<T> buildBadDataResponse(){
		return new GenericResponse<T>(null, HttpStatus.BAD_REQUEST.value(), "Bad Data");
	}
	
	default <T> GenericResponse<T> buildBadDataResponse(String msg){
		return new GenericResponse<T>(null, HttpStatus.BAD_REQUEST.value(), msg);
	}
	
	default <T> GenericResponse<T> buildBadDataResponse(String msg, int subcode){
		return new GenericResponse<T>(null, HttpStatus.BAD_REQUEST.value(), msg, subcode);
	}
	
	default <T> GenericResponse<T> buildBadDataResponse(StandardReturnCode error) {
		if(error==null) {
			return this.buildBadDataResponse();
		}
		return this.buildBadDataResponse(error.getMess(), error.getCode());
	}

	default <T> GenericResponse<T> buildForbiddenResponse(){
		return new GenericResponse<T>(null, HttpStatus.FORBIDDEN.value(), "Forbidden");
	}
	
	default <T> GenericResponse<T> buildForbiddenResponse(String msg){
		return new GenericResponse<T>(null, HttpStatus.FORBIDDEN.value(), msg);
	}
	
	default <T> GenericResponse<T> buildForbiddenResponse(String msg, int subcode){
		return new GenericResponse<T>(null, HttpStatus.FORBIDDEN.value(), msg, subcode);
	}
	
	default <T> GenericResponse<T> buildForbiddenResponse(StandardReturnCode error) {
		if(error==null) {
			return this.buildForbiddenResponse();
		}
		return this.buildForbiddenResponse(error.getMess(), error.getCode());
	}
	
	
	default <T> GenericResponse<T> buildUnauthorizedResponse(){
		return new GenericResponse<T>(null, HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
	}
	
	default <T> GenericResponse<T> buildUnauthorizedResponse(String msg){
		return new GenericResponse<T>(null, HttpStatus.UNAUTHORIZED.value(), msg);
	}
	
	default <T> GenericResponse<T> buildUnauthorizedResponse(String msg, int subcode){
		return new GenericResponse<T>(null, HttpStatus.UNAUTHORIZED.value(), msg, subcode);
	}
	
	default <T> GenericResponse<T> buildUnauthorizedResponse(StandardReturnCode error) {
		if(error==null) {
			return this.buildUnauthorizedResponse();
		}
		return this.buildUnauthorizedResponse(error.getMess(), error.getCode());
	}
	
	
	
	default <T> GenericResponse<T> buildNotFoundResponse(){
		return new GenericResponse<T>(null, HttpStatus.NOT_FOUND.value(), "Not found");
	}
	
	default <T> GenericResponse<T> buildNotFoundResponse(String msg){
		return new GenericResponse<T>(null, HttpStatus.NOT_FOUND.value(), msg);
	}
	
	default <T> GenericResponse<T> buildNotFoundResponse(String msg, int subcode){
		return new GenericResponse<T>(null, HttpStatus.NOT_FOUND.value(), msg, subcode);
	}
	
	default <T> GenericResponse<T> buildNotFoundResponse(StandardReturnCode error) {
		if(error==null) {
			return this.buildNotFoundResponse();
		}
		return this.buildNotFoundResponse(error.getMess(), error.getCode());
	}
	
	
	default <T> GenericResponse<T> buildNotAcceptableResponse(){
		return new GenericResponse<T>(null, HttpStatus.NOT_ACCEPTABLE.value(), "Not Acceptable");
	}	
	
	default <T> GenericResponse<T> buildNotAcceptableResponse(String msg){
		return new GenericResponse<T>(null, HttpStatus.NOT_ACCEPTABLE.value(), msg);
	}
	
	default <T> GenericResponse<T> buildNotAcceptableResponse(String msg, int subcode){
		GenericResponse<T> res =  new GenericResponse<T>(null, HttpStatus.NOT_ACCEPTABLE.value(), msg, subcode);
		return res;
	}
	
	default <T> GenericResponse<T> buildNotAcceptableResponse(StandardReturnCode error) {
		if(error==null) {
			return this.buildNotAcceptableResponse();
		}
		return this.buildNotAcceptableResponse(error.getMess(), error.getCode());
	}
	
	
	default <T> GenericResponse<T> buildTooManyRequest(){
		return new GenericResponse<T>(null, HttpStatus.TOO_MANY_REQUESTS.value(), "Too Many Requests");
	}
	
	default <T> GenericResponse<T> buildTooManyRequest(String msg){
		return new GenericResponse<T>(null, HttpStatus.TOO_MANY_REQUESTS.value(), msg);
	}
	
	default <T> GenericResponse<T> buildTooManyRequest(String msg, int subcode){
		return new GenericResponse<T>(null, HttpStatus.TOO_MANY_REQUESTS.value(), msg, subcode);
	}
	
	default <T> GenericResponse<T> buildTooManyRequest(StandardReturnCode error) {
		if(error==null) {
			return this.buildTooManyRequest();
		}
		return this.buildTooManyRequest(error.getMess(), error.getCode());
	}
	
	
	default <T> GenericResponse<T> buildUnprocessableEntity(){
		return new GenericResponse<T>(null, HttpStatus.UNPROCESSABLE_ENTITY.value(), "Unprocessable Entity");
	}
	
	default <T> GenericResponse<T> buildUnprocessableEntity(String msg){
		return new GenericResponse<T>(null, HttpStatus.UNPROCESSABLE_ENTITY.value(), msg);
	}
	
	default <T> GenericResponse<T> buildUnprocessableEntity(String msg, int subcode){
		return new GenericResponse<T>(null, HttpStatus.UNPROCESSABLE_ENTITY.value(), msg, subcode);
	}
	
	default <T> GenericResponse<T> buildUnprocessableEntity(StandardReturnCode error) {
		if(error==null) {
			return this.buildUnprocessableEntity();
		}
		return this.buildUnprocessableEntity(error.getMess(), error.getCode());
	}
	
	default <T> GenericResponse<T> buildConflictEntity(){
		return new GenericResponse<T>(null, HttpStatus.CONFLICT.value(), "Entities are conflicting");
	}
	
	default <T> GenericResponse<T> buildConflictEntity(String msg){
		return new GenericResponse<T>(null, HttpStatus.CONFLICT.value(), msg);
	}
	
	default <T> GenericResponse<T> buildConflictEntity(StandardReturnCode error) {
		if(error==null) {
			return this.buildConflictEntity();
		}
		return this.buildConflictEntity( error.getMess(), error.getCode());
	}
	
	default <T> GenericResponse<T> buildConflictEntity(String msg, int subcode){
		return new GenericResponse<T>(null, HttpStatus.CONFLICT.value(), msg, subcode);
	}
	

	default <T> GenericResponse<T> buildInternalServerError(){
		return new GenericResponse<T>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
	}
	
	default <T> GenericResponse<T> buildInternalServerError(String msg){
		return new GenericResponse<T>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
	}
	
	default <T> GenericResponse<T> buildInternalServerError(String msg, int subcode){
		return new GenericResponse<T>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, subcode);
	}
	
	default <T> GenericResponse<T> buildInternalServerError(StandardReturnCode error) {
		if(error==null) {
			return this.buildInternalServerError();
		}
		return this.buildInternalServerError(error.getMess(), error.getCode());
	}
}

