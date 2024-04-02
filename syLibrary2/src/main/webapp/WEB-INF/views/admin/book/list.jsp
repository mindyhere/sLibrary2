<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="/syLibrary/resources/images/icon.png"
	type="image/x-icon">
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="/syLibrary/include/css/bootstrap.css">
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script>
	$(function() {
		$("#btnSearch").click(function() {
			if ($("select[name=search_option] option:selected")
					.text() == "선택") {
				swal("분류를 선택하세요");
				return false;
			}
			if ($("#keyword").val() == "") {
				swal("검색어를 입력하세요");
				return false;
			}
		});
	});

	function list(page) {
		location.href = "/syLibrary/book_servlet/search.do?cur_page=" + page
				+ "&search_option=${search_option}&keyword=${keyword}";
	}
</script>
<style>
html, body {
	height: 100%;
}

.search {
	position: relative;
	height: 40px;
	width: 500px;
	border: 2px solid #6699CC;
	border-radius: 30px;
	overflow: hidden;
	left: 300px;
	margin-bottom: 15px;
}

.search>input {
	height: 36px;
	width: 360px;
	font-size: 16px;
	padding: 10px;
	border: 0;
	float: left;
	outline: none;
}

.icon {
	padding-right: 14px;
	float: left;
	padding: 5px;
}

table {
position: absolute;
	border-collapse: collapse;
	height: 37.8px;
	margin: 0rem auto;
	box-shadow: 4px 4px 10px 0 rgba(0, 0, 0, 0.1);
	background-color: white;
	border: none;
	width: 1000px;
	left: 300px;
}

th {
	padding: 8px;
	text-align: center;
	background-color: #91C8E4;
	height: 37.8px;
}

td {
	height: 38px;
}
/* 테이블 올렸을 때 */
tbody tr:hover {
	background-color: #d3d3d3;
	opacity: 0.9;
	cursor: pointer;
}

#s_list {
	font-size: 14px;
	text-align: center;
	height: 36px;
	outline: none;
	width: 100px;
	float: left;
	border: 0;
	color: #3f3f3f;
}
.dd {
padding-left: 20px;
padding-right: 20px;
padding-top: 20px;
padding-bottom: 20px;
}

#hr {
	text-decoration: none;
	color: black;
}
</style>
</head>
<body>
	<jsp:include page="../admin_header.jsp" />
	<div id="body-wrapper">
	<div id="body-content">
	<jsp:include page="../menu.jsp" />
	<div style="width:1000px;">
	<div class="page-direction" style="padding: 20px; padding-left: 250px;">
		<div class="navi">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;도서관리</span> <i class="bi bi-chevron-right"></i>
				<span style="font-weight:bold;">도서목록</span>
		</div>
	</div>

	<form name="form1" method="post"
		action="/syLibrary/book_servlet/search.do">

	</form>
	<table border="1">
		<tr align="center" style="color: white; font-size: 17px;">
			<th>도서번호</th>
			<th>도서명</th>
			<th>저자</th>
			<th>출판사</th>
			<th>발행년도</th>
			<th>분류</th>
		</tr>
				<c:forEach var="dto" items="${map.dto}">
					<tr align="center">
						<td><a style="color: black;" href="../book_servlet/edit.do?b_id=${dto.b_id}">${dto.b_id}</a></td>
						<td>${dto.b_name}&nbsp;</td>
						<td>${dto.b_author}</td>
						<td>${dto.b_pub}</td>
						<td width="150px">${dto.b_year}</td>
						<td>${dto.b_category}&nbsp;</td>
					</tr>
				</c:forEach>

				<tr align="center">
					<td colspan="7"><c:if test="${map.page.curPage>1}">
							<a id="hr" href="#" onclick="list('1')">[처음]</a>
						</c:if> <c:if test="${map.page.curBlock>1}">
							<a id="hr" href="#" onclick="list('${map.page.prevPage}')">[이전]</a>
						</c:if> <c:forEach var="num" begin="${map.page.blockStart}"
							end="${map.page.blockEnd}">
							<c:choose>
								<c:when test="${num==map.page.curPage}">
									<span style="color: blue">${num}</span>
								</c:when>
								<c:otherwise>
									<a id="hr" href="#" onclick="list('${num}')">${num} </a>
								</c:otherwise>
							</c:choose>
						</c:forEach> <c:if test="${map.page.curBlock <= map.page.totBlock}">
							<a id="hr" href="#" onclick="list('${map.page.nextPage}')">[다음]</a>
						</c:if> <c:if test="${map.page.curPage <= map.page.totPage}">
							<a id="hr" href="#" onclick="list('${map.page.totPage}')">[마지막]</a>
						</c:if></td>
				</tr>
	</table>
	</div>
		</div>
	<jsp:include page="/admin/admin_footer.jsp" />
</div>
</body>
</html>