/**
 * 
 */

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
					window.location.replace("/?loginSucc=true");
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
});