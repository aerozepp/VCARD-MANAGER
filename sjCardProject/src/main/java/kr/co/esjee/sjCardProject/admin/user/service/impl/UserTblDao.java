package kr.co.esjee.sjCardProject.admin.user.service.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.esjee.sjCardProject.admin.user.service.UserTblVO;

/**
 * @Class Name : UserDaoImpl.java
 * @Description : UserDaoImpl Business class
 * @Modification Information
 * 
 * @author 정현
 * @since 2017-04-28
 * @version 1.0
 * @see Copyright (C) All right reserved.
 */

@Repository("userTblDao")
public class UserTblDao extends SqlSessionDaoSupport {

	/**
	 * USER_TBL을 등록한다.
	 * 
	 * @param vo
	 *            - 등록할 정보가 담긴 UserTblVO
	 * @return 등록 결과
	 * @exception Exception
	 */
	public int insertUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return getSqlSession().insert("user.insertUser", map);
	}

	
	/** @Method updateUser 
	* @Description<pre> 
	* 
	* </pre> 
	* @author 정현
	* @since 2017. 5. 24. 
	* @param userTblVO
	* @return 
	* @return int 
	*/
	public int updateUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return getSqlSession().update("user.updateUser", map);
	}

	/**
	 * USER_TBL을 조회한다.
	 * 
	 * @param username
	 *            - username
	 * @return 등록 결과
	 * @exception Exception
	 */
	public Map<String, Object> selectUser(String username) {
		return getSqlSession().selectOne("user.selectUser", username);
	}

	/**
	 * USER_TBL의 모든 컬럼을 select 한다.
	 * 
	 * @param username
	 *            - username
	 * @return 등록 결과
	 * @exception Exception
	 */
	public UserTblVO getUserInfo(String username) {
		return getSqlSession().selectOne("user.getUserInfo", username);
	}

	/**
	 * @Method getMemberList
	 * @Description 가입된 모든 회원의 ID를 select한다.
	 * @author 정현
	 * @date 2017.0.16
	 * @return getSqlSession().selectOne("user.getMembers")
	 * @return_type Map<String,Object>
	 */
	public List<Map<String, Object>> listMembers(Map<String, Object> params) {

		return getSqlSession().selectList("user.listMembers", params);
	}

	/**
	 * @Method countMembers
	 * @Description
	 * 
	 * <pre>
	 *  
	*  총 가입 회원수를 구하기위한 select.
	 * </pre>
	 * 
	 * @author 정현
	 * @since 2017. 5. 16.
	 * @return
	 * @return List<Map<String,Object>>
	 */

	public int countMembers(Map<String, Object> params) {

		return getSqlSession().selectOne("user.countMembers", params);
		// return getSqlSession().selectList("user.countMembers");
	}

	/**
	 * @Method searchMembers
	 * @Description
	 		회원 정보 검색
	 * 
	 * @author 정현
	 * @since 2017. 5. 22.
	 * @param option
	 * @param order
	 * @param input
	 * @return
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> searchMembers(Map<String, Object> params) {

		return getSqlSession().selectList("user.listMembers", params);
	}

	/** @Method countSearchMembers 
	* @Description<pre> 
	* 
	* </pre> 
	* @author 정현
	* @since 2017. 5. 24. 
	* @param params
	* @return 
	* @return int 
	*/
	public int countSearchMembers(Map<String, Object> params) {

		return getSqlSession().selectOne("user.countMembers", params);
		// return getSqlSession().selectList("user.countMembers");
	}
	
	/** @Method deleteUser 
	* @Description<pre> 
	*		회원 정보 삭제
	* @author 정현
	* @since 2017. 6. 4. 
	* @param username
	* @return 
	* @return int 
	*/
	public int deleteUser(String username) {
		// TODO Auto-generated method stub
		return getSqlSession().delete("user.deleteUser", username);
	}


}