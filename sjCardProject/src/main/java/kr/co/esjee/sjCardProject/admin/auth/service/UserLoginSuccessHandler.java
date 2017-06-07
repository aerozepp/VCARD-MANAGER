package kr.co.esjee.sjCardProject.admin.auth.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/** kr.co.esjee.sjCardProject.admin.auth.service</br> 
* UserLoginSuccessHandler.java 
* 
* @Description<pre> 
* 	로그인 성공시 처리
* </pre> 
* @author 정현
* @since 2017. 5. 16.  
*/
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
			throws IOException, ServletException {
	
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);

		String jsonString = om.writeValueAsString(map);

		OutputStream out = res.getOutputStream();
		out.write(jsonString.getBytes());
		
	}

}
