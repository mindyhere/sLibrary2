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
	<form method="post" name="form1" action="/user/book/bookRequest"
		enctype="multipart/form-data">
		<div class="container">
			<h3 class="text-bold">
				<img src="/resources/images/book-half.svg" width="50px"
					height="40px">비치희망도서
			</h3>
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
						<h5>희망도서신청</h5>
						<hr>
						<div>
							<input	id="Keyword " onclick="Keyword ()" name="Keyword" type="submit"
								class="btn btn-light" value="자료검색하기"> <input id="check"
								onclick="location.href='/user/member/myLibrary'" name="check"
								type="submit" class="btn btn-light" value="신청현황 확인">
						</div>
						<br>
						<div class="col detail">
							<p>
								<span>도서명 : </span> <input type="text" name="h_name" id="h_name"
									class="form-control" value="${dto.h_name}">
							</p>

							<p>
								<span>저자 : </span> <input type="text" name="h_author"
									id="h_author" class="form-control" value="${dto.h_author}">
							</p>
 
							<p>
								<span>출판사 : </span> <input type="text" name="h_pub" id="h_pub"
									class="form-control" value="${dto.h_pub}">
							</p>

							<p>
								<span>출판년도 : </span> <input type="number" name="h_year"
									id="h_year" class="form-control" value="${dto.h_year}">
							</p>

							<p>
								<span>분류 : </span> <input type="text" name="h_category"
									id=h_category class="form-control" value="${dto.h_category}">
							</p>

							<p>
 							<input 	id="save " onclick="btnsave()" name="save" type="submit"
								class="btn btn-light" value="신청하기"></p>
						</div>
						
								</div>
								 </div>
					</div>
				</div>
	</form>
</body>
</html>
