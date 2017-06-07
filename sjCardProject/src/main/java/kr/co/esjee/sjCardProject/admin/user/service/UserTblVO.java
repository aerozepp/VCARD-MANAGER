package kr.co.esjee.sjCardProject.admin.user.service;

import org.springframework.stereotype.Repository;

/**
 * @Class Name : UserTblVO.java
 * @Description : UserTbl VO class
 * @Modification Information
 *
 * @author 정현
 * @since 2017-04-28
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
@Repository("userTblVO")
public class UserTblVO {

	private String user_id;
	private String password;
	
	private String lastName;
	private String givenName;
	private String email;
	private String tel_number;
	private String company;
	private String url;
	private String country;
	private String city;
	private String image;
	private String vcf;
	private String authority;
	private int enabled;
	
	private String tempImage;
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel_number() {
		return tel_number;
	}

	public void setTel_number(String tellNum) {
		this.tel_number = tellNum;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	private String address;
	
	
	public String getUsername() {
		return user_id;
	}
	public void setUsername(String username) {
		this.user_id = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getVcf() {
		return vcf;
	}

	public void setVcf(String vcf) {
		this.vcf = vcf;
	}
	
	

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
	

	public String getTempImage() {
		return tempImage;
	}

	public void setTempImage(String tempImage) {
		this.tempImage = tempImage;
	}

	@Override
	public String toString() {
		return "UserTblVO [username=" + user_id + 
						", password=" + password + 
						", email=" + email + 
						", company=" + company + 
						", tellNum=" + tel_number + 
						", country=" + country +
						", city=" + city +
						", address=" + address +
						", url=" + url + 
						", image=" + image + "]";
	}
	
	
	
	
	
}
