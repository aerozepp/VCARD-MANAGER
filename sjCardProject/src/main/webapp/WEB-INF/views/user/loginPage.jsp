<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	/**
	 * @Class Name : loginPage.jsp
	 * @Description : vCard 로그인 화면
	 * @Modification Information
	 *
	 * @author prettymuch(Hyun Jung)
	 * @since 2017.04.19
	 * @version 1.0
	 *
	 * Copyright (C) All right reserved.
	 */
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache"> -->
<title>sjCard login</title>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<link rel="stylesheet" type="text/css" href="resources/css/loginPage.css">


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/css/tether-theme-arrows-dark.css"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<script src="<c:url value='../js/loginPage.js'/>" type='text/javascript'></script>

<style>
 .container {
	margin-top: 20px;
}

.modal-header {
	background-color: grey;
	color: white;
}

.login-status {
	color: red;
}

.vcard-link {
	float: right;
}

.upload-status {
	color: red;
}

.errorMessage {
	color: red;
} 
</style>
</head>
<body>


	<fmt:setBundle basename="/properties/message" />

	<fmt:message key="message.password" var="noPass" />
	<fmt:message key="message.username" var="noUser" />
	<fmt:message key="message.pwdInvalid" var="pwdInvalid" />
	<fmt:message key="message.userNotFound" var="userNotFound" />

	<fmt:message key="message.emptyFile" var="noFile" />
	<fmt:message key="message.wrongFile" var="wrongFile" />
	<fmt:message key="message.logoutSucc" var="logoutSucc" />
	<fmt:message key="message.signupSucc" var="signupSucc" />
	<fmt:message key="message.sessionExceed" var="sessionExceed" />



	 <script>
	$(document).ready(function() {
	
	    $('#signupSuccess').modal('show');
	    $('#deleteSuccess').modal('show');
	    /**
	     * 로그인 버튼 이벤트
	     */
	    $("#btn-login").click(function() {

			var username = $("#login-username").val();
			var password = $("#login-password").val();
	
			if ($.trim(username).length <= 0) {
			    $(".login-status").text("${noUser}");
			} else if ($.trim(password).length <= 0) {
			    $(".login-status").text("${noPass}");
			} else {
	
			    $(".login-status").text("전송......");
	
			    var data = '';
	
			    $.ajax({
				url : "/user/login",
				dataType : 'html',
				method : "POST",
				data : {
				    username : username,
				    password : password
				},
				cache : false,
				success : function(message) {
				    data = $.parseJSON(message);
	
				    if (data.success == true) {
					window.location.replace("<c:url value='/?login=succ'/>");
				    } else if (data.success == false) {
	
					if (data.message == "Bad credentials") {
					    $(".login-status").text("${pwdInvalid}");
					} else if (data.message == "user_not_found") {
					    $(".login-status").text("${userNotFound}");
					} else if (data.message == "exceed") {
					    $(".login-status").text("${sessionExceed}");
					}
	
				    }
				},
				error : function() {
				    $(".login-status").text("로그인 에러 발생");
				}
			    });
			}
	    });

	    /**
	     * 회원가입시 vcf 파일 전송
	     */
	    $("#btn-signup-vcf").click(function() {

			var fileExtension = "vcf";
	
			if ($("#vcfUploadFile").get(0).files.length === 0) {
			    $(".upload-status").text("${noFile}");
	
			} else {
	
			    if ($("#vcfUploadFile").val().split('.').pop().toLowerCase() != fileExtension) {
					$(".upload-status").text("${wrongFile}");
			    } else {
					$("#parseVCFForm").submit();
			    }
			}
	    });
	    
	    $('#login-username').keypress(function(key) {
		    if( (key.keyCode != 8) && (key.keyCode != 9) && (key.charCode < 97 || key.charCode > 122) && (key.charCode < 48 || key.charCode > 57)){
			 	$(".login-status").text("특수문자 및 대문자는 사용할 수 없습니다.");
				return false;
		    }else{
				$(".login-status").text("");
				return true;
		    }
		});
	    
	});
    </script>

	<div class="container">

		<div class="panel panel-primary">
			<div class="panel-heading">SJCARD 로그인</div>

			<div class="panel-body">
				<form name="loginForm" id="userLoginForm" action="<c:url value="/user/login"/>" method="POST">

					<div>
						<label>사용자명 :&nbsp;</label>
						<input type="text" onpaste="return false" style='IME-MODE: disabled' onkeyup="this.value=this.value.replace(/[^a-z0-9]/g,'')" name="username" id="login-username" maxlength="15" placeholder="username"><br /> 
						<label>비밀번호 :&nbsp;</label>
						<input type="password" onpaste="return false" onkeyup="this.value=this.value.replace(/[^a-z0-9]/g,'')" style='IME-MODE: disabled' name="password" id="login-password" maxlength="10" placeholder="password"><br /> <br />
						<div class="login-status"></div>
						<div class="errorMessage"></div>
					</div>
					<div class="panel-footer">
						<span class="btn-group pull-right">
							<button type="button" id="btn-login">로그인</button>
							<button type="button" data-toggle="modal" data-target="#signup">회원가입</button>
						</span>
					</div>
				</form>
			</div>
		</div>
	</div>



	<!-- 파일 첨부 modal-->

	<div class="modal fade" id="signup">

		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">VCF 파일첨부</div>
				<form id="parseVCFForm" name="parseVCF" action="<c:url value="/user/parseVCF"/>" method="POST" enctype="multipart/form-data">

					<div class="modal-body">
						<label>VCF파일</label><input type="file" name="vcfFile" id="vcfUploadFile" value="VCF 파일 선택"><br />

						<div class="upload-status"></div>

						<div class="vcard-link">
							<p>
								<a href="<c:url value="https://www.x3internetsolutions.com/vcard_generator.php"/>" target="_blank">vCard가 없다면?</a>
							</p>
						</div>
					</div>

					<div class="modal-footer">
						<input type="button" value="전송" id="btn-signup-vcf" class="btn btn-default">
						<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					</div>
				</form>
			</div>

		</div>
	</div>

	<c:if test="${not empty param.signup}">
		<div class="modal fade" id="signupSuccess">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">VCF 파일 등록</div>
					<div class="modal-body">
						<c:if test="${param.signup == true}">
							<p>회원가입 되었습니다.</p>
						</c:if>
						<c:if test="${param.signup == false}">
							<p>회원가입중 에러가 발생하였습니다. 다시 시도해 주세요.</p>
						</c:if>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
					</div>
				</div>

			</div>
		</div>
	</c:if>

	<c:if test="${not empty param.deleteSucc}">
		<div class="modal fade" id="deleteSuccess">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">회원 삭제</div>
					<div class="modal-body">
						<c:if test="${param.deleteSucc == true}">
							<p>회원 정보가 삭제되었습니다.</p>
						</c:if>
						<c:if test="${param.deleteSucc == false}">
							<p>회원정보 삭제 중 에러가 발생하였습니다. 다시 시도해 주세요.</p>
						</c:if>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
					</div>
				</div>

			</div>
		</div>
	</c:if>

</body>
</html>