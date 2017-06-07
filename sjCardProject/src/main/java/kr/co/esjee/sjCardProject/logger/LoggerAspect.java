/**
 * 
 */
package kr.co.esjee.sjCardProject.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * kr.co.esjee.sjCardProject.util</br>
 * LoggerAspect.java
 * 
 * @Description
 * 
 * <pre>
 *  
* 	AOP 로거
 * </pre>
 * 
 * @author 정현
 * @since 2017. 5. 19.
 */

//@Aspect
public class LoggerAspect {

	protected Log log = LogFactory.getLog(LoggerAspect.class);
	static String type = "";

	//@Around("execution(* kr.co.esjee..*Impl.*(..)) or execution(* kr.co.esjee..*Controller.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {

		type = joinPoint.getSignature().getDeclaringTypeName();

		log.info(type + "." + joinPoint.getSignature().getName() + "()");

		return joinPoint.proceed();
	}

}
