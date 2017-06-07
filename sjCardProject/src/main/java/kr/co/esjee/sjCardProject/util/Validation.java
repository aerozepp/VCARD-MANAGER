/**
 * 
 */
package kr.co.esjee.sjCardProject.util;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * kr.co.esjee.sjCardProject.admin.user.service.impl</br>
 * Validation.java
 * 
 * @Description
 * 
 * <pre>
 *    유효성 검사
 * </pre>
 * 
 * @author 정현
 * @since 2017. 6. 2.
 */
public class Validation {

	private static final Logger logger = LoggerFactory.getLogger(Validation.class);
	/** @Method checkUserID 
	* @Description<pre> 
	*   사용자 아이디 검사
	* </pre> 
	* @author 정현
	* @since 2017. 6. 2. 
	* @param username
	* @return 
	* @return boolean 
	*/
	public boolean checkUserID(String username) {

		//String test = "#$%asdfeA";
		//char[] userArray = test.toCharArray();
		
		if(username.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
			logger.info("사용자명 한글 입력");
			return false;
		}
		
		boolean result = true;
		char[] userArray = username.toCharArray();
		
		int userAscii;
		
		for(int i = 0 ; i < userArray.length ; i++){
			
			userAscii = Integer.valueOf(userArray[i]);
			
			logger.info(String.valueOf(userAscii));
			
			//소문자와 숫자만 입력
			if( (userAscii < 97 || userAscii > 122) && (userAscii < 48 || userAscii > 57) ){
				logger.info("사용자명 범위 제한");
				result = false;
			}
			
		
		}
		
		if(userArray.length > 15){
			
			result = false;
		}
		
		return result;
		
	}

	/** @Method checkEmail 
	* @Description<pre> 
	*   이메일 형식 검사
	* </pre> 
	* @author 정현
	* @since 2017. 6. 2. 
	* @param email
	* @return 
	* @return boolean 
	*/
	public boolean checkEmail(String email) {
		
		boolean result = true;
		char[] emailArray = email.toCharArray();
		int emailAscii;
		
		if(email.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
			logger.info("이메일 한글 입력");
			return false;
		}
		
		for(int i = 0 ; i < emailArray.length ; i++){
			
			emailAscii = Integer.valueOf(emailArray[i]);
			
			if(  emailAscii != 46 && emailAscii != 64 && (emailAscii < 97 || emailAscii > 122) && (emailAscii < 48 || emailAscii > 57) ){
				logger.info("이메일 범위 제한");
				result = false;
			}
		}
		
		return result;
	}

	/** @Method checkTellNum 
	* @Description<pre> 
	*    전화번호 검사
	* </pre> 
	* @author 정현
	* @since 2017. 6. 2. 
	* @param tellnum
	* @return 
	* @return boolean 
	*/
	public boolean checkTellNum(String tellnum) {

		boolean result = true;
		char[] tellArray = tellnum.toCharArray();
		int tellAscii;
		
		for(int i = 0 ; i < tellArray.length ; i++){
			
			tellAscii = Integer.valueOf(tellArray[i]);
			
			if( tellAscii != 45 && tellAscii < 48 || tellAscii > 57 ){
				result = false;
			}
		}
		
		return result;
	}


	/** @Method checkLength 
	* @Description<pre> 
	*    입력 제한 길이 검사
	* </pre> 
	* @author 정현
	* @since 2017. 6. 4. 
	* @param map
	* @return 
	* @return boolean 
	*/
	public boolean checkLength(Map <String, Object> map) {

		boolean result = false;
		Map<String, Object> mapToCheck = map;
		
		for(Map.Entry<String, Object> entry : mapToCheck.entrySet()){
			
			int length = (Integer) entry.getValue();
			logger.info("length : " + String.valueOf(length));
			
			if(length > 30){
				result = false;
			}else{
				result = true;
			}
		}
		
		return result;
	}

}
