<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<style>
.dd {
	padding-left: 10px;
	padding-right: 10px;
	padding-top: 10px;
	padding-bottom: 10px;
	float: left;
}
/* 테이블행 */
table {
	border-collapse: collapse;
	height: 20px;
	margin: 0rem auto;
	box-shadow: 4px 4px 10px 0 rgba(0, 0, 0, 0.1);
	background-color: white;
	border: none;
}

th {
	padding: 8px;
	text-align: center;
	height: 20px;
}
</style>
<script>
function cancleReason() {
	let h_cancle = $("#h_cancle").val();
	let h_idx = $("#h_idx").val();
	$.ajax({
		type: "post",
		url: "/admin/hope/cancle_reason",
		data: {
			"h_idx" : h_idx,
			"h_cancle" : h_cancle
		},
		success : function() {
			swal('수정되었습니다.');
			location.href = "/admin/hope/hope_detail";
		}
	})
}
function changeState(h_idx) {
	
	/* let h_state = $("#state_change").val();
	let h_idx = $("#h_idx").val();
	$.ajax({
		type: "post",
		url: "/admin/hope/change_status",
		data: {
			"h_idx" : h_idx,
			"h_state" : h_state
		},
		success : function() {
			swal('수정되었습니다.');
			location.href = "/admin/hope/hope_detail";
		}
	}) */
	
	document.form1.h_idx.value=h_idx;
	document.form1.action="/admin/hope/change_status";
	document.form1.submit();
}
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/admin/admin_header.jsp" />
<div id="body-wrapper">
	<div id="body-content">
			<jsp:include page="/WEB-INF/views/admin/menu.jsp" />

		<div class="page-direction" style="padding: 20px; padding-left: 250px;">
			<div class="navi">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;희망도서</span>
				<i class="bi bi-chevron-right"></i> <span style="font-weight: bold;">희망도서상세</span>
				
				
				<div style="left: 290px; position: absolute; width: 2000px;">
					<div class="dd">
						<form name="form1" method="post" enctype="multipart/form-data">
							<table border="1" width="600px" style="position: static;">
								<tr>
									<td>신청자</td>
									<td><input type="text" name="h_memid" id="h_memid" size="5"
									class="form-control" readonly value="${dto.h_memid}"></td>
								</tr>
								<tr>
									<td>신청일자</td>
									<td><input type="text" name="h_regdate" id="h_regdate" size="5"
									class="form-control" readonly value="${dto.h_regdate}"></td>
									<td>처리일자</td>
									<td><input type="text" name="h_chkdate" id="h_chkdate" size="5"
									class="form-control" readonly value="${dto.h_chkdate}"></td>
								</tr>
								<tr>
									<td>처리상태</td>
									<td><input type="text" name="h_state" id="h_state" size="5"
									class="form-control" readonly value="${dto.h_state}">
									</td>
								</tr>
								<c:if test="${dto.h_cancel != null}">
									<tr>
										<td></td>
										<td colspan="3">
											<input type="text" name="h_cancel" id="h_cancel" size="5"
										class="form-control" readonly value="${dto.h_cancel}">
										</td>
									</tr>
								</c:if>
								<tr>
									<td>도서명</td>
									<td><input type="text" name="h_name" id="h_name" size="5"s
									class="form-control" readonly value="${dto.h_name}"></td>
									<td>도서표지</td>
									<td rowspan="6">
										<img name="h_url" id="h_url" style="height: 150px; width: 100px;" readonly src="${dto.h_url}">
									</td>
								</tr>
								<tr>
									<td>저자명</td>
									<td><input type="text" name="h_author" id="h_author" size="5"
									class="form-control" readonly value="${dto.h_author}"></td>
								</tr>
								<tr>
									<td>출판사명</td>
									<td><input type="text" name="h_pub" id="h_url" size="5"
									class="form-control" readonly value="${dto.h_pub}"></td>
								</tr>
								<tr>
									<td>ISBN</td>
									<td><input type="text" name="h_isbn" id="h_isbn" size="5"
									class="form-control" readonly value="${dto.h_isbn}"></td>
								</tr>
								<tr>
									<td>출판년도</td>
									<td><input type="text" name="h_year" id="h_year" size="5"
									class="form-control" readonly value="${dto.h_year}"></td>
								</tr>
								<tr>
									<td>도서분류명</td>
									<td><input type="text" name="h_category" id="h_category" size="5"
									class="form-control" readonly value="${dto.h_category}"></td>
								</tr>
							</table>
								<tr>
									<td colspan="4" align="right">
												<select id="state" name="state" onchange="document.form1.state.value=this.value;">
													<option value="신청완료" <c:if test="${dto.h_state=='신청완료'}">${'selected'}</c:if>>신청완료</option>
													<option value="접수취소" <c:if test="${dto.h_state=='접수취소'}">${'selected'}</c:if>>접수취소</option>
													<option value="처리중" <c:if test="${dto.h_state=='처리중'}">${'selected'}</c:if>>처리중</option>
													<option value="이용가능" <c:if test="${dto.h_state=='이용가능'}">${'selected'}</c:if>>이용가능</option>
												</select>
													<td width="70px">
															<input type="button" value="상태변경" class="btn text-white" style="background-color: #6699CC;" onclick="changeState(${dto.h_state})">
													</td>
									</td>
									<c:choose>
										<c:when test="${dto.h_state == '접수취소'}">
											<td>
												<div id="p1">
													<form name="form2" method="post" action="/admin/hope/cancle_reason">
														<input type="hidden" name="h_idx" value="${dto.h_idx}">
														취소사유 : <input name="cancelReason">
														<td width="70px">
															<input type="button" value="취소사유등록" class="btn text-white" style="background-color: #6699CC;" onclick="cancelReason()">
														</td>
													</form>
												</div>
											</td>
										</c:when>
									</c:choose>
								</tr>
						</form>
					</div>
				</div>
				
				
			</div>
		</div>
	</div>
<jsp:include page="/WEB-INF/views/admin/admin_footer.jsp" />
</div>
</body>
</html>