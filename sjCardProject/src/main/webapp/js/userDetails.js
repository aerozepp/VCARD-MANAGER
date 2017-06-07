/**
 * 
 */
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
	
	
	$(document).ready(function() {
	
	    var addInfo = 0;
	    var idCheck = 0; //사용자명 중복확인 여부
	    var params = window.location.search.substr(1).split('?');
	    
	    if($.trim(params).length <= 0){
			$('#parceSuccess').modal('show');
	    }

	    $("#signup-username").change(function() {
			idCheck = 0;
	    });
	
	 
	    //사용자명 중복확인 이벤트
	    $("#btn-checkUsername").click(function() {

			var username = $("#signup-username").val();
	
			if ($.trim(username).length <= 0) {
			    $("#username-status").text("${noUser}");
			} else {
	
			    $.ajax({
					url : "/user/checkUser",
					dataType : 'html',
					method : "POST",
					data : {
					    username : username
					},
					success : function(data) {
					    
					    if (data == username) {
							$('#username-status').text("${userAvailable}");
							idCheck = 1;
					    } else {
							$('#username-status').text("${userDenied}");
					    }
					},
			    });
			}

	    });
	    
	    $('#signup-username').keypress(function(key) {
		    if( (key.keyCode != 8) && (key.keyCode != 9) && (key.charCode < 97 || key.charCode > 122) && (key.charCode < 48 || key.charCode > 57) ){
			 	$("#username-status").text("특수문자 및 대문자는 사용할 수 없습니다.");
				return false;
		    }else{
				$("#username-status").text("");
				return true;
		    }
		});
	    
	    $('#signup-password').keypress(function(){
		 	$("#password-status").text("");
	    });
	    
	    $('#signup-confirm').keypress(function(){
	 		$("#confirm-status").text("");
    	});
		     
		//회원가입 전송
	    $("#btn-signup").click(function() {
		
			var username = $("#signup-username").val();
			var password = $("#signup-password").val();
			var pw_confirm = $("#signup-confirm").val();
			
			if ($.trim(username).length <= 0) {
			    $("#username-status").text("${noUser}");
			} else if ($.trim(password).length <= 0) {
			    $("#password-status").text("${noPass}");
			} else if ($.trim(pw_confirm).length <= 0) {
			    $("#confirm-status").text("${confirmPass}");
			} else if (password != pw_confirm) {
			    $("#confirm-status").text("${passInvalid}");
			} else {
			    
			    if (idCheck == 1) {
					
					$("#insertUserForm").submit();
			    } else {
					alert("${userCheck}")
			    }
			}
	    });

	    $("#btn-signup-cancel").click(function() {
			window.location.replace("loginPage");
	    });
	    
	    
	    $("#btn-edit").click(function() {
		
			var password = $("#pw-edit").val();
			var pw_confirm = $("#signup-confirm").val();
				
			if($.trim(password).length > 0){
			    
			    if($.trim(pw_confirm).length <= 0) {
				    $("#confirm-status").text("${confirmPass}");
			    }else if (password != pw_confirm) {
				    $("#confirm-status").text("${passInvalid}");
				}else{
				    $("#confirm-status").text("");
					$("#editUser").modal("show");
			    }
			    
			}else{
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
				    	window.location.replace("loginPage?deleteSucc=true");
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
			    $(".exportStatus").text("회원정보 수정을 버튼을 확인해주세요.");
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
			window.location.replace("/");
    	});

	});
	