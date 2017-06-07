/**
 * 
 */

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
	
	
	
	function showMemberList(data, curPage) {
	    
	    var list = data.list;
	    var totalMember = data.count;
	
	    $(".memberListBody").text('');
	    
	    for (var i = 0; i < list.length; i++) {
			
			var members = list[i];
			var htmlStr = "<tr class='memberList'>"
						+ "<td style='width: 20%;'><img src='";
			if(members.IMAGE == null || members.IMAGE == "noImage"){
			    htmlStr += "/images/default-user-image.png";
			}else{
			    htmlStr += "${pageContext.request.contextPath}/images/"+members.IMAGE;
			}			
					
				htmlStr += "'/></td>"
						+ "<td class='username'>" + members.USER_ID + "</td>"
						+ "<td class='email'>" + members.EMAIL + "</td>"
						+ "<td class='address'>" + members.ADDRESS + "</td>"
						+ "</tr>";
			
			$(".memberListBody").append(htmlStr);		
	    }
	    
	    $('.listResult').text(totalMember + "건" );
	  
	    pager(totalMember, curPage);
	    retreiveMemberInfo();
	}
	
	function pageTagger(className, content, addClass){
	    
	    var pageStr = "<li class='page-item " + addClass + "'><a class='" + className + "' href='#'>" 
	                + content + "</a></li>";
	    
	    return pageStr;
	}
	
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
	
	function pager(totalMember, curPage){
	    
	    var curPage = parseInt(curPage);
	    var lastPage = Math.ceil(totalMember / 10);
	    var pagerStr = pageTagger("page-link", "<", "first") + pageTagger("page-link", "<<", "prev");
	    var pagerStrAdd = '';
	    var limit = 3;
	    var numBeforeOffset = Math.floor(limit/2);
	    var offset = 1;
	    
	    $('.pagination').text("");
	    
	    	
	    /* 페이져의 시작에서 오프셋이 1보다 크고 오프셋포함 limit 숫자 만큼만 보야야 할 경우
	    	예) limit이 3이면 현재 페이지가 3부터 
	    	   limit이 5이면 현재 페이지가 4부터
	    	   limit이 7이면 현재 페이지가 5부터	*/
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
	    
	   	pagerStrAdd += pageTagger("page-link", ">>", "next") + pageTagger("page-link", ">", "last");
	   	
	   	
	    $('.pagination').append(pagerStrAdd);	  
		
	    pagination();    
	    
	}
	
	function pagination(){
		
	    var curPage = $('.active').children().text();
	    var lastPage = $('.pagination li').eq(-3).text();
	    var order = order;
	  
	    $('.page-link').click(function() {

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
			    
			}else{
				curPage = $(this).text();
			    listMembers(curPage);
			}	
			
	    });
	}
	
	function showMemberInfo(data){

	    var htmlStr = '';
	    
	    if(data.image == null || data.image == "noImage"){
			htmlStr = "<img src='/images/default-user-image.png'/>";
	    }else{
			htmlStr = "<img src='${pageContext.request.contextPath}/images/"+data.image+"'/>";
	    }
 
		
	    $("#img").text('');
		$("#img").append(htmlStr);
    	
		$("#hiddenUsername").attr("href","/user/downloadVCF?username="+data.username);
		$("#username").text(data.username);
		$('#lastname').text(data.lastName);
		$('#givenname').text(data.givenName);
		$('#email').text(data.email);
		$("#company").text(data.company);
		$('#tellnum').text(data.tel_number);
		$('#country').text(data.country);
		$('#city').text(data.city);
		$('#address').text(data.address);
		$('#url').text(data.url);	
	}
	

	$(document).ready(function() {
		
		var curPage = 1;
			
	   listMembers(1);
	    
	    $('#btn-search').click(function(){
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
	    
	});