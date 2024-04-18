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
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="/resources/static/user.css">
<script src="/resources/static/js/bootstrap.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>

<script>
function bookInfo(success, data) {
	const items = data.item;
	let str = "";
	if (data.totalResults > 0) {
		for (i = 0; i < items.length; i++) {
			let categoryArr = items[i].categoryName.split(">", 2)
			console.log(categoryArr[1].split("/", 1));
			let jsonArr = new Array();
			jsonArr.push({
				"idx" : i + "",
				"h_name" : items[i].title,
				"h_url" : items[i].cover,
				"h_author" : items[i].author,
				"h_pub" : items[i].publisher,
				"h_isbn" : items[i].isbn13,
				"h_description" : modify(items[i].description),
				"h_year" : items[i].pubDate.substr(0, 4),
				"h_category" : categoryArr[1].split("/", 1).toString(),
				"h_link" : items[i].link
			});

			str += "<tr><td><a href='#' onclick='confirm(" + i + ")'>"
					+ items[i].title + "</a><br>(" + items[i].author
					+ "&nbsp;|&nbsp;" + items[i].publisher + "&nbsp;|&nbsp;"
					+ items[i].pubDate.substr(0, 4) + ")<input id='" + i
					+ "' type='hidden' value='"
					+ JSON.stringify(jsonArr) + "'></td></tr>";
		}
	} else {
		Swal.fire({
			icon: "info",
			title: "Check!",
			html: "찾으시는 검색결과 없습니다.<br>검색어를 다시 한번 확인해주세요.",
			confirmButtonText: "OK"
		});
	}
	
	$("#result").append(str);
}

function search() {
	let keyword = $("#keyword").val();

	if (keyword.trim().length >= 2) {
		$('#result > tr > td').remove();
		let url = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbabout_kei2155001&Query="
				+ keyword
				+ "&QueryType=Keyword&&MaxResults=100&SearchTarget=Book&Sort=Title&cover=Big&output=js&callBack=bookInfo";

		$.ajax({
			url : url,
			async : false,
			dataType : "jsonp",
			jsonp : "bookInfo"
		});
	} else {
		Swal.fire({
			icon: "info",
			title: "Check!",
			html: "검색 키워드는 2글자 이상 입력해주세요.",
			confirmButtonText: "OK"
		});
	}
}

function confirm(i) {
	const obj = JSON.parse(document.getElementById(i).value);
	const data = obj[0];
	
	opener.document.getElementById("h_url").src = data.h_url
	opener.document.getElementById("h_name").value = data.h_name
	opener.document.getElementById("h_author").value = data.h_author
	opener.document.getElementById("h_pub").value = data.h_pub
	opener.document.getElementById("h_year").value = data.h_year
	opener.document.getElementById("h_category").value = data.h_category
	opener.document.getElementById("data").value = document.getElementById(i).value
	window.close();
}

function modify(e) {
	let resultText = e.replaceAll("&", "&amp;")	
					  .replaceAll("<", "&lt;")
					  .replaceAll(">", "&gt;")
					  .replaceAll("((?<!\\\\)(\\\\\\\\)*)(\\\\\\\")", "$1&quot;")
					  .replaceAll("'", "&#x27;")
					  .replaceAll("/", "&#x2F;");
	resultText = resultText.replace(/[\u0000-\u0019]+/g, "");
	return resultText
}
</script>

<style>
body {
	background: #fbf7f5 !important;
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

#btnSearch {
	background-color: #FEC5BB !important;
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
			<h3>&nbsp;&nbsp;<strong><i class="bi bi-justify-left"></i>&nbsp;SEARCH</strong></h3>
		</div>
	</div><!-- modal-header 끝 -->
	
	<hr>
	<div id="search" class="input-group input-group-sm d-flex">
		<input id="keyword" name="keyword" type="text" class="form-control" placeholder="검색어(도서명 or 저자)를 입력하세요">
		<button class="btn btn-light" type="button" id="btnSearch" onclick="search()">
			<i class="bi bi-search"></i>
		</button>
	</div><!-- search 끝 -->
	<br>
	<div id="searchResult">
		<table class="table table-sm table-hover align-middle text-left" id="table1">
			<tbody id="result" style="border-color: #FAE0E0;"></tbody>
		</table>
	</div>
</div> <!-- container 끝 -->
</body>
</html>
