<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link type="text/css" rel="stylesheet" href="/resources/static/user.css">
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
 <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> 
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>

<script>
$(function() {
	reservCheck();
});

 function reservCheck(){
	// 대출가능여부 확인
	 $.ajax({
			url:"/user/book/rebookAlert",
			success: function(data){
				console.log(data);
			 	for (let i = 0; i < data.length; i++){
			 		if(data[i].r_reservation == 0){
			 			console.log(data[i].b_name);
			 			myAlert("info","잠깐!", "대출가능한 책이 있습니다.");
			 			return;
			 		} 		
				}
			},
			error: function(request, status, error){
				myAlert('error', 'Error!', '관리자에게 문의바랍니다.');
			}
		});
} 

function myConfirm(title, msg, icon, url){
		Swal.fire({
			title: title,
			html: msg,
			icon: icon,
			showDenyButton: true,
			reverseButtons: true,
			confirmButtonText: "YES",
			denyButtonText: "NO"
			}).then((result) => {
			if (result.isConfirmed) {
				location.href=url;
			}else if (result.isDenied) {
				location.reload();
			}
		});
}
function myAlert(icon, title, msg){
		Swal.fire({
			icon: icon,
			title: title,
			text: msg,
			confirmButtonText: "OK"
		});
}
 
//대출신청 기능
function checkOut(b_id){
	$.ajax({
		url:'/checkout/'+b_id,
		success: function(result) {
			console.log(result);
			if(result=="Not possible") {
				myConfirm(result,
						"나의서재에서 이용현황을 확인해주세요.<br>해당 페이지로 이동할까요?", 
						"error", 
						"/user/book/myLibrary/${mId}");
			} else {
				console.log("대출신청 결과? "+result);
				// 대출신청 성공 시 re_book에서 레코드 삭제
				$.ajax({
					url: "/user/book/myres_delete",
					type: "post",
					data: {"b_id" : b_id},
					success : function(txt) {
						console.log("예약테이블 delete 결과? "+txt);
						myConfirm(txt,
							"나의서재에서 신청내역을 조회할 수 있습니다.<br>해당 페이지로 이동할까요?", 
							"success",
							"/user/book/myLibrary/${mId}");
						//setTimeout('location.reload()',2000); 
					}
				});
			}	
		},
		error: function(err) {
			console.log("**"+err);
			myConfirm("Not possible",
					"나의서재에서 이용현황을 확인해주세요.<br>해당 페이지로 이동할까요?", 
					"error", 
					"/user/book/myLibrary/${mId}");
		}
	});
}

//예약취소
function reDelete(r_bookid) {
	let m_id = "${sessionScope.mId}";
	Swal.fire({
		title: "잠깐!",
		text : "예약을 취소하시겠습니까?",
		icon: "quesiton",
		showDenyButton: true,
		reverseButtons: true,
		confirmButtonText: "YES",
		denyButtonText: "NO"
	}).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url : "/user/book/res_delete",
				type : "post",
				data : {
					"m_id" : m_id,
					"r_bookid" : r_bookid
				},
				success : function(data) {
					myAlert("success", "success", "예약이 취소되었습니다.");
					setTimeout('location.reload()',2000); 
				}, 	
				error : function() {
					myAlert('error', 'Error!', '관리자에게 문의바랍니다.');
				}
			});
		}else if (result.isDenied) {
			location.reload();
		}
	})
}
</script>

<style>
.swal2-confirm {
	background-color: #FEC5BB !important;
	border: 1px solid #FEC5BB !important;
	box-shadow: none !important;
	outline: none !important;
	height: 44px;
}

.swal2-deny {
	background-color: #C6C7C8 !important;
	border: 1px solid #C6C7C8 !important;
	box-shadow: none !important;
	outline: none !important;
	height: 44px;
}
</style>
</head>
<body>
<%@ include file="../common/header.jsp"%>
	<div class="container min-vh-100">
		<h3 class="text-bold">
			<img src="/resources/images/myLibrary/reserv.png"
				width="35px" height="35px"> 예약 중인 도서
		</h3>
		<hr>
		<c:choose>
			<c:when test="${myReBook.size() > 0 }">
				<div class="card-style mb-30">
					<h4>예약 현황건수 : ${myReBook[0].re_book_cnt}건</h4>
					<hr>
					<ul>
						<c:forEach var="myRList" items="${myReBook}">
							<li style="list-style: none;">
								<div class="row mb-4">
									<!-- 이미지 -->
									<div class="col col-md-auto">
										<img src="${myRList.b_url}"
											style="width: 150px; height: 200px;"> 
									</div>
									<div class="col detail">
										<p>
											<a
												href="/user/search/bookInfo/${myRList.b_id}">${myRList.b_name}</a>
										</p>
										<p>
											<span>작가 : ${myRList.b_author} </span> <span> 출판사 :
												${myRList.b_pub} </span> <span>발행연도 : ${myRList.b_year}</span>
										</p>								
										<p>
											<c:set var="now" value="<%=new java.util.Date()%>" />
											<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"
												var="today" />

											<span>예약일 : ${myRList.r_regdate}</span> <span>예약순번
												:${myRList.r_reservation}</span> <span></span>
										</p>
									</div>
									<div class="col detail" style="margin-left: 50px;">
										<ul>
											<li style="list-style: none; margin-bottom: 20px;"><input type="button" value="예약취소" id="main-btn"
												onclick="reDelete(${myRList.b_id})"></li>
											<li style="list-style: none;">											
												<c:choose>
													<c:when test="${myRList.r_reservation ==0}">
														<input type="button" value="대출신청" id="main-btn" onclick="checkOut(${myRList.b_id})"> 
													</c:when>
													<c:otherwise>
														<input type="button" value="대출불가" id="main-btn" onclick="checkOut(${myRList.b_id})" disabled style="background-color:#C6C7C8;">
													</c:otherwise>
												</c:choose>
											</li>
										</ul>
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
					<h4>예약 현황건수 : ${myReBook.size()} 건</h4>
					<hr>
					<h5 class="text-bold" align="center">현재 예약 중인 도서가 없습니다.</h5>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</body>
<%@ include file="../common/footer.jsp"%>
</html>