<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<object xmlns="http://www.aladin.co.kr/ttb/apiguide.aspx">
<link>http://www.aladin.co.kr/search/wsearchresult.aspx?KeyTitle=aladdin&SearchTarget=book</link>
<link rel="icon" href="/resources/images/icon.png" type="image/x-icon">
<link type="text/css" rel="stylesheet" href="/resources/static/user.css">
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<script>
function popUp(){
	window.open("../../pop1.jsp", "pop", "width=600,height=500, scrollbars=yes, resizable=yes");
}
</script>

<style>
/* 모달페이지 */
.modal-bg {
	position: fixed;
	display: flex;
	justify-content: center;
	padding: 10%;
	top: 0;
	left: 0;
	width: 100%; 
	height: 100%;
	background-color: rgba(0, 0, 0, 0.6);
	transition : all 1s;
	z-index: 999;
	transition : opacity 0.5s ease;
}
.modal-body {
	/* overflow-y: auto; */
	box-sizing: border-box;
	position: absolute;
	top: 50%; 
	min-width: 600px !important;
	height: 500px !important;
	align-items: center;
	justify-content: center;
	text-align: center;
	background: #fbf7f5 !important;
	border-radius: 7px;
	box-shadow: 0 1px 7px rgba(0, 0, 0, 0.5); 
	transform: translateY(-50%); 
}
.modal-bg.visible {
	visibility: visible;
	opacity : 1;
	display: block;
	transition : all 0.5s;
}
.invisible {
	display: none;
	z-index: -999;
	opacity : 0;
	transition : all 0.5s;
}
.modal-container {
background: #fbf7f5 !important;
width:100%;
height:100%;
padding:2%;
border-radius: 7px !important;
}
#modal-header {
height:10%;
margin-bottom:1%
}
#modal-section1 {
overflow-y: scroll;
height:70%;
margin-bottom:2%;
overflow-y: auto;
background-color:white !important;
border-radius: 7px !important;
box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);\
}
#modal-section2 {
height:15%;
max-height:2.5rem;
}
#review-total .table {
white-space:nowrap;
}
</style>
</head>


<body>
<%@ include file="../common/header.jsp"%>
<div class="container">
	<form method="post" name="form1" action="/user/book/bookRequest" enctype="multipart/form-data">
		<h3 class="text-bold"><img src="/resources/images/book-half.svg" width="50px" height="40px">희망도서신청</h3>		
		<hr>
		<div class="card-style d-flex-col">
			<div class="top d-flex-col">
				<h4>
					<span class="mont">신청안내</span> <br>
					<li>비치희망자료는 홈페이지에서 자료 검색 후, 소장자료가 없을 때만 신청 가능</li>
					<li>처리기간: 신청접수 후 우선 처리</li>
					<li>신청취소: 접수완료나 처리 중인 상태에만 취소 가능</li>
				</h4>			
					
				<div class="bot">
			<h5>신청서작성</h5>
			<hr>
			
			<div>
				<span><input type ="text" id="keyword" value="${keyword}"></span>
				<input id="btnSearch" onclick="popUp()" name="btnSearch" type="button" class="btn btn-light"  value ="자료검색하기" >
			</div>
			<br>
			<div class="col-9" style="background-color: white; border-radius: 20px; padding: 30px 50px 30px 50px;">
				<div>
					<label>책 제목</label>
					<input type="text" name="h_name" id="h_name" class="form-control">					
				</div>
				<div>
					<label>url</label>
					<input type="text" name="h_url" id="h_url" class="form-control">					
				</div>
				<div>
					<label> 작가명</label>
					<input type="text" name="h_author" id="h_author" class="form-control">				
				</div>
				<div>
					<label>출판사명</label>
					<input type="text" name="h_pub" id="h_pub" class="form-control">					
				</div>
				<div>
					<label>isbn</label>
					<input type="text" name="h_isbn" id="h_isbn" class="form-control">			
				</div>
				<div>
					<label>도서설명</label>
					<input type="text" name="h_description" id="h_description" class="form-control">					
				</div>
				<div>
					<label>출판년도</label>
					<input type="text" name="h_year" id="h_year" class="form-control">				
				</div>
				<div>
					<label>분류</label>
					<input type="text" name="h_category" id=h_category class="form-control">					
				</div>
				</div id="result"></div>
			
			</div>
		</div>
	</form>
</div>

</body>
</html>
