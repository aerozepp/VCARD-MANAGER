package kr.co.esjee.sjCardProject.admin.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @Class Name : UserTblService.java
 * @Description : UserTbl Business class
 * @Modification Information
 * 
 * @author 정현
 * @since 2017-04-28
 * @version 1.0
 * @see Copyright (C) All right reserved.
 */


@Service
public interface UserTblService {

	/**
	 * DB에서 사용자명을 중복확인한다.
	 * 
	 * @param username
	 * @return boolean
	 * @exception Exception
	 */
	
	boolean checkUsername(String username) throws Exception;
	
	/**
	 * USER_TBL을 등록한다.
	 * 
	 * @param username, password
	 * @return 등록 결과
	 * @exception Exception
	 */

	boolean insertUserTbl(Map<String, Object> map, CommonsMultipartFile file, HttpSession session) throws Exception;

	
	/** @Method updateUserTbl 
	* @Description<pre> 
	*   회원 정보 수정
	* </pre> 
	* @author 정현
	* @since 2017. 5. 24. 
	* @param request
	* @param file
	* @param session
	* @return
	* @throws Exception 
	* @return boolean 
	*/
	boolean updateUserTbl(Map<String, Object> map, CommonsMultipartFile file, HttpSession session) throws Exception;
	
	/**
	 * USER_TBL에서 정보를 select한다.
	 * 
	 * @param username
	 * @return 등록 결과
	 * @exception Exception
	 */
	UserTblVO selectUserTbl(String username) throws Exception;
	
	
	/** @Method searchMembers 
	* @Description<pre> 
	*   회원 정보 검색
	* </pre> 
	* @author 정현
	* @since 2017. 5. 24. 
	* @param option
	* @param input
	* @param curPage
	* @param order
	* @return
	* @throws Exception 
	* @return List<Map<String,Object>> 
	*/
	List<Map<String, Object>> searchMembers(Map<String, Object> map) throws Exception;
	
	
	/** @Method countSearchMembers 
	* @Description<pre> 
	*   총 회원수 검색 
	* </pre> 
	* @author 정현
	* @since 2017. 5. 24. 
	* @param option
	* @param input
	* @return
	* @throws Exception 
	* @return Map<String,Object> 
	*/
	int countSearchMembers(Map<String, Object> map) throws Exception;
	
	/** @Method deleteUserTbl 
	* @Description<pre> 
	*   회원 삭제
	* </pre> 
	* @author 정현
	* @since 2017. 5. 24. 
	* @param request
	* @return
	* @throws Exception 
	* @return boolean 
	*/
	boolean deleteUserTbl(HttpServletRequest request, HttpSession session) throws Exception;
	

}
