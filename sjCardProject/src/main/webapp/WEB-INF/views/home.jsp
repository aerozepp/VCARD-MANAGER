<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	/**
	 * @Class Name : honme.jsp
	 * @Description : vCard 메인화면
	 * @Modification Information
	 *
	 * @author prettymuch(Hyun Jung)
	 * @since 2017.04.19
	 * @version 1.0
	 *
	 * Copyright (C) All right reserved.
	 */
%>

<fmt:setBundle basename="message" />

<fmt:message key="message.loginSucc" var="loginSucc" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<title>sjcard homepage</title>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- 
<style>
.modal-header{
background-color : green;}
</style> -->
</head>

<style>
.modal-header {
	background-color: grey;
	color: white;
}

.contaier {
	background: grey;
}
</style>
<body>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/css/tether-theme-arrows-dark.css"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

	<script>
	
	$(document).ready(function(){
	    
	    $('#loginSucess').modal('show');
	    $('#updateSuccess').modal('show');
	    
	    
	    /* $('#edit').click(function(){
			
			var username = $('#vcfUsername').val();
			
			$.ajax({
			    url : "/user/edit",
			    method : "POST",
			    data : {username : username},
			    success : function(){
				
			    }
			});
		
	    }); */
		    
	});

	
    </script>



	<input type="hidden" value="${pageContext.request.userPrincipal.name}" id="vcfUsername">
	
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
					<li><a id="edit" href="<c:url value="/user/userDetails"/>"><span id="session_name" class="glyphicon glyphicon-user"> ${pageContext.request.userPrincipal.name} </span></a></li>
					<li><a href="<c:url value="/user/logout"/>"><span class="glyphicon glyphicon-log-out"></span>&nbsp로그아웃</a></li>
				</ul>
			</c:if>

			<c:if test="${empty pageContext.request.userPrincipal}">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="<c:url value="/user/loginPage"/>" data-toggle="modal" data-target="#signup"><span class="glyphicon glyphicon-user"> </span> 회원가입</a></li>
					<li><a href="<c:url value="/user/loginPage"/>"><span class="glyphicon glyphicon-log-in"></span> 로그인</a></li>
				</ul>
			</c:if>
		</div>
	</div>
	</nav> </section>

	<section>
	<div id="vcard" class="container-fluid text-center">
		<h2>sjCARD</h2>
		<br> <br>
		<div class="row slideanim">
			<div class="col-sm-4">
				<span class="glyphicon glyphicon-leaf logo-small"></span>
				<h4>친환경</h4>
				<p>자연을 생각하는 VCARD</p>
			</div>
			<div class="col-sm-4">
				<span class="glyphicon glyphicon-picture logo-small"></span>
				<h4>이미지</h4>
				<p>사진 첨부 서비스</p>
			</div>
			<div class="col-sm-4">
				<span class="glyphicon glyphicon-lock logo-small"></span>
				<h4>보안</h4>
				<p>보안이 됩니다.</p>
			</div>
		</div>
		<br> <br>
	</div>
	</section>


	<c:if test="${param.login == 'succ'}">
		<div class="modal fade" id="loginSucess">

			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">로그인</div>
					<div class="modal-body">
						<p>로그인 되었습니다.</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
					</div>
				</div>

			</div>
		</div>
	</c:if>

	<c:if test="${not empty param.update}"> 
		<div class="modal fade" id="updateSuccess">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">회원 정보 수정</div>
					<div class="modal-body">
						<c:if test="${param.update == true}">
							<p>회원정보가 수정 되었습니다.</p>
						</c:if>
						<c:if test="${param.update == false}">
							<p>회원정보 수정중 에러가 발생하였습니다. 다시 시도하여 주세요.</p>
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