/**
 * 
 */
package kr.co.esjee.sjCardProject.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/** kr.co.esjee.sjCardProject.exception</br> 
* ExceptionController.java 
* 
* @Description<pre> 
*  예외처리
* </pre> 
* @author 정현
* @since 2017. 6. 4.  
*/

@EnableWebMvc
@ControllerAdvice
public class ExceptionController {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	/** @Method handleAllException 
	* @Description<pre> 
	*    일반적인 예외처리 
	* </pre> 
	* @author 정현
	* @since 2017. 6. 4. 
	* @param ex
	* @return 
	* @return ModelAndView 
	*/
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {
		logger.info("Exception : {}",ex);
		logger.info("LocalizedMessage : {}",ex.getLocalizedMessage());
		logger.info("Message : {}",ex.getMessage());
		logger.info("StackTrace : {}",ex.getStackTrace());
		
		ModelAndView model = new ModelAndView("error/generic_error");
		model.addObject("errMsg", "에러가 발생하였습니다. 관리자에게 문의해주세요");
		return model;

	}

	/** @Method handleCustomException 
	* @Description<pre> 
	*   IOException 처리
	* </pre> 
	* @author 정현
	* @since 2017. 6. 4. 
	* @param ex
	* @return 
	* @return ModelAndView 
	*/
	@ExceptionHandler(IOException.class)
	public ModelAndView handleCustomException(IOException ex) {

		logger.info("Exception : {}",ex);
		logger.info("LocalizedMessage : {}",ex.getLocalizedMessage());
		logger.info("Message : {}",ex.getMessage());
		logger.info("StackTrace : {}",ex.getStackTrace());
		ModelAndView model = new ModelAndView("error/generic_error");

		model.addObject("errMsg", "업로드한 파일이 올바른 파일이 아닙니다.");

		return model;

	}

	/** @Method handleDataException 
	* @Description<pre> 
	*   DataAceessException처리
	* </pre> 
	* @author 정현
	* @since 2017. 6. 4. 
	* @param ex
	* @return 
	* @return ModelAndView 
	*/
	@ExceptionHandler(DataAccessException.class)
	public ModelAndView handleDataException(DataAccessException ex) {

		ModelAndView model = new ModelAndView("error/generic_error");

		logger.info("Exception : {}",ex);
		logger.info("LocalizedMessage : {}",ex.getLocalizedMessage());
		logger.info("Message : {}",ex.getMessage());
		logger.info("StackTrace : {}",ex.getStackTrace());
		model.addObject("errMsg", "데이터 접근이 거부되었습니다. 관리자에게 문의해주세요.");

		return model;

	}

}
