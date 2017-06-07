/**
 * 
 */
package kr.co.esjee.sjCardProject.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


/** kr.co.esjee.sjCardProject.util</br> 
* ImageUploader.java 
* 
* @Description<pre> 
*    이미지 업로드
* </pre> 
* @author 정현
* @since 2017. 6. 4.  
*/
@Service("imageUploader")
public class ImageUploader {
	
	@Resource(name="getPropertyValue")
	private GetPropertyValue getPropertyValue; 
	
	/** @Method uploadImage 
	* @Description<pre> 
	*    일반적인 이미지 파일 업로드
	* </pre> 
	* @author 정현
	* @since 2017. 6. 4. 
	* @param file
	* @param session
	* @param directory
	* @param username
	* @return
	* @throws Exception 
	* @return boolean 
	*/
	public boolean uploadImage(CommonsMultipartFile file, HttpSession session, String directory, String username) throws Exception {

		getPropertyValue.getPropertyValue();
		
		if (file != null) {

			if(file.getSize() > 1000000){
				
				throw new Exception(getPropertyValue.getImg_size_exceed());
			}
			
			ServletContext context = session.getServletContext();

			String path = context.getRealPath(directory);
			String fileName = username + getPropertyValue.getDefault_img_format();

		//	logger.info("file path : " + path + " " + fileName);

			byte[] bytes = file.getBytes();

			BufferedOutputStream stream = null;

			try {

				stream = new BufferedOutputStream(new FileOutputStream(new File(path + File.separator + fileName)));
				stream.write(bytes); 
				stream.flush();
				
			} catch (IOException e) {

				throw new IOException(getPropertyValue.getWrong_image());
				
			} finally {
				stream.close(); 
			}
			
			return true;
			
		} else{
			
			return false;
		}

	
	}


	/** @Method uploadBase64Image 
	* @Description<pre> 
	*    Base64 이미지 업로드
	* </pre> 
	* @author 정현
	* @since 2017. 6. 4. 
	* @param session
	* @param base64
	* @param fileName
	* @return
	* @throws Exception 
	* @return boolean 
	*/
	public boolean uploadBase64Image(HttpSession session, String base64, String fileName) throws Exception {
		
		getPropertyValue.getPropertyValue();
		
		byte[] data = Base64.getDecoder().decode(base64);

		ServletContext context = session.getServletContext();
		
		String path = context.getRealPath(getPropertyValue.getImage_upload_dir());
		String file = fileName + getPropertyValue.getDefault_img_format();
		BufferedOutputStream stream = null;

		try {
			stream = new BufferedOutputStream(
					new FileOutputStream(new File(path + File.separator + file)));
			stream.write(data);

			stream.flush();

		} catch (Exception e) {

			throw new IOException(getPropertyValue.getWrong_image());

		} finally {
			stream.close();
		}
		
		return false;
	}

}
