package kr.co.esjee.sjCardProject.util;

import javax.annotation.Resource;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;


@Service("shaEncoder")
public class ShaEncoder {
	
	@Resource(name="passwordEncoder")
	private ShaPasswordEncoder encoder;

	/**
	 * @Method encoding
	 * @Description
	 *  	인자로 넘어온 문자열을 인코딩
	 * 
	 * @author hyun JUNG
	 * @since 2017. 4. 25.
	 * @param str
	 * @return String
	 */
	
	public String encoding(String str){
		return encoder.encodePassword(str,null);
	}

	/**
	 * @Method saltEncoding
	 * @Description
	 *  	인자로 넘어온 문자열을  솔트인코딩
	 * 
	 * @author hyun JUNG
	 * @since 2017. 4. 25.
	 * @param str
	 * @param salt
	 * @return String
	 */
	public String saltEncoding(String str,String salt){
		return encoder.encodePassword(str,salt);
	}
}