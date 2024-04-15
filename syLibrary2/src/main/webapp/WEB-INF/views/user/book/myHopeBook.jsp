<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="/resources/images/icon.png" type="image/x-icon">
<link type="text/css" rel="stylesheet" href="/resources/static/user.css">
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<script>
// 신청 취소
function cancelHoBook(hIdx) {
	Swal.fire({
		icon : 'question',
		text : '선택하신 희망 도서를 취소하시겠습니까?',
		showCancelButton : true,
		confirmButtonText : "YES",
	}).then((result) => {
		if (result.isConfirmed) {
		$.ajax({
			url : "/user/book/cancelHoBook",
			type : "POST",
			data : {"h_idx" : hIdx},
			success : function(status) {
				if (status == "success") {
					Swal.fire({
						  icon: 'success',
						  text: '신청이 취소되었습니다.',
						closeOnClickOutside : false
					  }).then(function(){
					  	location.reload();
					  });
				} else if (status == "error") {
					Swal.fire({
						icon : 'warning',
						text : '선택하신 도서는 취소가 불가합니다.',
					});
				}
			},
			error : function() {
				Swal.fire({
					icon : 'error',
					title : '에러 발생',
					text : '관리자에게 문의바랍니다.',
				});
			}
		});
	}
	});
}

// 취소 불가
function cannotCancel(h_state){
	if(h_state == "접수취소"){
		Swal.fire({
			icon : 'warning',
			text : '선택하신 도서는 관리자권한으로 취소되었습니다.',
		});
	} else if(h_state == "처리중"){
		Swal.fire({
			icon : 'info',
			text : '선택하신 도서는 현재 처리중입니다.',
		});
	} else{
		Swal.fire({
			icon : 'success',
			text : '선택하신 도서는 현재 이용가능한 상태입니다.',
		});
	}
}
</script>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="container min-vh-100">
		<h3 class="text-bold">
			<img src="/resources/images/myLibrary/ho_book.png" width="35px"
				height="35px"> 희망도서 신청내역
		</h3>
		<hr>
		<c:choose>
			<c:when test="${myHopeBook[0].ho_book_cnt > 0 }">
				<div class="card-style mb-30">
					<h4>희망도서 신청건수 : ${myHopeBook[0].ho_book_cnt} 건</h4>
					<hr>
					<ul>
						<c:forEach var="myHopeBook" items="${myHopeBook}">
							<li style="list-style: none;">
								<div class="row mb-4">
									<div class="col col-md-auto" style="margin-right: 30px;">
										<img src="${myHopeBook.h_url}"
											style="width: 150px; height: 200px; border: 1px solid #dee2e6;">
									</div>
									<div class="col detail"
										style="margin-right: 30px; margin-left: 10px;">
										<p>
											<a href="${myHopeBook.h_link}">${myHopeBook.h_name}</a>
										</p>
										<p>
											<span>작가 : ${myHopeBook.h_author} </span> <span>출판사 :
												${myHopeBook.h_pub} </span> <span>발행연도 :
												${myHopeBook.h_year}</span>
										</p>
										<p>
											<span>도서분류 : ${myHopeBook.h_category} </span> <span>ISBN
												: ${myHopeBook.h_isbn} </span>
										</p>
										<p>
											<span>신청일 : ${myHopeBook.h_regdate}</span>
										</p>
									</div>
									<div class="col align-self-center" style="text-align: center;">
										<c:choose>
											<c:when test="${myHopeBook.h_state == '신청완료'}">
												<p>
													<input type="button" value="신청 취소" id="main-btn"
														onclick="cancelHoBook(${myHopeBook.h_idx})">
												</p>
											</c:when>
											<c:when test="${myHopeBook.h_state == '접수취소'}">
												<p>
													<input type="button" value="접수 취소" id="main-btn"
														style="background-color: lightgray;"
														onclick="cannotCancel('${myHopeBook.h_state}')">
												</p>
											</c:when>
											<c:when test="${myHopeBook.h_state == '처리중'}">
												<p>
													<input type="button" value="처리 중" id="main-btn"
														style="background-color: #C1E8F5;"
														onclick="cannotCancel('${myHopeBook.h_state}')">
												</p>
											</c:when>
											<c:otherwise>
												<p>
													<input type="button" value="이용 가능" id="main-btn"
														style="background-color: #EBF0C5;"
														onclick="cannotCancel('${myHopeBook.h_state}')">
												</p>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</li>
							<hr>
						</c:forEach>
					</ul>
				</div>
			</c:when>
			<c:otherwise>
				<div class="card-style mb-30" style="height: 150px;">
					<h4>희망도서 신청건수 : ${myHopeBook[0].ho_book_cnt} 건</h4>
					<hr>
					<h5 class="text-bold" align="center">신청하신 희망도서가 없습니다.</h5>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</body>
<jsp:include page="../common/footer.jsp"></jsp:include>
</html>