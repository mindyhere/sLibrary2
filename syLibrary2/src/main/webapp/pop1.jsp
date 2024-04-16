<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="icon" href="/resources/images/icon.png" type="image/x-icon">
<link rel="stylesheet" href="/resources/static/css/bootstrap.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="/resources/static/user.css">
<script src="/resources/static/js/bootstrap.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

<script>
function bookInfo(success, data) {
	const items = data.item;
	let str = "";
	console.log("콜백= " + items.length);
	for (i = 0; i < items.length; i++) {
		let jsonArr = new Array();
		jsonArr.push({
			"idx" : i + "",
			"h_name" : items[i].title,
			"h_url" : items[i].cover,
			"h_author" : items[i].author,
			"h_pub" : items[i].publisher,
			"h_isbn" : items[i].isbn13,
			"h_description" : items[i].description,
			"h_year" : items[i].pubDate.substr(0, 4),
			//"h_category" : items[i].categoryName.split(">", 2),
			"h_category" : items[i].categoryName,
			"h_link" : items[i].link,
		});

		str += "<tr><td><a href='#' onclick='confirm(" + i + ")'>"
				+ items[i].title + "</a>&nbsp;(" + items[i].author
				+ "&nbsp;|&nbsp;" + items[i].publisher + "&nbsp;|&nbsp;"
				+ items[i].pubDate.substr(0, 4) + ")<input id='" + i
				+ "' type='hidden' value='"
				+ JSON.stringify(jsonArr) + "'></td></tr>";
	}
	//console.log("콜백, str: " + str);
	$("#result").append(str);
}

function search() {
	let keyword = $("#keyword").val();
	console.log("클릭 = " + keyword);

	if (keyword != "") {
		$('#result > tr > td').remove();
		let url = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbabout_kei2155001&Query="
				+ keyword
				+ "&QueryType=Keyword&start=1&MaxResults=100&SearchTarget=Book&Sort=Title&cover=Big&output=js&callBack=bookInfo";
		console.log("if => " + keyword);

		$.ajax({
			url : url,
			async : false,
			dataType : "jsonp",
			jsonp : "bookInfo"
		});
	}
}

function confirm(i) {
	const obj = JSON.parse(document.getElementById(i).value);
	const data = obj[0];
	console.log(data.h_author);
	//console.log(data);
	
	opener.document.getElementById("h_name").value = data.h_name
	opener.document.getElementById("h_author").value = data.h_author
	opener.document.getElementById("h_pub").value = data.h_pub
	opener.document.getElementById("h_year").value = data.h_year
	opener.document.getElementById("h_category").value = data.h_category
	opener.document.getElementById("data").value = document.getElementById(i).value
	window.close();
}
</script>

<style>
body {
	display:flex;
	background: #fbf7f5 !important;
}

div {
	border: 1px solid red; /* 테스트 */
}

.container {
	padding: 1%;
	background: white !important;
	width: 100%;
	height: 400px;
	border-radius: 7px !important;
}

#search {
	margin-bottom: 0.5em;
}

#keyword, #btnSearch {
	border-color: #FAE0E0;
	outline: none;
	box-shadow: none !important;
}

#searchResult {
	overflow-y: scroll;
	height: 70%;
	margin-bottom: 2%;
	overflow-y: auto;
	background-color: white !important;
}

#result .table {
	white-space: nowrap;
}

a {
	color: crimson;
	text-decoration-line: none;
}
</style>
</head>

<body>
<div class="container">
	<div id="modal-header" class="d-flex justify-content-between">
		<div>
			<h3>&nbsp;&nbsp;<strong><i class="bi bi-justify-left"></i>&nbsp;SEARCH</h3></stong>
		</div>
	</div><!-- modal-header 끝 -->
	
	<hr>
	<div id="search" class="input-group input-group-sm d-flex">
		<input id="keyword" name="keyword" type="text" class="form-control" placeholder="검색어를 입력하세요">
		<button class="btn btn-light" type="button" id="btnSearch" onclick="search()" style="background-color: #FEC5BB !important;">
			<i class="bi bi-search"></i>
		</button>
	</div><!-- search 끝 -->
	
	<div id="searchResult">
		<table class="table table-sm table-hover align-middle text-left" id="table1">
			<tbody id="result" style="border-color: #FAE0E0;"></tbody>
		</table>
	</div>
</div> <!-- container 끝 -->
</body>
</html>
