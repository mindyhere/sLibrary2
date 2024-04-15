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
	function cancelHoBook(l_memno, l_bookid) {
		Swal.fire({
			text : "선택하신 희망 도서를 취소하시겠습니까?",
			buttons : [ "취소", "확인" ],
		}).then(function(isConfirmed) {
			if (isConfirmed) {
				$.ajax({
					url : "/user/book/renew",
					type : "POST",
					data : {
						"m_no" : l_memno,
						"b_id" : l_bookid
					},
					success : function(status) {
						if (status == 0) {
							Swal.fire({
								  title: '',
								  text: '신청이 취소되었습니다.',
								  icon: 'success',
								closeOnClickOutside : false
							  }).then(function(){
							  	location.reload();
							  });
						} else if (status == 1) { // 애초에 취소버튼 비활성화하기?
							Swal.fire({
								icon : 'warning',
								text : '현재 000 단계로 취소가 불가합니다.',
							});
						}	else if (status == 2) {
							Swal.fire({
								icon : 'warning',
								text : '현재 연체 중으로 이용 불가 상태입니다.',
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
		})
	}
	
	// 취소 불가
	function cannotCancel(){
		Swal.fire({
			icon : 'warning',
			text : '선택하신 도서는 취소가 불가합니다.'',
		});
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
			<c:when test="${myHopeBook.size() > 0 }">
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
											<%-- <a href="/user/search/bookInfo/${myHopeBook.h_id}">${myHopeBook.h_name}</a> --%>
											<a href="${myHopeBook.h_url}">${myHopeBook.h_name}</a>
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
											<span>신청일 : ${myHopeBook.h_regdate}</span> <span>처리 현황
												: ${myHopeBook.h_state}</span>
										</p>
									</div>
									<div class="col align-self-center" style="text-align: center;">
										<p>
											<input type="button" value="신청 취소" id="main-btn"
												onclick="renewDate(${mId}, ${myHopeBook.h_idx})">
										</p>
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
					<h4>희망도서 신청건수 : ${myHopeBook.size()} 건</h4>
					<hr>
					<h5 class="text-bold" align="center">신청하신 희망도서가 없습니다.</h5>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</body>
<jsp:include page="../common/footer.jsp"></jsp:include>
</html>