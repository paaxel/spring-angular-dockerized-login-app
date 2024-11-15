package it.palex.provaLogin.library.rest.aspects;

import it.palex.provaLogin.library.rest.GenericResponse;
import it.palex.provaLogin.library.service.ApplicationVersionService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
@Aspect
@Component
public class ApplicationVersionAdderToGenericResponse {

	@Autowired
	private ApplicationVersionService versionSrv;
	
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void controller() {
	}

	@Pointcut("execution(* *.*(..))")
	protected void allMethod() {
	}

	@Around("controller() && allMethod()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		
		if (joinPoint != null) {
			Object obj = null;
			
			try {
				obj = joinPoint.proceed();
			}catch(Exception e) {
				throw e;
			}
			
			if (obj != null) {
				if (obj instanceof ResponseEntity) {
					Object res = ((ResponseEntity<?>) obj).getBody();
					if(res!=null && res instanceof GenericResponse) {
						GenericResponse<?> park = ((GenericResponse<?>) res);
						if(park!=null) {
							park.setVersion(versionSrv.getVersion());
						}
					}
				}
			}
		
			return obj;
		} 
		
		return null;
	}

}
