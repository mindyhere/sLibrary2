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

</head>
<body>
<%@ include file="../common/header.jsp"%>
<form method="post" name="form1" action="/user/book/bookRequest" enctype="multipart/form-data">
		<div class="container">
		<h3 class="text-bold">
		<img src="/resources/images/book-half.svg" width="50px"
				height="40px">비치희망도서</h3>		
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
				<h5> 희망도서신청</h5>
				<hr>
				<div>
				<span><input type ="text" id="book"></span> <input id="Keyword " onclick="Keyword ()" name="hoBook" type="submit" class="btn btn-light"  value ="자료검색하기" >
				</div>
				<br>
				<div class="col-9"
							style="background-color: white; border-radius: 20px; padding: 30px 50px 30px 50px;">
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
					<label>도서분류</label>
					<input type="text" name="h_name" id="h_name" class="form-control">				
					</div>
					
					<div>
					<label>출판년도</label>
					<input type="text" name="h_year" id="h_year" class="form-control">				
					</div>
					
					<div>
					<label>분류</label>
					<input type="text" name="h_category" id=h_category class="form-control">					
					</div></div>
				
</div>
</div>
</form>
</body>
</html>
