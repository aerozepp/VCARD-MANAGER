<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	request.setCharacterEncoding("UTF-8");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	/**
	 * @Class Name : userDetails.jsp
	 * @Description : vCard 회원정보 수정화면
	 * @Modification Information
	 *
	 * @author 정현
	 * @since 2017.04.19
	 * @version 1.0
	 *
	 * Copyright (C) All right reserved.
	 */
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">

<title>sjCard admin page</title>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">


<style>
.container {
	margin-top: 20px;
}

.headline {
	margin-bottom: 10px;
	font-size: large;
	font-weight: bold;
	color: blue;
	font-size: large;
}

li {
	margin-left: 20px;
}

.log {
	color: red;
}

.modal-header {
	background-color: grey;
	color: white;
}

#username-status {
	color: red;
}

#password-status {
	color: red;
}

#confirm-status {
	color: red;
}

#addInfo-status {
	color: red;
}

.exportStatus {
	color: red;
}

.upload-status {
	color: red;
}

.panel-heading {
	background: grey;
}

ul {
	margin-top: 20px;
	list-style-type: none;
}

/* #thumbnail{
	background : red;
	border : 1px solid black;
	width : 100px;
	height : 100px;
} */
</style>

</head>
<body>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/css/tether-theme-arrows-dark.css"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

	<fmt:setBundle basename="/properties/message" />
	<fmt:message key="message.password" var="noPass" />
	<fmt:message key="message.shortPassword" var="shortPass" />
	<fmt:message key="message.username" var="noUser" />
	<fmt:message key="message.shortUsername" var="shortUser" />
	<fmt:message key="message.pwdInvalid" var="passInvalid" />
	<fmt:message key="message.confirmPass" var="confirmPass" />

	<fmt:message key="message.userAvailable" var="userAvailable" />
	<fmt:message key="message.userDenied" var="userDenied" />
	<fmt:message key="message.userCheck" var="userCheck" />

	<fmt:message key="message.parceSucc" var="parceSucc" />
	<fmt:message key="message.parceFail" var="parceFail" />

	<script>
	
	//이미지 업로드 실시간 view
	function showMyImage(fileInput) {
	    var files = fileInput.files;
	    for (var i = 0; i < files.length; i++) {
		var file = files[i];
		var imageType = /image.*/;
		if (!file.type.match(imageType)) {
		    continue;
		}
		var img = document.getElementById("thumbnail");
		img.file = file;
		var reader = new FileReader();
		reader.onload = (function(aImg) {
		    return function(e) {
			aImg.src = e.target.result;
		    };
		})(img);
		reader.readAsDataURL(file);
	    }
	}
	
	function validation(key){
	    
	    if( (key.keyCode != 8) && (key.keyCode != 9) && (key.charCode < 97 || key.charCode > 122) && (key.charCode < 48 || key.charCode > 57) ){
			return false;
    	}else{
			return true;
    	}
	    
	}
	
	function retreiveUserDetails(){
	    var username = $("#username").val();
	    
	    $.ajax({
		    url : "/user/edit",
		    method : "POST",
		    dataType : "json",
		    data : {username : username},
		    success : function(data){
			
				showUserDetails(data);
		    }
	    });
			
	}
	
	function showUserDetails(data){
	  
	    $("#signup-lastName").val(data.lastName);
	    $("#signup-givenName").val(data.givenName);
	    $("#signup-email").val(data.email);
	    $("#signup-tellNum").val(data.tel_number);
	    $("#signup-company").val(data.company);
	    $("#signup-url").val(data.url);
	    $("#hiddenImage").val(data.image);
	    
	    if(data.image == "noImage" || data.image == null){
			$("#thumbnail").attr("src", "<c:url value='/images/default-user-image.png'/>" );
	    }else{
			$("#thumbnail").attr("src", "<c:url value='${pageContext.request.contextPath}/images/"+ data.image +"'/>");
	    }
	      
	}
	
	$(document).ready(function() {
	
	    retreiveUserDetails();
	    
	    var addInfo = 0;
	    
	    $('#signup-username').keypress(function(key) {
		
			if(validation(key) == false){
			    $("#username-status").text("특수문자 및 대문자는 사용할 수 없습니다.");
			    return false;
			}else{
			    $("#username-status").text("");
			    return true;
			}
		});
	    
	    $('.addInfo').keypress(function(key){
			if(validation(key) == false){
		    	$("#addInfo-status").text("특수문자 및 대문자는 사용할 수 없습니다.");
		    	return false;
			}else{
		    	$("#addInfo-status").text("");
		    return true;
			}
	    });
	    
	    $('#signup-email').keypress(function(key){
			if( (key.charCode != 64) && (key.keyCode != 8) && (key.keyCode != 9) 
				 && (key.charCode != 46) && (key.charCode < 97 || key.charCode > 122) && (key.charCode < 48 || key.charCode > 57) 
				 && (key.keyCode <= 12687 || key.keyCode >= 12592) ) {
			    $("#addInfo-status").text("@를 제외한 특수문자 및 대문자는 사용할 수 없습니다.");
				return false;
			}else{
			    $("#addInfo-status").text("");
				return true;
			}
	    });
	    
	    $('#signup-tellNum').keypress(function(key){
			if( (key.keyCode != 8) && (key.keyCode != 9) && (key.charCode != 45) && (key.charCode < 48 || key.charCode > 57)){
		    	$("#addInfo-status").text("숫자와 '-'만 입력할 수 있습니다.'");
				return false;
			}else{
		    	$("#addInfo-status").text("");
				return true;
			}
    	});
	    
	    $('#signup-url').keypress(function(key){
			if( (key.keyCode != 8) && (key.keyCode != 9) && (key.charCode != 45) && (key.charCode != 46) && (key.charCode != 58) && (key.charCode != 47) && (key.charCode < 48 || key.charCode > 57)  && (key.charCode < 97 || key.charCode > 122)){
		    	$("#addInfo-status").text("URL 주소 형식에서 벗어나는 문자는 사용할 수 없습니다.");
				return false;
			}else{
		    	$("#addInfo-status").text("");
				return true;
			}
		});
	    
	    $('#signup-password').keypress(function(){
		 	$("#password-status").text("");
	    });
	    
	    $('#signup-confirm').keypress(function(){
	 		$("#confirm-status").text("");
    	});
		   	    
	    $("#btn-signup-cancel").click(function() {
			window.location.replace("memberList");
	    });
	   
	    
	    $("#btn-edit").click(function() {
		
			var password = $("#pw-edit").val();
			var pw_confirm = $("#signup-confirm").val();
			    
			if ($.trim(password).length <= 0) {
			    $("#password-status").text("${noPass}");
			} else if ($.trim(password).length < 5) {
			    $("#password-status").text("${shortPass}");
			} else if ($.trim(pw_confirm).length <= 0) {
			    $("#confirm-status").text("${confirmPass}");
			} else if (password != pw_confirm) {
			    $("#confirm-status").text("${passInvalid}");
			} else{
			    $("#confirm-status").text("");
				$("#editUser").modal("show");
		    }
		    	
	    });
	    
	    $("#confirmDelete").click(function(){
		
			var username = $("#signup-username").val();
			
			$.ajax({
			    
			    url : "/user/deleteUser",
			    dataType : "json",
				method : "POST",
				data : {
				    username : username
				},
				success : function(data) {
				    
				    if(data.result == true){
				    	window.location.replace("<c:url value='memberList?deleteSucc=true'/>");
				    
				    }else{
						alert("회원 삭제 에러");
				    }
				}
			});
			
	    });
	    
	    $(".addInfo").change(function(){
			addInfo = 1;
	    });
		
	    $("#btn-vcf").click(function() {
		
			if(addInfo == 1){
			    $(".exportStatus").text("회원정보 수정 버튼을 확인해주세요.");
			}else{
			    $(".exportStatus").text("");
			    $("#exportVCF").modal("show");    
			}	
    	});
	    	
	    $("#confirmDownload").click(function(){
			$("#exportVCF").modal("hide");
	    });
	    
	    $("#btn-leave").click(function() {
			$("#deleteUser").modal("show");
	    });
	    
	    $("#confirmEdit").click(function(){
			addInfo = 0;
			$("#insertUserForm").submit();
	    });
	    
	    $("#btn-edit-cancel").click(function() {
			window.location.replace("<c:url value='memberList'/>");
    	});

	});
	
	
    </script>

	<input type="hidden" value="${param.username}" id="username" />

	<section id="menuPanel"> <nav class="navbar navbar-inverse fluid navbar-static-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/">sjCard</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<c:if test="${not empty pageContext.request.userPrincipal}">

				<ul class="nav navbar-nav navbar-right">
					<li class="listMembers"><a href="<c:url value="/user/memberList"/>"><span class="glyphicon glyphicon-th-list"></span>&nbsp회원목록</a></li>
					<li><a href="<c:url value="/user/userDetails"/>"><span id="session_name" class="glyphicon glyphicon-user"> ${pageContext.request.userPrincipal.name} </span></a></li>
					<li><a href="<c:url value="/user/logout"/>"><span class="glyphicon glyphicon-log-out"></span>&nbsp로그아웃</a></li>
				</ul>
			</c:if>

			<c:if test="${empty pageContext.request.userPrincipal}">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="<c:url value="/user/loginPage"/>"><span class="glyphicon glyphicon-log-in"></span> 로그인</a></li>
				</ul>
			</c:if>
		</div>
	</div>
	</nav> </section>



	<div class="container">
		<div class="panel-primary">

			<div class="panel-heading">SJCARD 관리자 페이지</div>

			<form name="userInfoForm" id="insertUserForm" action="<c:url value="/user/updateUser"/>" method="POST" enctype="multipart/form-data">

				<div class="panel-body">

					<img id="thumbnail" name="image" src="" style="width: 20%; height: auto;" alt="userImage" /> <input type="hidden" name="image" id="hiddenImage"> <input type="file" name="imageFile" class="addInfo" accept="image/jpeg" id="imageUploadFile" onchange="showMyImage(this)" />
					<p class="upload-status"></p>

					<ul>
						<li class="headline">필수정보</li>

						<li><label>사용자명(변경불가) :&nbsp;</label> <input type="text" onkeyup="this.value=this.value.replace(/[^a-z0-9]/g,'')" name="username" style="background: #FFCCCC;" id="signup-username" maxlength="15" value="${param.username}" readonly>
							<p id="username-status"></p></li>
						<li><label>비밀번호 변경 :&nbsp;</label><input type="password" onkeyup="this.value=this.value.replace(/[^a-z0-9]/g,'')" onpaste="return false" style='IME-MODE: disabled' name="password" class="status" id="pw-edit" placeholder="비밀번호 10자 이내" maxlength="10">
							<p id="password-status"></p></li>


						<li><label>번호확인 :&nbsp;</label><input type="password" onkeyup="this.value=this.value.replace(/[^a-z0-9]/g,'')" onpaste="return false" style='IME-MODE: disabled' name="password-confirm" class="status" id="signup-confirm" placeholder="비밀번호 10자 이내" maxlength="10">
							<p id="confirm-status"></p></li>

						<li class="headline">부가정보</li>
						<li><label>&emsp;&emsp;&emsp;성 :&nbsp;</label><input type="text" onpaste="return false" name="lastName" class="addInfo" id="signup-lastName" size="20" maxlength="30" value="${userInfo.lastName}"></li>
						<li><label>&emsp;&emsp;이름 :&nbsp;</label><input type="text" onpaste="return false" name="givenName" class="addInfo" id="signup-givenName" size="20" maxlength="30" value="${userInfo.givenName}"></li>
						<li><label>&emsp;이메일 :&nbsp;</label><input type="email" onkeyup="this.value=this.value.replace(/[^a-z0-9@.]/g,'')" onpaste="return false" style='IME-MODE: disabled' name="email" id="signup-email" size="20" maxlength="30" value="${userInfo.email}" /></li>
						<li><label>전화번호 :&nbsp;</label><input type="text" onkeyup="this.value=this.value.replace(/[^0-9-]/g,'')" onpaste="return false" style='IME-MODE: disabled' name="tellNum" id="signup-tellNum" maxlength="30" value="${userInfo.tel_number}" /></li>
						<li><label>소속회사 :&nbsp;</label><input type="text" onpaste="return false" style='IME-MODE: disabled' name="company" class="addInfo" id="signup-company" maxlength="30" value="${userInfo.company}" /></li>
						<li><label>홈페이지 :&nbsp;</label><input type="text" onkeyup="this.value=this.value.replace(/[^a-z/:.]/g,'')" onpaste="return false" style='IME-MODE: disabled' name="url" id="signup-url" maxlength="30" value="${userInfo.url}" /></li>
						<li><p id="addInfo-status"></p></li>
				</div>

				<!-- 회원정보 수정 -->
				<div class="panel-footer">
					<p class="exportStatus"></p>
					<span class="btn-group pull-right"><input type="button" value="vcard 생성" id="btn-vcf" class="btn btn-default"><input type="button" value="수정" id="btn-edit" class="btn btn-default"> <input type="button" value="삭제" id="btn-leave" class="btn btn-default"> <input
						type="button" value="취소" id="btn-edit-cancel" class="btn btn-default" /></span>
				</div>
			</form>
		</div>
	</div>

	<div class="modal fade" id="editUser">

		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">회원 정보 수정</div>
				<div class="modal-body">
					<p>회원 정보를 수정하시겠습니까?</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="confirmEdit">확인</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
				</div>
			</div>

		</div>
	</div>

	<div class="modal fade" id="deleteUser">

		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">회원 정보 삭제</div>
				<div class="modal-body">
					<p>회원 정보를 삭제하시겠습니까?</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="confirmDelete">확인</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
				</div>
			</div>

		</div>
	</div>

	<div class="modal fade" id="exportVCF">

		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">vCard 다운로드</div>
				<div class="modal-body">
					<p>회원 정보를 바르게 입력하였는지 확인해주세요.</p>
					<p>vCard를 다운로드 하시겠습니까?</p>
				</div>
				<div class="modal-footer">
					<a href="<c:url value='/user/admin/downloadVCF?username=${param.username}'/>" class="btn btn-default" id="confirmDownload" role="button">vcard 생성</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
				</div>
			</div>

		</div>
	</div>


</body>
</html>
