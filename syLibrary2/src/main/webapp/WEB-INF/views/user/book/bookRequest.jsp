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
function bookDisplay(success, data) {
	console.log("콜백= " + data.item[1].title);
	/*let params={
	"title": "All About IB Psychology Essay - IB Psychology Essay Guide for SL and HL Students",
	"link": "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=269866535&amp;partner=openAPI&amp;start=api",
	"author": "강수민 (지은이)",
	"pubDate": "2021-05-15",
	"description": "성공적으로 IB 심리 과정을 이수한 저자가 에세이 노하우의 모든것을 이 책에 적어냈다. 심리 에세이를 접근하는 방법부터 안정적인 에세이의 구성, IB 심리에서 용이하게 쓰이는 용어와 문장들이 정리되어 있다.",
	"isbn13": "9791197045318",
	"cover": "https://image.aladin.co.kr/product/26986/65/cover/k452730517_1.jpg",
	"categoryName": "국내도서\u003E자기계발\u003E취업/진로/유망직업\u003E해외취업/이민/유학.연수",
	"publisher": "그루미출판"
	};*/
}

function search() {
	let keyword = $("#keyword").val(); // aladin-bookinfo로 시작하는 id를 가진 div 엘리먼트를 찾습니다.
	console.log("클릭 = "+keyword);
	
	if (keyword !="") { 
		let url = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbabout_kei2155001&Query="
			+ keyword + "&QueryType=Keyword&MaxResults=10&start=1&SearchTarget=Book&Sort=Title&cover=Big&output=js&callBack=bookDisplay";
		console.log("if => "+keyword);
		
		$.ajax({
			url: url,
			async: false,
			dataType: "jsonp",
			jsonp: "bookDisplay"
		});
	}
}
</script>

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
				<input id="btnSearch" onclick="search()" name="btnSearch" type="button" class="btn btn-light"  value ="자료검색하기" >
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
