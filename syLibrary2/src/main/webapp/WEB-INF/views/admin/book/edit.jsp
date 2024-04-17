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
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="/syLibrary2/resources/static/css/bootstrap.css">
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script>
	var replaceChar = /[~!@\#$%^&*\()\-=+_'\;<>0-9\/.\`:\"\\,\[\]?|{}]/gi;
	var replaceNotFullKorean = /[ㄱ-ㅎㅏ-ㅣ]/gi;
	var replaceNotInt = /[^0-9]/gi;
	$(function() {
		$("#b_category").on(
				"focusout",
				function() {
					var x = $(this).val();
					if (x.length > 0) {
						if (x.match(replaceChar)
								|| x.match(replaceNotFullKorean)) {
							x = x.replace(replaceChar, "").replace(
									replaceNotFullKorean, "");
						}
						$(this).val(x);
					}
				}).on("keyup", function() {
			$(this).val($(this).val().replace(replaceChar, ""));
		});
		$("#b_isbn").on("focusout", function() {
			var x = $(this).val();
			if (x.length > 0) {
				if (x.match(replaceNotInt)) {
					x = x.replace(replaceNotInt, "");
				}
				$(this).val(x);
			}
		}).on("keyup", function() {
			$(this).val($(this).val().replace(replaceNotInt, ""));
		});

	});
	$(function() {
		$("#btnUpdate").click(function() {
			let b_id = $("#b_id").val();
			let b_name = $("#b_name").val();
			let b_author = $("#b_author").val();
			let b_pub = $("#b_pub").val();
			let b_year = $("#b_year").val();
			let b_description = $("#b_description").val();
			let b_amount = $("#b_amount").val();
			let b_isbn = $("#b_isbn").val();
			if (b_name == "") {
				swal("도서명을 입력하세요");
				$("#b_name").focus();
				return;
			}
			if (b_author == "") {
				swal("저자를 입력하세요");
				$("#b_author").focus();
				return;
			}
			if (b_pub == "") {
				swal("출판사를 입력하세요");
				$("#b_pub").focus();
				return;
			}
			if (b_year == "") {
				swal("출판년도를 입력하세요");
				$("#b_year").focus();
				return;
			}
			if (b_amount == "") {
				swal("수량을 입력하세요");
				$("#b_amount").focus();
				return;
			}
			if (b_isbn == "") {
				swal("isbn을 입력하세요");
				$("#b_isbn").focus();
				return;
			}
			if (b_description == "" || b_description.trim().length == 0) {
				swal("설명을 입력하세요");
				$("#b_description").focus();
				return;
			} 
			let filename = document.form1.file1.value;
			let start = filename.lastIndexOf(".") + 1;
			if (start != -1) {
				let ext = filename.substring(start, filename.length);
				if (ext == "jsp" || ext == "exe") {
					swal("업로드할 수 없는 파일입니다.");
					return;
				}
			}
			document.form1.submit();
		});

		$("#btnDelete").click(function() {
			swal_confirm();
		});
	});
	
	function swal_confirm() {
	    swal({
	        text: "정말 삭제하시겠습니까?",
	        buttons: ["취소", "확인"],
	    }).then(function(isConfirmed) {
	        if (isConfirmed) {
	        	document.form1.action = "/book/delete.do?b_id=${map.dto.b_id}";
				document.form1.submit();
	        } 
	    });
	}
	
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				document.getElementById('b_url').src = e.target.result;
			};
			reader.readAsDataURL(input.files[0]);
		} else {
			document.getElementById('b_url').src = "";
		}
	}
