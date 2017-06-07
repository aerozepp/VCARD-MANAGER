/**
 * 
 */
package kr.co.esjee.sjCardProject.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** kr.co.esjee.sjCardProject.util</br> 
* GetPropertyValue.java 
* 
* @Description<pre> 
* 	프로퍼티 파일 불러오기
* </pre> 
* @author 정현
* @since 2017. 6. 4.  
*/

@Service("getPropertyValue")
public class GetPropertyValue {

	public static final Logger logger = LoggerFactory.getLogger(GetPropertyValue.class);
	
	public String vcf_upload_dir;
	public String image_upload_dir;
	public String default_img_format;
	public String bad_credential;
	public String user_not_found;
	public String user_default_auth;
	public String page_limit;
	public String vcf_format;
	public String data_access_denied;
	public String no_image;
	public String id_available;
	public String id_unavailable;
	public String wrong_image;
	public String img_size_exceed;
	public String wrong_file;
	public String file_not_found;
	public String file_not_readable;
	
	public String getVcf_format() {
		return vcf_format;
	}

	String result = "";
	InputStream inputStream;
	
	/** @Method getPropertyValue 
	* @Description<pre> 
	* 	프로퍼티 파일을 읽어 프로퍼티값 불러오기
	* </pre> 
	* @author 정현
	* @since 2017. 6. 4. 
	* @return
	* @throws Exception 
	* @return String 
	*/
	public String getPropertyValue() throws Exception{
		
		try{
			Properties prop = new Properties();
			String propFileName = "sjcard_constant.properties";
			
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			vcf_upload_dir = prop.getProperty("VCF_UPLOAD_DIRECTORY");
			image_upload_dir = prop.getProperty("IMAGE_UPLOAD_DIRECTORY");
			default_img_format = prop.getProperty("DEFAULT_IMAGE_FORMAT");
			bad_credential = prop.getProperty("BAD_CREDENTIAL");
			user_not_found = prop.getProperty("USER_NOT_FOUND");
			user_default_auth = prop.getProperty("USER_DEFAULT_AUTH");
			page_limit = prop.getProperty("PAGE_LIMIT");
			vcf_format = prop.getProperty("VCF_FORMAT");
			data_access_denied = prop.getProperty("DATA_ACCESS_DENIED");
			no_image = prop.getProperty("NO_IMAGE");
			id_available = prop.getProperty("ID_AVAILABLE");
			id_unavailable = prop.getProperty("ID_UNAVAILABLE");
			wrong_image = prop.getProperty("WRONG_IMAGE");
			img_size_exceed = prop.getProperty("IMG_SIZE_EXCEED");
			wrong_file = prop.getProperty("WRONG_FILE"); 
			file_not_found = prop.getProperty("FILE_NOT_FOUND");
			file_not_readable = prop.getProperty("FILE_NOT_READABLE");
 						
		} catch(Exception e){
			logger.info("Exception : " + e);
		} finally {
			inputStream.close();
		}
		
		return result;
	}

	public String getFile_not_found() {
		return file_not_found;
	}

	public String getFile_not_readable() {
		return file_not_readable;
	}

	public String getWrong_file() {
		return wrong_file;
	}

	public String getWrong_image() {
		return wrong_image;
	}

	public String getImg_size_exceed() {
		return img_size_exceed;
	}

	public String getData_access_denied() {
		return data_access_denied;
	}

	public String getNo_image() {
		return no_image;
	}

	public String getId_available() {
		return id_available;
	}

	public String getId_unavailable() {
		return id_unavailable;
	}

	public static Logger getLogger() {
		return logger;
	}

	public String getVcf_upload_dir() {
		return vcf_upload_dir;
	}

	public String getImage_upload_dir() {
		return image_upload_dir;
	}

	public String getDefault_img_format() {
		return default_img_format;
	}

	public String getBad_credential() {
		return bad_credential;
	}

	public String getUser_not_found() {
		return user_not_found;
	}

	public String getUser_default_auth() {
		return user_default_auth;
	}

	public String getPage_limit() {
		return page_limit;
	}

	public String getResult() {
		return result;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
	
	
	
}
