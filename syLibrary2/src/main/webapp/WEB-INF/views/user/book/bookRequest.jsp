<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="/resources/images/icon.png" type="image/x-icon">
<link type="text/css" rel="stylesheet" href="/resources/static/user.css">
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<script>
$(function(){
	$("#btnOpen").click(function(){
		var pop = window.open("../../popup.jsp", "pop",
		"width=650,height=500, scrollbars=yes, resizable=yes");
	});
	
	$("#btnCheck").click(function(){
		location.href= "/user/book/myHopeBook/${sessionScope.mId}";
	});
	
	$("#btnConfirm").click(function() {
		const obj = JSON.parse(document.getElementById("data").value);
		const data = obj[0];
		console.log(data.h_category);
		$.ajax({
			url: "/user/request/insert",
			data: data,
			success: function(result) {
				Swal.fire({
					title: result,
					html: "나의서재에서 신청현황을 확인하실 수 있습니다.<br>해당 페이지로 이동할까요?",
					icon: "success",
					showDenyButton: true,
					reverseButtons: true,
					confirmButtonText: "YES",
					denyButtonText: "NO"
					}).then((result) => {
					if (result.isConfirmed) {
						location.href="/user/book/myHopeBook/${sessionScope.mId}";
					} else if (result.isDenied) {
						location.reload();
					}
				});
			},
			error: function(err) {
				console.log("**"+err);
				Swal.fire({
					icon: "error",
					title: "Not possible",
					html: "신청이 거부되었습니다.<br> 반복 실패 시, 관리자에게 문의해 주세요.",
					confirmButtonText: "OK"
				});
			}
		});
	});
});
</script>

<style>
p > input[type="text"] {
	border-color: #FAE0E0 !important;
	outline: none;
	box-shadow: none !important;
}

/* alert 커스텀 */
.swal2-confirm {
	background-color: #FEC5BB !important;
	border: 1px solid #FEC5BB !important;
	box-shadow: none !important;
	outline:none !important;
	height: 44px;
}
.swal2-deny {
	background-color: #C6C7C8 !important;
	border: 1px solid #C6C7C8 !important;
	box-shadow: none !important;
	outline:none !important;
	height: 44px;
}
</style>
</head>

<body>
<%@ include file="../common/header.jsp"%>
<div class="container">
	<h3 class="text-bold">
		<img src="/resources/images/book-half.svg" width="50px" height="40px"> 희망도서신청
	</h3>
	<hr>
	<div class="card-style d-flex-col">
		<div class="top d-flex-col">
			<h4 style="color:black !important;">
				<span class="mont">신청안내</span>
				<li>현재 저희 도서관에서 소장하지 않은 책일 경우, 희망도서를 신청하실 수 있습니다.</li>
				<li>"자료검색하기"를 통해 찾으시는 도서정보를 선택한 후, "신청하기" 를 클릭해 희망도서신청을 완료해주세요.</li>
				<li>검색 결과는 최대 100건 까지 조회됩니다.</li>
				<li>"나의서재 > 희망도서내역"에서 신청 처리 현황을 확인하실 수 있습니다.</li>
			</h4>
		</div>
		<div clas"bot">
			<div>
				<input id="btnOpen" name="btnOpen" type="button" class="btn btn-light" value="자료검색하기">
				<input id="btnCheck"  name="btnCheck" type="button" class="btn btn-light" value="신청현황확인">
			</div>
			<br>
			<form method="post" name="form1" action="/user/book/bookRequest">
			<div class="col detail">
				<p>
					<span>도서명 : </span>
					<input type="text" name="h_name" id="h_name" class="form-control" readonly>
				</p>
				<p>
					<span>저자 : </span>
					<input type="text" name="h_author" id="h_author" class="form-control" readonly>
				</p>
				<p>
					<span>출판사 : </span>
					<input type="text" name="h_pub" id="h_pub" class="form-control" readonly>
				</p>
				<p>
					<span>출판년도 : </span>
					<input type="number" name="h_year" id="h_year" class="form-control" readonly>
				</p>
				<p>
					<span>분류 : </span>
					<input type="text" name="h_category" id=h_category class="form-control" readonly>
				</p>
				<p>
					<input type="hidden" id="data" name="data" value="">
					<input id="btnConfirm" name="btnConfirm" type="button" class="btn btn-light" value="신청하기">
				</p>
			</div>
			</form>
		</div>
	</div>
</div>
<br>
</body>
<jsp:include page="../common/footer.jsp"></jsp:include>
</html>