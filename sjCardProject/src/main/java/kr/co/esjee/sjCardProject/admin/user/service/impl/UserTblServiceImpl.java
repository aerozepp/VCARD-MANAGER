package kr.co.esjee.sjCardProject.admin.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.co.esjee.sjCardProject.admin.user.service.UserTblService;
import kr.co.esjee.sjCardProject.admin.user.service.UserTblVO;
import kr.co.esjee.sjCardProject.util.Validation;
import kr.co.esjee.sjCardProject.util.GetPropertyValue;
import kr.co.esjee.sjCardProject.util.ImageUploader;
import kr.co.esjee.sjCardProject.util.ShaEncoder;
import kr.co.esjee.sjCardProject.util.VCFParser;

/**
 * @Class Name : UserTblServiceImpl.java
 * @Description : UserTblService Business Implement class
 * @Modification Information
 * 
 * @author 정현
 * @since 2017-04-28
 * @version 1.0
 * @see Copyright (C) All right reserved.
 */

@Service("userTblService")
public class UserTblServiceImpl implements UserTblService {

	@Resource(name = "userTblDao")
	private UserTblDao userTblDao;

	@Resource(name = "userTblVO")
	private UserTblVO userTblVO;

	@Resource(name = "shaEncoder")
	private ShaEncoder encoder;

	@Resource(name = "imageUploader")
	private ImageUploader imageUploader;

	@Resource(name = "vcfParser")
	private VCFParser vcfParser;
	
	@Resource(name="getPropertyValue")
	private GetPropertyValue getPropertyValue; 

