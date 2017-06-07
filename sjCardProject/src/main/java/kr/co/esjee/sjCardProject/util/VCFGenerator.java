package kr.co.esjee.sjCardProject.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.ImageType;
import ezvcard.property.Address;
import ezvcard.property.Email;
import ezvcard.property.Photo;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;
import kr.co.esjee.sjCardProject.admin.user.service.UserTblVO;

/**
 * kr.co.esjee.sjCardProject.util</br>
 * VCFGenerator.java
 * 
 * @Description
 * 
 *              <pre>
 * vcard 파일을 만들어 export
 *              </pre>
 * 
 * @author 정현
 * @since 2017. 5. 30.
 */

@Service("vcfGenerator")
public class VCFGenerator {

	private static final Logger logger = LoggerFactory.getLogger(VCFGenerator.class);

	@Resource(name = "vcfParser")
	private VCFParser vcfParser;
	
	
	/** @Method writeVcard 
	* @Description<pre> 
	*   vcard 문서파일 작성
	* </pre> 
	* @author 정현
	* @since 2017. 6. 4. 
	* @param vo
	* @param session
	* @return
	* @throws Exception 
	* @return String 
	*/
	public String writeVcard(UserTblVO vo, HttpSession session) throws Exception {

		GetPropertyValue getPropertyValue = new GetPropertyValue();
		
		getPropertyValue.getPropertyValue();
		
		ServletContext context = session.getServletContext();

		VCard vcard = new VCard();

		StructuredName n = new StructuredName();
		String formattedName = vo.getGivenName() + " " + vo.getLastName();

		Address address = new Address();
		// Photo photo = null;

		if (vo.getLastName() != null) {
			n.setFamily(vo.getLastName());
		}

		if (vo.getGivenName() != null) {
			n.setGiven(vo.getGivenName());
		}

		vcard.setStructuredName(n);
		vcard.setFormattedName(formattedName);

		if (vo.getEmail() != null) {
			vcard.addEmail(new Email(vo.getEmail()));
		}

		if (vo.getTel_number() != null) {
			vcard.addTelephoneNumber(new Telephone(vo.getTel_number()));
		}

		if (vo.getCompany() != null) {
			vcard.setOrganization(vo.getCompany());
		}

		if (vo.getUrl() != null) {
			vcard.addUrl(vo.getUrl());
		}

		if (vo.getCountry() != null) {
			address.setCountry(vo.getCountry());
		}

		if (vo.getCity() != null) {
			address.setLocality(vo.getCity());
		}

		if (vo.getAddress() != null) {
			address.setExtendedAddress(vo.getAddress());
		}

		vcard.addAddress(address);

		if(vo.getImage() == null){
			
			logger.info("no image");
			
		}else if (vo.getImage().contains("jpg")) {

			String path = context.getRealPath(getPropertyValue.getImage_upload_dir());
			String fileName = vo.getImage();

			File imageFile = new File(path + File.separator + fileName);
			Photo photo = new Photo(imageFile, ImageType.JPEG);

			vcard.addPhoto(photo);

		} else {

			logger.info("no image");
		}

		String vcardOut = "";
		try {
			vcardOut = Ezvcard.write(vcard).version(VCardVersion.V4_0).go();

		} catch (Exception e) {
			throw new IOException(getPropertyValue.getWrong_file());
		}

		return vcardOut;

	}

	/** @Method exportVcard 
	* @Description<pre> 
	*   vcard 다운로드
	* </pre> 
	* @author 정현
	* @since 2017. 6. 1. 
	* @param request
	* @param response
	* @param vcard
	* @throws Exception 
	* @return void 
	*/
	public void exportVcard(HttpServletRequest request, HttpServletResponse response, String vcard, String filename) throws Exception {

		GetPropertyValue getPropertyValue = new GetPropertyValue();
		
		getPropertyValue.getPropertyValue();
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + filename + getPropertyValue.getVcf_format() + "\"");

		StringBuffer sb = new StringBuffer(vcard);
		InputStream in = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
		ServletOutputStream out = response.getOutputStream();

		byte[] outputByte = new byte[4096];

		while (in.read(outputByte, 0, 4096) != -1) {
			out.write(outputByte, 0, 4096);
		}
		in.close();
		out.flush();
		out.close();
	}

}
