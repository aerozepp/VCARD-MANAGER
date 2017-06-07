package kr.co.esjee.sjCardProject.admin.user.web;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.co.esjee.sjCardProject.admin.user.service.UserTblService;
import kr.co.esjee.sjCardProject.admin.user.service.UserTblVO;
import kr.co.esjee.sjCardProject.util.VCFGenerator;
import kr.co.esjee.sjCardProject.util.VCFParser;

@Controller
public class UserTblController {

	public static final Logger logger = LoggerFactory.getLogger(UserTblController.class);

	@Resource(name = "userTblService")
	private UserTblService userTblService;

	@Resource(name = "userTblVO")
	private UserTblVO userTblVO;

	@RequestMapping("/user/loginPage")
	public String loginPage() {
		return "/user/loginPage";
	}

	@RequestMapping("/user/logout")
	public String logoutPage() {
		return "/user/loginPage";
	}

	
	@RequestMapping("/user/userDetails")
	public String userDetails() {
		return "/user/userDetails";
	}

	@RequestMapping("/user/admin")
	public String userAdmin() {
		return "/user/admin";
	}

	@RequestMapping(value = "/user/memberList")
	public String showMemberList(HttpServletRequest request, Model model) throws Exception {
		return "/user/memberList";
	}

	/*
	 * @RequestMapping(value="/user/edit") public String
	 * editUser(HttpServletRequest request, Model model) throws Exception{
	 * return "/user/edit"; }
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome to sjCard");

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	/**
	 * @Method checktUser
	 * @Description
	 * 
	 * 				사용자명 중복체크
	 * 
	 * @author hyun JUNG
	 * @since 2017. 5. 11
	 * @param request
	 * @return String
	 * @throws IOException
	 */

	
	
	
	@ResponseBody
	@RequestMapping(value = "/user/checkUser")
	public String checkUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String username = request.getParameter("username");

