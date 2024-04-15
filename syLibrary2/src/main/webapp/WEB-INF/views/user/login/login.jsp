<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="/resources/images/icon.png" type="image/x-icon">
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
s<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link rel="stylesheet" href="/resources/static/user.css">
<c:if test="${param.message == 'join'}">
	<script>
		$(function() {
			swal('회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.');
		});
	</script>
</c:if>
<script>
	$(function() {
		var mId = getCookie("Cookie_userid");
		$("#mId").val(mId);

		// id가 저장되어 있는 상태라면 아이디저장하기 체크표시
		if ($("#mId").val() != "")
			$("#saveId").attr("checked", true);

		var mId = document.getElementById('mId');
		var mPasswd = document.getElementById('mPasswd');
		var loginForm = document.getElementById('loginForm');

		$(".btnLogin").click(function() {
			let mId = $("#mId").val();
			let mPasswd = $("#mPasswd").val();

			if (mId == "") {
				Swal.fire({
					icon : 'warning',
					text : '아이디를 입력하세요.',
				});
				$("#mId").focus();
				return;
			}
			if (mPasswd == "") {
				Swal.fire({
					icon : 'warning',
					text : '비밀번호를 입력하세요.',
				});
				$("#mPasswd").focus();
				return;
			}
			if ($("#saveId").is(":checked")) {
				setCookie("Cookie_userid", mId, 30);
			} else {
				delCookie("Cookie_userid");
			}
			document.loginForm.submit();
		});
	});

	/* 아이디 저장 */
	/* 쿠키 저장하기
	@ param name : 쿠키명
	@ param value : 값
	@ param exdays : 만료일
	 */
		function setCookie(name, value, exdays) {
			var today = new Date();
			today.setDate(today.getDate() + exdays);
			var cookie = escape(value)
					+ ((exdays == null) ? "" : ";expires=" + today.toGMTString());
			document.cookie = name + "=" + cookie;
		}

	/* 쿠키 가져오기
	@ param name : 쿠키명
	 */
	function getCookie(name) {
		name = name + "=";
		var cookieData = document.cookie;
		var begin = cookieData.indexOf(name);
		var cookie = '';
		if (begin != -1) {
			begin += name.length;
			var end = cookieData.indexOf(';', begin);
			if (end == -1)
				end = cookieData.length;
			cookie = cookieData.substring(begin, end);
		}
		return unescape(cookie);
	}

	// 쿠키 삭제
	function delCookie(name) {
		var exprieDate = new Date();
		exprieDate.setDate(exprieDate.getDate() - 1);
		document.cookie = name + "=" + "; expires=" + exprieDate.toGMTString();
	}
	
	$(function(){
		var responseMsg = "<c:out value="${message}" />";
		if(responseMsg == '로그인실패'){
			Swal.fire({
				icon : 'warning',
				title : '회원정보 불일치',
				text : '아이디 또는 비밀번호를 확인해주세요.',
			});
		}
	});
</script>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="container min-vh-100">
		<h3 class="text-bold">
			<img src="/resources/images/login.png" width="35px" height="35px">
			로그인
		</h3>
		<hr>
		<p class="text-sm text-gray">로그인을 하시면 보다 더 많은 정보와 서비스를 이용하실 수
			있습니다.</p>
		<!--  로그인 입력창 -->
		<div class="card-style mb-30">
			<form name="loginForm" method="post" action="/user/login/login.do">
				<div>
					<div class="input-style-1">
						<label>아이디</label> <input type="text" name="mId" id="mId"
							placeholder="아이디를 입력해주세요">
					</div>
					<div class="input-style-1">
						<label>비밀번호</label> <input type="password" class="form-control"
							name="mPasswd" id="mPasswd" placeholder="비밀번호를 입력해주세요">
					</div>
					<div class="form-check">
						<input type="checkbox" id="saveId" class="form-check-input">
						<label for="saveId" class="form-check-label">아이디 저장</label>
					</div>
					<br> <input type="button" value="로그인" id="main-btn"
						class="btnLogin">
				</div>
			</form>
		</div>
		<!-- 아이디 찾기, 비밀번호 찾기, 회원가입 -->
		<div class="card-style d-flex align-items-center"
			style="background-color: #E8E8E4; border: 1px solid #D5D5D5; height: 300px">
			<div class="col text-center">
				<div class="btnLoginBottom">
					<a href="/user/login/searchId"> <img
						src="../../resources/images/login/id.png"><br> 아이디 찾기
					</a>
				</div>
			</div>
			<div class="col text-center">
				<div class="btnLoginBottom">
					<a href="/user/login/searchPasswd"> <img
						src="../../resources/images/login/forgot.png"><br> 비밀번호
						찾기
					</a>
				</div>
			</div>
			<div class="col text-center">
				<div class="btnLoginBottom">
					<a href="/user/member/join"> <img
						src="../../resources/images/login/join.png"><br>회원가입
					</a>
				</div>
			</div>
		</div>
	</div>
</body>
<jsp:include page="../common/footer.jsp"></jsp:include>
</html>