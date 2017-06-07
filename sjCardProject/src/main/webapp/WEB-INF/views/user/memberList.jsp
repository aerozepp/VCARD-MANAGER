<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<% request.setCharacterEncoding("UTF-8"); %>

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
<!-- <meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache"> -->
<title>sjCard members</title>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<head>
<script src="//raw.github.com/botmonster/jquery-bootpag/master/lib/jquery.bootpag.min.js"></script>
</head>

<style>
img {
	border-radius: 50%;
	width: 30px;
	height: 30px;
}

.memberList {
	cursor: pointer;
}

ul {
	list-style-type: none;
}

.modal-header {
	background: grey;
	color: white;
}

.search-status {
	color: red;
}

#username {
	font-weight: bold;
	color: blue;
}

.page-null {
	cursor: not-allowed;
}
</style>



<body>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/css/tether-theme-arrows-dark.css"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

	<fmt:setBundle basename="/properties/label" />

	<fmt:message key="listTitle" var="list-title" />
	<fmt:message key="listHeaderImage" var="listHeader-image" />
	<fmt:message key="listHeaderId" var="listHeader-id" />
	<fmt:message key="listHeaderEmail" var="listHeader-email" />
	<fmt:message key="listHeaderAddress" var="listHeader-address" />

	<fmt:message key="memberInfoTitle" var="memberInfo-title" />
	<fmt:message key="memberInfoUsername" var="memberInfo-username" />
	<fmt:message key="memberInfoLastname" var="memberInfo-lastname" />
	<fmt:message key="memberInfoGivenname" var="memberInfo-givenname" />
	<fmt:message key="memberInfoEmail" var="memberInfo-email" />
	<fmt:message key="memberInfoCompany" var="memberInfo-company" />
	<fmt:message key="memberInfoTellnum" var="memberInfo-tellnum" />
	<fmt:message key="memberInfoCountry" var="memberInfo-country" />
	<fmt:message key="memberInfoCity" var="memberInfo-city" />
	<fmt:message key="memberInfoAddress" var="memberInfo-address" />
	<fmt:message key="memberInfoUrl" var="memberInfo-url" />

	<script>

	/*회원 목록 가져오기  */
	function listMembers(curPage){
	    
	    $('.search-status').text("");
		$('#curPageInput').val(curPage);
	
	    $.ajax({
		
			url : "/user/searchMembers",
			type : "GET",
			dataType : "json",
			data : $('#searchForm').serialize(),
			success : function(json){
			   
			    showMemberList(json, curPage);
			    //alert(json[json.length-1].COUNT+"건이 검색되었습니다.");
			}
	    });    
	}

	/*상세 회원 정보 가져오기  */
	function retreiveMemberInfo(){
	    
	    $(".memberList").click(function() {
		
			$("#memberInfo").modal('show');
			
			var username = $(this).find('.username').text();
		
				$.ajax({
		    		url : "/user/retreiveMemberInfo",
	    			type : "POST",
	    			data : {username : username},
	    			dataType : "json",
	    			success : function(json) {  
	    			
						showMemberInfo(json);
	    			}
				});
		});   
	}
	
	
	/* 회원목록 그리기 */
	function showMemberList(data, curPage) {
	    
	    var list = data.list;
	    var totalMember = data.count;
	
	    $(".memberListBody").text('');
	    
	    for (var i = 0; i < list.length; i++) {
			
			var members = list[i];
			var htmlStr = "<tr class='memberList'>"
						+ "<td style='width: 20%;'><img src='";
			if(members.IMAGE == null || members.IMAGE == "noImage"){
			    htmlStr += "<c:url value='/images/default-user-image.png'/>";
			}else{
			    htmlStr += "<c:url value='${pageContext.request.contextPath}/images/"+members.IMAGE + "'/>";
			}			
				
				var email = members.EMAIL == null ? "없음" : members.EMAIL;	
					
				htmlStr += "'/></td>"
						+ "<td class='username'>" + members.USER_ID + "</td>"
						+ "<td class='email'>" + email + "</td>"
						+ "</tr>";
			
			$(".memberListBody").append(htmlStr);		
	    }
	    
	    $('.listResult').text(totalMember + "건" );
	  
	    pager(totalMember, curPage);
	    retreiveMemberInfo();
	}
	
	/* 페이지 그릴때 태그 설정하기 */
	function pageTagger(className, content, addClass){
	    
	    var pageStr = "<li class='page-item " + addClass + "'><a class='" + className + "' href='#'>" 
	                + content + "</a></li>";
	    
	    return pageStr;
	}
	
	/*페이지네이션 보여줄 단위수대로 그리기  */
	function drawPagerByLimit(offset, curPage, limit){

	    var pagerStr = '';
	 
	    for(var i = offset; i < offset + limit ; i++){	
			
		 	if(i == curPage){
			    pagerStr += pageTagger("page-link", i, "active");
			}else{
			    pagerStr += pageTagger("page-link", i);
			}	
	    }   
	    
	    $('.pagination').append(pagerStr);	     
	}
	
	/* 페이져 그리기 */
	function pager(totalMember, curPage){
	    
	    var curPage = parseInt(curPage);
	    var lastPage = Math.ceil(totalMember / 10);
	    var pagerStrBackward = pageTagger("page-link", "<<", "first") + pageTagger("page-link", "<", "prev");
	    var pagerStrForward = pageTagger("page-link", ">", "next") + pageTagger("page-link", ">>", "last");
	    var refresh = pageTagger("page-link","목록 처음으로", "refresh");
	    var pagerStr = '' ;
	    var pagerStrAdd = '';
	    var limit = 3;
	    var numBeforeOffset = Math.floor(limit/2);
	    var offset = 1;
	    
	    $('.pagination').text("");
	    
	    	
	    /* 페이져의 시작에서 오프셋이 1보다 크고 오프셋포함 limit 숫자 만큼만 보야야 할 경우
	    	예) limit이 3이면 현재 페이지가 3부터 
	    	   limit이 5이면 현재 페이지가 4부터
	    	   limit이 7이면 현재 페이지가 5부터	*/
	    	
	    if(totalMember != 0){
			
			if(curPage != 1){
				pagerStr += pagerStrBackward;
			}	 
		    	   
		    if(curPage > limit - numBeforeOffset && lastPage > limit){
				offset = curPage - numBeforeOffset;
				pagerStr += pageTagger("page-link", 1);
			
		    }
		    
		    /* 페이져 마지막 limit 숫자 만큼만 보여야 할 경우 
		    	예) limit이 3이고 마지막 페이지가 50이면 현재 페이지가 49이상일때만 3개를 그리고
		    	   limit이 5이고 마지막 페이지가 50이면 현재 페이지가 48이상일때만 5개를 그린다. */
		    if(curPage >= lastPage - numBeforeOffset && lastPage > limit){
			    offset = (lastPage - limit) + 1;
	
			}
		    
		    /* 1 다음에 ...을 그려야하는 경우 */
		    if(offset >= 3){
				pagerStr += pageTagger("page-null", "...");
			}	    
		    
		    $('.pagination').append(pagerStr);	
		    
		    /* 페이져 가운데 보여줄 3개를 그린다. */
		    drawPagerByLimit(offset, curPage, limit > lastPage ? lastPage : limit );
		    
		    /* 페이지 후반 ...을 그려야하는 경우
		       예) limit이 3이고 마지막 페이지가 50이면 오프셋이 46이하부터*/
		    if(offset < lastPage - limit){
				pagerStrAdd += pageTagger("page-null", "...");
			}	
		    
		    /*마지막 페이지를 따로 그려야 하는 경우 */   
		    if(offset <= lastPage-limit){
			
				pagerStrAdd += pageTagger("page-link", lastPage);
		    }
		    
		    if(curPage != lastPage){
		   		pagerStrAdd += pagerStrForward;
		    }	
		   	
		    $('.pagination').append(pagerStrAdd);	    
		    
		  }	else{
		      $('.pagination').append(refresh);	  
		  }
	    	
	    pagination();    
	    
	}
	
	/*페이져 이벤트 발생  */
	function pagination(){
		
	    var curPage = $('.active').children().text();
	    var lastPage = $('.pagination li').eq(-3).text();
	   
	    var order = order;
	  
	    $('.page-link').click(function() {

		 	var searched = $("#searched").val();
			
		 	if(searched == "searched"){
			    $('.search-status').text("검색창에 입력이 있습니다. 검색 버튼을 눌러 주세요.");
			}else{
			    if($(this).parent().hasClass('first')){
				    listMembers(1);	
				    
				}else if($(this).parent().hasClass('prev')){
				    if (curPage != 1) {
					    listMembers(--curPage);
					}
				}else if($(this).parent().hasClass('next')){
				    if (curPage < lastPage) {
					    listMembers(++curPage);
					} 		    
				}else if($(this).parent().hasClass('last')){
				    listMembers(lastPage);
				    
				}else if($(this).parent().hasClass('refresh')){
				    window.location.replace("<c:url value='/user/memberList'/>");
				    
				}else{
					curPage = $(this).text();
				    listMembers(curPage);
				}	  
			}	
	    });
	}
	
	/* 상세 회원정보 보여주기 */
	function showMemberInfo(data){

	    var htmlStr = '';
	    
	    if(data.image == null || data.image == "noImage"){
			htmlStr = "<img src='<c:url value='/images/default-user-image.png'/>'/>";
	    }else{
			htmlStr = "<img src='<c:url value='${pageContext.request.contextPath}/images/"+data.image+"'/>'/>";
	    }
 
		
	    $("#img").text('');
		$("#img").append(htmlStr);
    	
		$("#username").text(data.username);
		$('#lastname').text(data.lastName);
		$('#givenname').text(data.givenName);
		$('#email').text(data.email);
		$("#company").text(data.company);
		$('#tellnum').text(data.tel_number);
		$('#url').text(data.url);
		
		$("#btn-admin").click(function(){
		   	window.location.replace("<c:url value='admin?username=" + data.username + "'/>");
		});
		
	}
	

	$(document).ready(function() {
		
	    $('#deleteSuccess').modal('show');
	    
		var curPage = 1;
		var search = 0;
			
	   listMembers(1);
	   
	   $("#searchInput").keydown(function(){
	       $("#searched").val("searched");
	   });
	     
	    $('#btn-search').click(function(){
			
			$("#searched").val("not-searched");
			
			var searchInput = $("#searchInput").val();
			
			if($.trim(searchInput).length <= 0){
			    $('.search-status').text("검색명을 입력해 주세요.");
			}else{
	
			    listMembers(1);
			}
    	});
	    
	    $('.custom-select').change(function(){	
			listMembers(curPage);		  
	    });
	    
	    $('#searchInput').keypress(function(key){
			if( (key.charCode != 64) && (key.keyCode != 8) && (key.keyCode != 9) 
				 && (key.charCode != 46) && (key.charCode < 97 || key.charCode > 122) && (key.charCode < 48 || key.charCode > 57) 
				 && (key.keyCode <= 12687 || key.keyCode >= 12592) ) {
			    $('.search-status').text("@를 제외한 특수문자 및 대문자는 사용할 수 없습니다.");
				return false;
			}else{
			    $('.search-status').text("");
				return true;
			}
    	});
	    
	});
	
    </script>

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
					<li><a href="<c:url value="/user/loginPage"/>" data-toggle="modal" data-target="#signup"><span class="glyphicon glyphicon-user"> </span> 회원가입</a></li>
					<li><a href="<c:url value="/user/loginPage"/>"><span class="glyphicon glyphicon-log-in"></span> 로그인</a></li>
				</ul>
			</c:if>
		</div>
	</div>
	</nav> </section>

	<div class="container">
		<table class="table table-striped">
			<thead>
				<tr>
					<th colspan="2">사용자 목록</th>
					<th colspan="2" class="listResult text-right"></th>
				</tr>
				<tr>
					<th>이미지</th>
					<th>아이디</th>
					<th>이메일</th>
				</tr>
			</thead>

			<tbody class="memberListBody">

			</tbody>
		</table>

		<!-- 검색 조건 셀렉터 -->
		<div class="container">
			<form class="form-inline" id="searchForm">
				<select name="option" class="custom-select mb-2 mr-sm-2 mb-sm-0" id="optionFormCustomSelect">
					<option value="USER_ID">아이디</option>
					<option value="EMAIL">이메일</option>
				</select> <select name="order" class="custom-select mb-2 mr-sm-2 mb-sm-0" id="orderFormCustomSelect">
					<option value="asc">오름차순</option>
					<option value="desc">내림차순</option>
				</select> <label class="custom-control custom-checkbox mb-2 mr-sm-2 mb-sm-0"> <input type="text" name="input" class="custom-control-input" placeholder="검색명" id="searchInput" maxlength="30">
				</label> <input type="button" style='IME-MODE: disabled' class="btn btn-sm" id="btn-search" value="검색" /> <input type="hidden" name="curPage" id="curPageInput"> <input type="hidden" name="searched" id="searched">
			</form>
			<div class="search-status"></div>
		</div>


	</div>

	<!-- 페이지네이션 -->
	<div class="container">
		<nav aria-label="Page navigation example">

		<ul class="pagination justify-content-center">

		</ul>
		</nav>
	</div>

	<!-- 회원정보 상세보기 팝업 -->
	<div class="modal fade" id="memberInfo">

		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">회원 상세 정보</div>


				<div class="modal-body">

					<div class="panel-body">

						<table class="table table-striped">

							<tbody>
								<tr>
									<td id="img" style='width: 50%;'></td>
									<td id="username"></td>
								</tr>
								<tr>
									<td>성</td>
									<td id="lastname"></td>
								</tr>
								<tr>
									<td>이름</td>
									<td id="givenname"></td>
								</tr>
								<tr>
									<td>이메일</td>
									<td id="email"></td>
								</tr>
								<tr>
									<td>회사</td>
									<td id="company"></td>
								</tr>
								<tr>
									<td>전화번호</td>
									<td id="tellnum"></td>
								</tr>
								<tr>
									<td>홈페이지</td>
									<td id="url"></td>
								</tr>
							</tbody>

						</table>
					</div>

				</div>

				<div class="modal-footer">

					<sec:authorize ifAnyGranted="ROLE_ADMIN">
						<button type="button" class="btn btn-default" data-dismiss="modal" id="btn-admin">관리자 페이지</button>
					</sec:authorize>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>

			</div>

		</div>
	</div>
	
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