		if (userTblService.checkUsername(username) == true) {
			return username;
		} else {
			return null;
		}

	}

	/**
	 * @Method insertUser
	 * @Description
	 * 
	 * 				userDetails.jsp에서 넘어온 request와 file을 사용자명 사용가능 확인 후 DB에 저장
	 * 
	 * @author hyun JUNG
	 * @since 2017. 4. 25.
	 * @param request
	 * @param file
	 * @param session
	 * @return String
	 */

	@RequestMapping(value = "/user/insertUser")
	public String insertUser(@RequestParam Map<String, Object> map,
			@RequestParam(value = "imageFile", required = false) CommonsMultipartFile file, HttpSession session,
			Model model) throws Exception {

		boolean insert = userTblService.insertUserTbl(map, file, session);

		model.addAttribute("signup", insert);

		return "redirect:/user/loginPage";
	}

	/**
	 * @Method parseVCF
	 * @Description
	 * 
	 * 				사용자가 업로드한 vcf파일을 parsing하여 각각의 property value를 view로 전달
	 * 
	 * @author hyun JUNG
	 * @since 2017. 4. 25.
	 * @param file
	 * @param session
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/user/parseVCF")
	public String parseVCF(@RequestParam("vcfFile") CommonsMultipartFile file, HttpSession session, Model model)
			throws Exception {

		VCFParser vcfParser = new VCFParser();

		userTblVO = vcfParser.vcfParse(file, session);

		model.addAttribute("userInfo", userTblVO);

		return "/user/userDetails";

	}

	/**
	 * @Method editUserInfo
	 * @Description
	 * 
	 *              <pre>
	 *    회원 정보 수정
	 *              </pre>
	 * 
	 * @author 정현
	 * @since 2017. 5. 16.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * @return String
	 */
	@RequestMapping(value = "/user/edit")
	public @ResponseBody UserTblVO editUserInfo(HttpServletRequest request, Model model) throws Exception {

		userTblVO = userTblService.selectUserTbl(request.getParameter("username"));

		return userTblVO;
	}

	/**
	 * @Method updatetUser
	 * @Description
	 * 
	 *              <pre>
	 *     회원 정보 수정
	 *              </pre>
	 * 
	 * @author 정현
	 * @since 2017. 5. 24.
	 * @param request
	 * @param file
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 * @return String
	 */
	@RequestMapping(value = "/user/updateUser")
	public String updatetUser(@RequestParam Map<String, Object> map,
			@RequestParam(value = "imageFile", required = false) CommonsMultipartFile file, HttpSession session,
			Model model) throws Exception {

		boolean update = userTblService.updateUserTbl(map, file, session);

		model.addAttribute("update", update);
		model.addAttribute("username", map.get("username"));

		return "redirect:/";
	}

	/**
	 * @Method deleteUser
	 * @Description
	 * 
	 *              <pre>
	 *     회원 정보 삭제
	 *              </pre>
	 * 
	 * @author 정현
	 * @since 2017. 5. 31.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/user/deleteUser")
	public @ResponseBody boolean deleteUser(HttpServletRequest request, HttpSession session)
			throws Exception {

		return userTblService.deleteUserTbl(request, session);

	}

	/**
	 * @Method retreiveMemberInfo
	 * @Description
	 * 
	 *              <pre>
	 *   회원 상세 정보 가져오기 
	 *              </pre>
	 * 
	 * @author 정현
	 * @since 2017. 5. 24.
	 * @param request
	 * @return
	 * @throws Exception
	 * @return UserTblVO
	 */

	@RequestMapping(value = "/user/retreiveMemberInfo")
	public @ResponseBody UserTblVO retreiveMemberInfo(HttpServletRequest request) throws Exception {

		String username = request.getParameter("username");
		userTblVO = userTblService.selectUserTbl(username);

		return userTblVO;
	}

	/**
	 * @Method searchMembers
	 * @Description
	 * 
	 *              <pre>
	 *   회원 검색 하기
	 *              </pre>
	 * 
	 * @author 정현
	 * @since 2017. 5. 24.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * @return List<Map<String,Object>>
	 */
	@RequestMapping(value = "/user/searchMembers")
	public @ResponseBody Map<String, Object> searchMembers(@RequestParam Map<String, Object> map, Model model)
			throws Exception {

		List<Map<String, Object>> list = userTblService.searchMembers(map);
		int count = userTblService.countSearchMembers(map);

		Map<String, Object> output = new HashMap<String, Object>();
		output.put("list", list);
		output.put("count", count);

		return output;
	}

	/**
	 * @Method downloadVCF
	 * @Description
	 * 
	 *              <pre>
	 *    vcf 다운로드
	 *              </pre>
	 * 
	 * @author 정현
	 * @since 2017. 5. 31.
	 * @param request
	 * @param session
	 * @param response
	 * @param model
	 * @throws Exception
	 * @return void
	 */
	@RequestMapping(value = "/user/downloadVCF")
	public void downloadVCF(HttpServletRequest request, HttpSession session, HttpServletResponse response, Model model)
			throws Exception {

		String username = request.getUserPrincipal().getName();

		userTblVO = userTblService.selectUserTbl(username);

		VCFGenerator vcfGenerator = new VCFGenerator();

		String vcard = vcfGenerator.writeVcard(userTblVO, session);

		vcfGenerator.exportVcard(request, response, vcard, username);
	}

	/**
	 * @Method downloadVCF
	 * @Description
	 * 
	 *              <pre>
	 *    관리자 계정 vcf 다운로드
	 *              </pre>
	 * 
	 * @author 정현
	 * @since 2017. 6. 4.
	 * @param request
	 * @param session
	 * @param response
	 * @throws Exception
	 * @return void
	 */
	@RequestMapping(value = "user/admin/downloadVCF")
	public void downloadVCF(HttpServletRequest request, HttpSession session, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("username");

		userTblVO = userTblService.selectUserTbl(username);

		VCFGenerator vcfGenerator = new VCFGenerator();

		String vcard = vcfGenerator.writeVcard(userTblVO, session);

		vcfGenerator.exportVcard(request, response, vcard, username);
	}

}
