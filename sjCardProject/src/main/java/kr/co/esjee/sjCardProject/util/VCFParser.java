package kr.co.esjee.sjCardProject.util;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Photo;
import kr.co.esjee.sjCardProject.admin.user.service.UserTblVO;

/**
 * @Class Name : VCFParser
 * @Description : VCFParser Util Class
 * 
 * @author 정현
 * @since 2017-04-28
 * @version 1.0
 * @see Copyright (C) All right reserved.
 */

@Service("vcfParser")
public class VCFParser {

	private static final Logger logger = LoggerFactory.getLogger(VCFParser.class);

	
	/** @Method vcfParse 
	* @Description<pre> 
	* 
	* </pre> 
	* @author 정현
	* @since 2017. 6. 4. 
	* @param file
	* @param session
	* @return
	* @throws Exception 
	* @return UserTblVO 
	*/
	public UserTblVO vcfParse(CommonsMultipartFile file, HttpSession session) throws Exception {
		
		//vcfWriteAndRead.writeFile(file, session);
		String vcardLine = readFile(file);
		
		return parseFile(vcardLine, session) ;			
	}
	/**
	 * @Method parseFile
	 * @Description
	 * 
	 * 
	 * 				인자로 전달된 vcard line 문자열에서 원하는 정보를 추출 사용 api :
	 *              https://github.com/mangstadt/ez-vcard
	 * 
	 * @author hyun JUNG
	 * @since 2017. 4. 25.
	 * @param vcardLine
	 * @param session
	 * @return UserTblVO
	 * @throws Exception 
	 */
	public UserTblVO parseFile(String vcardLine, HttpSession session) throws Exception {
		
		UserTblVO vo = new UserTblVO();
		GetPropertyValue getPropertyValue = new GetPropertyValue();
		getPropertyValue.getPropertyValue();

		if (vcardLine.equals("") || !vcardLine.contains("VCARD")) {
			throw new IOException();
		} else {

			VCard vcard = Ezvcard.parse(vcardLine).first();

			if (vcard.getStructuredName().getFamily() != null) {
				vo.setLastName(vcard.getStructuredName().getFamily());
			}

			if (vcard.getStructuredName().getGiven() != null) {
				vo.setGivenName(vcard.getStructuredName().getGiven());
			}

			if (vcard.getEmails().isEmpty() == false) {
				vo.setEmail(vcard.getEmails().get(0).getValue());
			}

			if (vcard.getTelephoneNumbers().isEmpty() == false) {
				vo.setTel_number(vcard.getTelephoneNumbers().get(0).getText());
			}

			if (vcard.getOrganization() != null) {
				vo.setCompany(vcard.getOrganization().getValues().get(0).toString());
			}

			if (vcard.getUrls().isEmpty() == false) {
				vo.setUrl(vcard.getUrls().get(0).getValue());
			}

			if (vcard.getAddresses().isEmpty() == false) {
				vo.setCountry(vcard.getAddresses().get(0).getCountry());
				vo.setCity(vcard.getAddresses().get(0).getLocality());
				vo.setAddress(vcard.getAddresses().get(0).getExtendedAddress());
			}

			if (vcard.getPhotos().isEmpty() == false) { // vcard에 사진이 포함되어 있을 경우
													
				try {
					for (Photo photo : vcard.getPhotos()) { //vcard image encoding type : BASE64
						
						String enc = Base64.getEncoder().encodeToString(photo.getData());
						vo.setImage(enc);
					}

				} catch (Exception e) {
					
					throw new IOException(getPropertyValue.getWrong_file());
				}
			}else{
				vo.setImage("noImage");
			}

			return vo;
		}

	}
	
	/**
	 * @Method writeFile 
	* @Description<pre> 
	* 	인자로 전달된 파일을 지정된 디렉토리에 저장
	* </pre> 
	* @author hyun JUNG
	* @since 2017. 4. 25. 
	* @param file
	* @param session
	* @param directory
	* @throws Exception 
	* @return String
	 */
	public boolean writeFile(CommonsMultipartFile file, HttpSession session) throws Exception {

		GetPropertyValue getPropertyValue = new GetPropertyValue();
		getPropertyValue.getPropertyValue();
		
		if (file != null) {

			ServletContext context = session.getServletContext();

			String path = context.getRealPath(getPropertyValue.getVcf_upload_dir());
			String fileName = file.getOriginalFilename();

			logger.info("file path : " + path + " " + fileName);

			byte[] bytes = file.getBytes();

			BufferedOutputStream stream = null;

			try {

				stream = new BufferedOutputStream(new FileOutputStream(new File(path + File.separator + fileName)));
				stream.write(bytes); 
				stream.flush();
				
			} catch (Exception e) {

				throw new IOException(getPropertyValue.getWrong_file());

				
			} finally {
				stream.close(); 
			}
			
			return true;
			
		} else{
			
			return false;
		}

	}

	/**
	 * @Method readFile 
	* @Description<pre> 
	* 	인자로 전달된 파일을 읽어 vcardParser API 포맷형식의 문자열로 반환 
	* </pre> 
	* @author hyun JUNG
	* @since 2017. 4. 25. 
	* @param file
	* @param session
	* @param directory
	* @return String
	 */
	public String readFile(CommonsMultipartFile file) throws Exception {

		GetPropertyValue getPropertyValue = new GetPropertyValue();
		getPropertyValue.getPropertyValue();
		
		String fileLine = null;
		String vcardLine = "";
		
		if(file.getSize() != 0){	

			try {
				
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));

				while ((fileLine = bufferedReader.readLine()) != null) {
					vcardLine += fileLine + "\r\n";
				
				}

				bufferedReader.close();

				return vcardLine;
				
			} catch (FileNotFoundException ex) {
				logger.info(getPropertyValue.getFile_not_found());

				return vcardLine;
			} catch (IOException ex) {
				logger.info(getPropertyValue.getFile_not_readable());

				return vcardLine;
			}

		}
		else{
			
			return vcardLine;
		}
		
	}
	
	/** @Method deleteFile 
	* @Description<pre> 
	* 
	* </pre> 
	* @author 정현
	* @since 2017. 6. 1. 
	* @param session
	* @param directory
	* @param filename
	* @return 
	* @return boolean 
	*/
	public boolean deleteFile(HttpSession session, String directory, String filename){
		
		ServletContext context = session.getServletContext();

		String path = context.getRealPath(directory);
		
		try{
			File deleteFile = new File(path + File.separator + filename);
			deleteFile.delete();
			
			return true;
			
		}catch(Exception e){
			
			
			return false;
		}
		
		
		
	}
}
