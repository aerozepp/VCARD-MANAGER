package kr.co.esjee.sjCardProject.admin.auth.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class UserLoginFailureHandler implements AuthenticationFailureHandler {


	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException auth)
			throws IOException, ServletException {


		ObjectMapper om = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);

		if (auth.getMessage().equals("Bad credentials")) {
			map.put("message", auth.getMessage());
		} else if (auth.getMessage().equals("user_not_found")) {
			map.put("message", auth.getMessage());
		} else if (auth.getMessage().equals("Maximum sessions of 1 for this principal exceeded")) {
			map.put("message", "exceed");
		}

		String jsonString = om.writeValueAsString(map);

		OutputStream out = res.getOutputStream();
		out.write(jsonString.getBytes());

	}

}
