package kr.co.esjee.sjCardProject.admin.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.co.esjee.sjCardProject.util.Validation;

public class UserAuthenticationService implements UserDetailsService {

	private SqlSessionTemplate sqlSession;

	public UserAuthenticationService(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	} 
	

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Map<String, Object> user = sqlSession.selectOne("user.selectUser", username);
		
		List<GrantedAuthority> grantedAuth = new ArrayList<GrantedAuthority>();

		if (user == null) {
			
			throw new BadCredentialsException("user_not_found");
			//throw new UsernameNotFoundException("user_not_found");
			
		}

		grantedAuth.add(new SimpleGrantedAuthority(user.get("AUTHORITY").toString()));

		return new UserDetailsVO(user.get("USERNAME").toString(), user.get("PASSWORD").toString(), true, true, true,
				true, grantedAuth);
	}

}
