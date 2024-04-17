<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="/resources/images/icon.png"
	type="image/x-icon">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="/resources/static/css/bootstrap.css">
<script src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/resources/static/js/bootstrap.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script>
	function list(page) {
		location.href = "/book/ct_list.do?cur_page="+page;
	}
	
	function del_confirm(number) {
	    swal({
	        text: "정말 삭제하시겠습니까?",
	        buttons: ["취소", "확인"],
	    }).then(function(isConfirmed) {
	        if (isConfirmed) {
	        	document.form1.action = "/book/del_ct.do?ct_number="+number;
				document.form1.submit();
	        } 
	    });
	}
</script>
<style>
html, body {
	height: 100%;
}

table {
	border-collapse: collapse;
	height: 37.8px;
	margin: 0rem auto;
	box-shadow: 4px 4px 10px 0 rgba(0, 0, 0, 0.1);
	background-color: white;
	border: none;
	width: 500px;
	padding-top: 20px;
	
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
	<div class="page-direction" style="padding: 20px; padding-left: 250px;">
	</div>
	<form name="form1" method="post" action="/book/ins_ct.do">
	<div id="inp" style="width:500px; align:center; padding-left: 155px; padding-bottom:20px;">
	<div style="float:left;"><input type="text" name="category" id="category" class="form-control" style="width:100px;"/></div>
	<div style="float:left; padding-left: 10px;"><input type="number" name="ct_number" id="ct_number" class="form-control" style="width:100px;"/></div>
	<div style="float:left; padding-left: 10px;"><input type="submit" value="등록" style="background-color: #6699CC;" class="btn text-white"></div>
	</div>
	<table border="1" style="width:500px; margin-top:30px;">

		<tr align="center" style="color: white; font-size: 17px;">
			<th>분류명</th>
			<th>분류 번호</th>
		</tr>
		<c:choose>
			<c:when test="${map.count == 0}">
			<tr height="100px" align="center">
			<td colspan="2">일치하는 항목이 없습니다.</td>
			</tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="dto" items="${map.dto}">
					<tr align="center">
						<td><a style="color: black;" href="#" onClick="del_confirm(${dto.ct_number})">${dto.category}</a></td>
						<td>${dto.ct_number}</td>
					</tr>
				</c:forEach>
				<tr align="center">
					<td colspan="2"><c:if test="${map.page.curPage>1}">
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
	</form>
	<c:if test="${result == 'success'}">
		<script>
		swal({
	        text: "등록되었습니다",
	        buttons: {cancel: false, confirm: true,},
	    }).then(function(isConfirmed) {
	        if (isConfirmed) {
	        	location.href = "/book/ct_list.do";
	        } 
	    });
		</script>
	</c:if>
	<c:if test="${result == 'over'}">
		<script>
		swal({
	        text: "중복된 항목은 넣을 수 없습니다",
	        buttons: {cancel: false, confirm: true,},
	    }).then(function(isConfirmed) {
	        if (isConfirmed) {
	        	location.href = "/book/ct_list.do";
	        } 
	    });
		</script>
	</c:if>
	<c:if test="${result == 'fail'}">
		<script>
		swal({
	        text: "오류가 발생했습니다",
	        buttons: {cancel: false, confirm: true,},
	    }).then(function(isConfirmed) {
	        if (isConfirmed) {
	        	location.href = "/book/ct_list.do";
	        } 
	    });
		</script>
	</c:if>
	<c:if test="${rst == '실패'}">
		<script>
		swal({
	        text: "오류가 발생했습니다",
	        buttons: {cancel: false, confirm: true,},
	    }).then(function(isConfirmed) {
	        if (isConfirmed) {
	        	location.href = "/book/ct_list.do";
	        } 
	    });
		</script>
	</c:if>
	<c:if test="${rst == '성공'}">
		<script>
		swal({
	        text: "삭제되었습니다",
	        buttons: {cancel: false, confirm: true,},
	    }).then(function(isConfirmed) {
	        if (isConfirmed) {
	        	location.href = "/book/ct_list.do";
	        } 
	    });
		</script>
	</c:if>
</body>
</html>