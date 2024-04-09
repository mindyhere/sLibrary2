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
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<script>
	// 연장 신청
	function renewDate(l_memno, l_bookid) {
		Swal.fire({
			text : "선택하신 도서를 연장하시겠습니까?",
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
								  text: '7일 연장되었습니다.',
								  icon: 'success',
								closeOnClickOutside : false
							  }).then(function(){
							  	location.reload();
							  });
						} else if (status == 1) {
							Swal.fire({
								icon : 'warning',
								text : '현재 연체 중으로 이용 불가 상태입니다.',
							});
						}	else if (status == 2) {
							Swal.fire({
								icon : 'warning',
								text : '도서당 연장신청은 1번만 가능합니다.',
							});
						}	else if (status == 3) {
							Swal.fire({
								icon : 'warning',
								text : '예약된 책으로 연장이 불가합니다.',
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
</script>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="container min-vh-100">
		<h3 class="text-bold">
			<img src="/resources/images/myLibrary/loan.png" width="35px"
				height="35px"> 대출 중인 도서
		</h3>
		<hr>
		<c:choose>
			<c:when test="${myLoanBook.size() > 0 }">
				<div class="card-style mb-30">
					<h4>대출 현황건수 : ${myLoanBook[0].lo_book_cnt} 건</h4>
					<hr>
					<ul>
						<c:forEach var="myLoanBook" items="${myLoanBook}">
							<li style="list-style: none;">
								<div class="row mb-4">
									<div class="col col-md-auto" style="margin-right: 30px;">
										<img src="${myLoanBook.b_url}"
											style="width: 150px; height: 200px; border: 1px solid #dee2e6;">
									</div>
									<div class="col detail"
										style="margin-right: 30px; margin-left: 10px;">
										<p>
											<a href="/user/search/bookInfo/${myLoanBook.l_bookid}">${myLoanBook.b_name}</a>
										</p>
										<p>
											<span>작가 : ${myLoanBook.b_author} </span> <span>출판사 :
												${myLoanBook.b_pub} </span> <span>발행연도 :
												${myLoanBook.b_year}</span>
										</p>
										<p>
											<span>도서분류 : ${myLoanBook.b_category} </span> <span>ISBN
												: ${myLoanBook.b_isbn} </span>
										</p>
										<p>
											<c:set var="now" value="<%=new java.util.Date()%>" />
											<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"
												var="today" />
											<span>대출일 : ${myLoanBook.l_lodate}</span>
											<c:if test="${myLoanBook.l_retdate ge today}">
												<span style="color: black;"> 반납예정일 :
													${myLoanBook.l_retdate} </span>
											</c:if>
											<c:if test="${myLoanBook.l_retdate lt today}">
												<span style="color: red;"> 반납예정일 :
													${myLoanBook.l_retdate} (연체중)</span>
											</c:if>
										</p>
									</div>
									<div class="col align-self-center" style="text-align: center;">
										<p>
											<input type="button" value="연장 신청" id="main-btn"
												onclick="renewDate(${myLoanBook.l_memno}, ${myLoanBook.l_bookid})">
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
					<h4>대출 현황건수 : ${myLoanBook.size()} 건</h4>
					<hr>
					<h5 class="text-bold" align="center">현재 대출 중인 도서가 없습니다.</h5>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</body>
<jsp:include page="../common/footer.jsp"></jsp:include>
</html>