	private static final Logger logger = LoggerFactory.getLogger(UserTblServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * kr.co.esjee.sjCardProject.admin.user.service.UserTblService#insertUserTbl
	 * (javax.servlet.http.HttpServletRequest,
	 * org.springframework.web.multipart.commons.CommonsMultipartFile,
	 * javax.servlet.http.HttpSession)
	 */

	@Override
	public boolean insertUserTbl(Map<String, Object> map, CommonsMultipartFile file, HttpSession session)
			throws Exception {

		Validation val = new Validation();
		getPropertyValue.getPropertyValue();

		String username = map.get("username").toString();
		//String username = "정현";
		String decodedImage = map.get("image").toString();
		String password = map.get("password").toString();
		String email = map.get("email").toString();
		//String tellnum = map.get("tellnum").toString();
	
		//String test = "@$#%@#$%@#$%@$#";
		
		boolean checkUsername = checkUsername(username);

		if (checkUsername != true || !val.checkUserID(username) || !val.checkEmail(email)) {
			throw new Exception();

		} else {

			if (file.getSize() != 0) {

				imageUploader.uploadImage(file, session, getPropertyValue.getImage_upload_dir(), username);
				map.replace("image", username + getPropertyValue.getDefault_img_format());

			} else {

				if (!decodedImage.equals("noImage")) {

					imageUploader.uploadBase64Image(session, decodedImage, username);
					map.replace("image", username + getPropertyValue.getDefault_img_format());
				}

			}

			map.put("authority", getPropertyValue.getUser_default_auth());
			map.put("enabled", 1);

			logger.info(map.toString());

			String encodedPassword = encoder.saltEncoding(password, username);

			map.replace("password", encodedPassword);

			int result = 0;

			try {
				result = userTblDao.insertUser(map);

				logger.info("result ===> {}", result);

			} catch (Exception e) {
				throw new DataAccessException(getPropertyValue.getData_access_denied()) {

					private static final long serialVersionUID = 1L;
				};
			}

			if (result == 1) {
				return true;
			} else {
				return false;
			}

			/*
			 * result = userTblDao.insertUser(map);
			 * 
			 * logger.info("result ===> {}", result);
			 * 
			 * throw new Exception();
			 */

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * kr.co.esjee.sjCardProject.admin.user.service.UserTblService#updateUserTbl
	 * (javax.servlet.http.HttpServletRequest,
	 * org.springframework.web.multipart.commons.CommonsMultipartFile,
	 * javax.servlet.http.HttpSession)
	 */
	@Override
	public boolean updateUserTbl(Map<String, Object> map, CommonsMultipartFile file, HttpSession session)
			throws Exception {

		Validation val = new Validation();
		getPropertyValue.getPropertyValue();

		String username = map.get("username").toString();
		String password = map.get("password").toString();

		logger.info("password : " + password);

		if (file.getSize() != 0) {

			vcfParser.deleteFile(session, getPropertyValue.getImage_upload_dir(),
					username + getPropertyValue.getDefault_img_format());
			imageUploader.uploadImage(file, session, getPropertyValue.getImage_upload_dir(), username);
			map.replace("image", username + getPropertyValue.getDefault_img_format());

		} else {
			logger.info(getPropertyValue.getNo_image());

		}

		String encodedPassword = encoder.saltEncoding(password, username);
		map.replace("password", encodedPassword);

		logger.info(map.toString());

		int result = 0;

		try {
			result = userTblDao.updateUser(map);

			logger.info("result ===> {}", result);

		} catch (Exception e) {
			throw new DataAccessException(getPropertyValue.getData_access_denied()) {

				private static final long serialVersionUID = 1L;
			};
		}

		if (result == 1) {
			return true;
		} else {
			return false;
		}

		/*
		 * result = userTblDao.updateUser(map);
		 * 
		 * logger.info("result ===> {}", result);
		 * 
		 * throw new Exception();
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * kr.co.esjee.sjCardProject.admin.user.service.UserTblService#checkUsername
	 * (java.lang.String)
	 */
	@Override
	public boolean checkUsername(String username) throws Exception{

		Map<String, Object> user = null;
		try{
			user = userTblDao.selectUser(username);
		}catch(Exception e){
			throw new Exception();
		}
		
		if (user == null) {
			logger.info(username + getPropertyValue.getId_available());

			return true;

		} else {
			logger.info(username + getPropertyValue.getId_unavailable());

			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * kr.co.esjee.sjCardProject.admin.user.service.UserTblService#selectUserTbl
	 * (java.lang.String)
	 */
	@Override
	public UserTblVO selectUserTbl(String username) throws Exception {

		userTblVO = userTblDao.getUserInfo(username);

		return userTblVO;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * kr.co.esjee.sjCardProject.admin.user.service.UserTblService#searchMembers
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> searchMembers(Map<String, Object> map) throws Exception {

		String input = map.get("input").toString().trim();
		
		if(input.equals("")){
			map.replace("input", null);
		}
		
		List<Map<String, Object>> list = userTblDao.listMembers(map);

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.esjee.sjCardProject.admin.user.service.UserTblService#
	 * countSearchMembers(java.lang.String, java.lang.String)
	 */
	@Override
	public int countSearchMembers(Map<String, Object> map) throws Exception {

		int count = userTblDao.countMembers(map);

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * kr.co.esjee.sjCardProject.admin.user.service.UserTblService#deleteUserTbl
	 * (javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public boolean deleteUserTbl(HttpServletRequest request, HttpSession session) throws Exception {

		getPropertyValue.getPropertyValue();
		
		String username = request.getParameter("username");
		
		int result = 0;

		try {
			result = userTblDao.deleteUser(username);

			logger.info("result ===> {}", result);

			vcfParser.deleteFile(session, getPropertyValue.getImage_upload_dir(),
					username + getPropertyValue.getDefault_img_format());
			
			if(username.equals(request.getUserPrincipal().getName())){
				session.invalidate();
				logger.info("session invalidated");
			}

		} catch (Exception e) {
			throw new DataAccessException(getPropertyValue.getData_access_denied()) {

				private static final long serialVersionUID = 1L;
			};
		}

		if (result == 1) {
			return true;
		} else {
			return false;
		}
		/*
		 * result = userTblDao.deleteUser(username);
		 * logger.info("result ===> {}", result); throw new Exception();
		 */

	}

}
