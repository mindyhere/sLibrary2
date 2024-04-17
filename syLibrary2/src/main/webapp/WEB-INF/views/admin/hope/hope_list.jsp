<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="/syLibrary2/resources/images/icon.png"
	type="image/x-icon">
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="/syLibrary2/resources/static/css/bootstrap.css">
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script>
	$(function() {
		$("#btnSearch").click(function() {
			if ($("#keyword").val() == "") {
				swal("검색어를 입력하세요");
				return false;
			}
		});
	});

	function list(page) {
		location.href = "/admin/hope/list.do?cur_page="+page
				+ "&search_option=${map.search_option}&keyword=${map.keyword}";
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
	width: 900px;
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
	<jsp:include page="/WEB-INF/views/admin/admin_header.jsp" />
	<div id="body-wrapper">
	<div id="body-content">
	<jsp:include page="/WEB-INF/views/admin/menu.jsp" />
	<div style="width:1000px;">
	<div class="page-direction" style="padding: 20px; padding-left: 250px;">
		<div class="navi">
				<span style="font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;희망도서</span> <i class="bi bi-chevron-right"></i>
		</div>
	</div>

	<form name="form1" method="post" action="/admin/hope/list.do">
		<div class="search">
			<select id="s_list" id="search_option" name="search_option">
				<c:choose>
					<c:when test="${map.search_option == 'none'}">
						<option value="none">전체</option>
						<option value="h_idx">번호</option>
						<option value="h_name">도서명</option>
						<option value="h_memid">신청자</option>
						<option value="h_date">날짜</option>
						<option value="h_state">상태</option>
					</c:when>
					<c:when test="${map.search_option == 'h_idx'}">
						<option value="none">전체</option>
						<option value="h_idx" selected>번호</option>
						<option value="h_name">도서명</option>
						<option value="h_memid">신청자</option>
						<option value="h_date">날짜</option>
						<option value="h_state">상태</option>
					</c:when>
					<c:when test="${map.search_option == 'h_name'}">
						<option value="none">전체</option>
						<option value="h_idx">번호</option>
						<option value="h_name" selected>도서명</option>
						<option value="h_memid">신청자</option>
						<option value="h_date">날짜</option>
						<option value="h_state">상태</option>
					</c:when>
					<c:when test="${map.search_option == 'h_memid'}">
						<option value="none">전체</option>
						<option value="h_idx">번호</option>
						<option value="h_name">도서명</option>
						<option value="h_memid" selected>신청자</option>
						<option value="h_date">날짜</option>
						<option value="h_state">상태</option>
					</c:when> 
					<c:when test="${map.search_option == 'h_date'}">
						<option value="none">전체</option>
						<option value="h_idx">번호</option>
						<option value="h_name">도서명</option>
						<option value="h_memid">신청자</option>
						<option value="h_date" selected>날짜</option>
						<option value="h_state">상태</option>
					</c:when>
					<c:when test="${search_option == 'h_state'}">
						<option value="none">전체</option>
						<option value="h_idx">번호</option>
						<option value="h_name">도서명</option>
						<option value="h_memid">신청자</option>
						<option value="h_date">날짜</option> 
						<option value="h_state" selected>상태</option>
					</c:when>
				</c:choose>
			</select> <input type="text" id="keyword" name="keyword" value="${map.keyword}"
				placeholder="검색어를 입력하세요">
			<div class="icon">
				<input type="submit" id="btnSearch" style="display: none;">
				<label for="btnSearch"><svg
						xmlns="http://www.w3.org/2000/svg" width="20" height="20"
						fill="#666666" class="bi bi-search" viewBox="0 0 16 16">
  <path
							d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0" />
</svg></label>
			</div>
		</div>
	</form>
	<table border="1">
		<tr align="center" style="color: white; font-size: 17px;">
			<th>신청번호</th>
			<th>도서명</th>
			<th>신청자</th>
			<th>신청날짜</th>
			<th>변경날짜</th>
			<th>상태</th>
		</tr>
		<c:choose>
			<c:when test="${map.count == 0}">
			<tr height="100px" align="center">
			<td colspan="6">일치하는 항목이 없습니다.</td>
			</tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="dto" items="${map.dto}">
					<tr align="center">
						<td><a style="color: black;" href="/admin/hope/detail.do?h_idx=${dto.h_idx}">${dto.h_idx}</a></td>
						<td>${dto.h_name}&nbsp;</td>
						<td>${dto.h_memid}</td>
						<td>${dto.h_regdate}</td>
						<td>${dto.h_chkdate}</td>
						<td>${dto.h_state}&nbsp;</td>
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
						</c:forEach> <c:if test="${map.page.curBlock < map.page.totBlock}">
							<a id="hr" href="#" onclick="list('${map.page.nextPage}')">[다음]</a>
						</c:if> <c:if test="${map.page.curPage < map.page.totPage}">
							<a id="hr" href="#" onclick="list('${map.page.totPage}')">[마지막]</a>
						</c:if></td>
				</tr>

			</c:otherwise>
		</c:choose>
	</table>
	</div>
		</div>
	<jsp:include page="/WEB-INF/views/admin/admin_footer.jsp" />
</div>
</body>
</html>