<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
function loginCheck(){
	let m_id = "${sessionScope.mId}" != null? "${sessionScope.mId}" : "";
	let a_id = "${sessionScope.a_id}" != null? "${sessionScope.a_id}" : "";
	if (m_id != ""){
		Swal.fire({
			title: "잠깐!",
			html: "통합관리시스템 이용권한이 없습니다.<br>로그아웃 후 이동할까요?",
			icon: "question",
			showCancelButton: true,
			reverseButtons: true,
			cancelButtonColor: "#C6C7C8",
			confirmButtonText: "YES",
			cancelButtonText: "NO"
			}).then((result) => {
			if (result.isConfirmed) {
				location.href="/user/login/logout";
				location.href="/admin/admin_login/admin.do";
			}
		});
	}else if(a_id != ""){
		location.href="/admin/admin_login/main";
	}else {
		location.href="/admin/admin_login/admin.do";
	}
}	
</script>
<style>
footer{
z-index:-999;
padding:1rem;
}
</style>

<footer class="d-flex" style="text-align:center;">
<div class="container-fluid">
	<nav class="navbar navbar-sm">
	  <div class="d-flex justify-content" >
		<ul class="nav">	
			<li class="nav-item"><a href="#" class="nav-link px-2 text-muted disabled">© 2024 S LIBRARY.</a></li>
		</ul>
	  </div>
	  <div class="d-flex justify-content-end" >
		<ul class="nav">	
			<!-- 관리자페이지로 이동 -->
			<li class="nav-item"><a href="#" class="nav-link px-2 text-muted" onclick="loginCheck()">통합관리시스템</a></li>
		</ul>
	  </div>
	</nav>
</div>
</footer>