</script>
<style>
table {
position: absolute;
  border-collapse: collapse;
  height: 20px;
  margin: 0rem auto;
  width: 1150px;
  box-shadow: 4px 4px 10px 0 rgba(0, 0, 0, 0.1);
  background-color: white;
  border: none;
  left: 300px;
}
th {
  padding: 8px;
  text-align: center;
  background-color: #91C8E4;
  height: 20px;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/admin/admin_header.jsp" />
<div id="body-wrapper">
	<div id="body-content">
<jsp:include page="/WEB-INF/views/admin/menu.jsp" />
<div style="width:1150px;">
<div class="page-direction" style="padding: 20px; padding-left: 250px;">
	<div class="navi">
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;도서관리</span> <i class="bi bi-chevron-right"></i>
			<span>도서목록</span> <i class="bi bi-chevron-right"></i>
			<span style="font-weight:bold;">도서수정</span>
	</div>
</div>

	<form name="form1" method="post" action = "/book/update.do?b_id=${map.dto.b_id}" enctype="multipart/form-data">
		<table border="1" style="width: 800px; height: 460px; resize: none">
			<tr style="width: 400px; height: 60px;">
				<td align="center">도서명</td>
				<td><input name="b_name" name="b_name" class="form-control" value="${map.dto.b_name}"></td>
				<td rowspan="3">첨부파일</td>
				<td><input type="file" name="file1" id="file1"
					onchange="readURL(this);" class="form-control"></td>
			</tr>
			<tr style="width: 400px; height: 60px;">
				<td align="center">저자</td>
				<td><input type="text" name="b_author" id="b_author" class="form-control"
					value="${map.dto.b_author}"></td>
				<c:choose>
				<c:when test="${map.check == 0}">
					<td rowspan="2">
						<img src="/resources/images/book/${map.dto.b_url}" id="b_url" name="b_url" 
					style="max-height: 100px;"/>
					</td>
				</c:when>
				<c:otherwise>
					<td rowspan="2">
						<img src="${map.dto.b_url}" id="b_url" name="b_url" style="max-height: 100px;"/>
					</td>
				</c:otherwise>
				</c:choose>
			</tr>
			<tr style="width: 400px; height: 60px;">
				<td align="center">출판사</td>
				<td><input type="text" name="b_pub" id="b_pub"
					value="${map.dto.b_pub}" class="form-control"></td>
			</tr>
			<tr style="width: 400px; height: 60px;">
				<td align="center">발행년도</td>
				<td><input type="number" name="b_year" id="b_year"
					maxlength="4" value="${map.dto.b_year}" class="form-control"></td>
				<td rowspan="4">도서 설명</td>
				<td rowspan="4"><textarea name="b_description"
						id="b_description" rows="7"
						style="height: 90%; width: 90%; border: 0; outline: none; resize: none;">${map.dto.b_description}</textarea></td>
			</tr>
			<tr style="width: 400px; height: 60px;">
				<td align="center">분류</td>
				<td><input type="text" list="list_a" name="b_category"
					id="b_category" placeholder="${map.dto.b_category}" style="width: 150px" class="form-control">
					<input type="hidden" name="category" id="category" class="form-control" value="${map.dto.b_category}">
					 <datalist id="list_a">
						<c:forEach var="list" items="${map.list}">
							<option value="${list}"></option>
						</c:forEach>
					</datalist></td>
			</tr>
			<tr style="width: 400px; height: 60px;">
				<td align="center">수량</td>
				<td><input type="number" name="b_amount" id="b_amount"
					maxlength="2" value="${map.dto.b_amount}" class="form-control"></td>
			</tr>
			<tr style="width: 400px; height: 60px;">
				<td align="center">ISBN</td>
				<td><input type="text" id="b_isbn" name="b_isbn" maxlength="13"
					minlength="13" class="form-control" value="${map.dto.b_isbn}" readonly></td>
			</tr>
			<tr>
				<td width="70px" colspan="4" align="right" style="padding-right: 5px; padding-bottom: 5px;">
				<input type="hidden" name="b_id"
					id="b_id" value="${dto.b_id}"> <input type="button"
					style="background-color: #6699CC;" class="btn text-white" value="수정" id="btnUpdate">
				<input type="button" style="background-color: #6699CC;" value="삭제"
					id="btnDelete" class="btn text-white"></td>
			</tr>
		</table>
	</form>
	</div>
	<c:if test="${result == 'success'}">
		<script>
			swal({
		        text: "수정되었습니다",
		        buttons: {cancel: false, confirm: true,},
		    }).then(function(isConfirmed) {
		        if (isConfirmed) {
		        	location.href = "/book/list_all.do";
		        } 
		    });
		</script>
	</c:if>
	
	</div>
	<jsp:include page="/WEB-INF/views/admin/admin_footer.jsp" />
</div>
</body>
</html>