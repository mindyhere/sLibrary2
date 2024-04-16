<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="icon" href="/resources/images/icon.png" type="image/x-icon">
<link rel="stylesheet" href="/resources/static/css/bootstrap.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="/resources/static/user.css">
<script src="/resources/static/js/bootstrap.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>


<script>
function bookInfo(success, data) {
	const items = data.item; 
	let str="";
	console.log("콜백= " + items.length);
	for (i=0 ; i < items.length ; i++){
		console.log("for문: "+i+" => "+items[i].title);
		str +="<tr><td><a href='#' onclick=''>" + items[i].title + "</a>&nbsp;(" + items[i].author + "&nbsp;|&nbsp;"
		+ items[i].publisher + "&nbsp;|&nbsp;" + items[i].pubDate.substr(0,4) + ")</td></tr>"
		+<input;
	}
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
	console.log("콜백, str: " + str);
	$("#result").append(str);
}

function search() {
	let keyword = $("#keyword").val(); // aladin-bookinfo로 시작하는 id를 가진 div 엘리먼트를 찾습니다.
	console.log("클릭 = "+keyword);
	
	if (keyword !="") {
		$('#result > tr > td').remove();   
		let url = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbabout_kei2155001&Query="
			+ keyword + "&QueryType=Keyword&start=1&MaxResults=100&SearchTarget=Book&Sort=Title&cover=Big&output=js&callBack=bookInfo";
		console.log("if => "+keyword);
		
		$.ajax({
			url: url,
			async: false,
			dataType: "jsonp",
			jsonp: "bookInfo"
		});
	}
}
</script>

<style>
body {
background: #fbf7f5 !important;
}
div{
border: 1px solid red; /* 테스트 */
}
.container {
padding:1%;
background: white !important;
width:100%;
height:400px;
border-radius: 7px !important;
}
#search{
margin-bottom: 0.5em;
}
#searchResult {
overflow-y: scroll;
height:70%;
margin-bottom:2%;
overflow-y: auto;
background-color:white !important;
/* box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24); */
}
#result .table {
white-space:nowrap;
}
a{
color: crimson;
}
</style>
</head>

<body>
<div class="container">
	<div id="modal-header" class="d-flex justify-content-between">
		<div><h3>&nbsp;&nbsp;<strong><i class="bi bi-justify-left"></i>&nbsp;SEARCH</h3></stong></div>
	</div><!-- modal-header 끝 -->	
	<hr>
	<div id="search" class="input-group input-group-sm d-flex">
		<input name="keyword" type="hidden" class="bookinfo" value="">
		<button class="btn btn-light" type="button" id="btnSearch" onclick="search()" style="background-color:#FEC5BB !important;">
			<i class="bi bi-search"></i>
		</button>
	</div><!-- search 끝 -->
	<div id="searchResult">
	<table class="table table-sm table-hover align-middle text-left" id="table1">
	  <tbody id="result" style="border-color:#FAE0E0;">
	  </tbody>	
	</table>
	</div>
</div><!-- container 끝 -->
</body>
</html>
