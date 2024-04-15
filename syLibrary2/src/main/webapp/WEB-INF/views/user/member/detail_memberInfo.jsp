<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="/resources/images/icon.png" type="image/x-icon">
<link
	rel="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="/resources/static/user.css">
<link rel="stylesheet" href="/resources/static/assets/bootstrap.min.css" />
<link rel="stylesheet" href="/resources/static/assets/lineicons.css" />
<link rel="stylesheet"
	href="/resources/static/assets/materialdesignicons.min.css" />
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
	//이미지 화면출력
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				document.getElementById('url').src = e.target.result;
			};
			reader.readAsDataURL(input.files[0]);
		} else {
			document.getElementById('url').src = "";
		}
	}

	// 전화번호 형식 체크
	function oninputPhone(mTel) {
		mTel.value = mTel.value.replace(/[^0-9]/g, '').replace(
				/(^02.{0}|^01.{1}|[0-9]{3})([0-9]{4})([0-9]{4})/g, "$1-$2-$3");
	}

	// 주소검색 팝업
	function goPopup() {
		var pop = window.open("../../jusoPopup.jsp", "pop",
				"width=570,height=420, scrollbars=yes, resizable=yes");
	}

	// 주소검색 콜백
	function jusoCallBack(zipNo, roadFullAddr, roadAddrPart1, roadAddrPart2,
			mDetailAddress) {
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		document.edit.mZipNo.value = zipNo; //상세 주소
		document.edit.mAddress.value = roadAddrPart1 + roadAddrPart2; //도로명 주소
		document.edit.mDetailAddress.value = mDetailAddress; //상세 주소
	}

	function updateInfo() {
		const mPasswd = $("#pw2").val();
		Swal.fire({
			text : "회원정보를 수정하시겠습니까?",
			showCancelButton : true,
			confirmButtonText : "YES",
		}).then((result) => {
			if (result.isConfirmed) {
			$.ajax({
				url : "/user/member/chkPasswd.do",
				type : "POST",
				dataType : "json",
				data : { mPasswd : mPasswd},
				success : function(status) {
				if (status == 1) {
					document.edit.submit();
				} else if (status == 2) {
					Swal.fire({
						icon : 'warning',
						text : '비밀번호가 일치하지 않습니다.',
					}).then(function() {
						location.reload();
					});
				}
				}, error : function() {
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
</script>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="container">
		<h3 class="text-bold">
			<img src="/resources/images/edit.png" width="35px" height="35px">
			회원정보 수정
		</h3>
		<hr>
		<div class="container">
			<form name="edit" method="post" enctype="multipart/form-data"
				action="/user/member/edit_memberInfo">
				<div class="row">
					<div class="col-3"
						style="background-color: #ededed; border-radius: 20px;">
						<div style="text-align: -webkit-center;">
							<div class="profile-image" style="margin-top: 50px;">
								<img id="url" src="/resources/images/member/${memberInfo.m_img}">
								<div class="update-image">
									<input type="file" name="profile" name="mImg"
										onchange="readURL(this);"> <label for=""><i
										class="lni lni-cloud-upload"></i></label>
								</div>
							</div>
							<br>
							<div style="margin-top: 20px;">
								<h4 class="text-bold text-dark mb-10">${memberInfo.m_name}</h4>
								<p class="text-sm text-gray">${memberInfo.le_name}</p>
							</div>
						</div>
					</div>
					<div class="col-9"
						style="background-color: white; border-radius: 20px; padding: 30px 50px 30px 50px;">
						<div class="input-style-2">
							<label>아이디</label> <input type="text" value="${memberInfo.m_id}"
								readonly>
						</div>
						<div class="input-style-1">
							<label>비밀번호</label> <input type="password"
								value="${memberInfo.m_passwd}" id="pw1" autocomplete="off"
								>
						</div>
						<div class="input-style-1">
							<label>비밀번호 확인</label> <input type="password" id="pw2"
								name="mPasswd" autocomplete="off">
							<p class="text-sm text-gray" id="pwConfirm">비밀번호를 입력해주세요</p>
						</div>

						<div class="input-style-2">
							<label>생년월일</label> <input type="text" name="mBirthDate"
								value="${memberInfo.m_birth_date}" readonly>
						</div>

						<div class="input-style-2">
							<label>가입일</label> <input type="text" name="mYear"
								value="${memberInfo.m_year}" readonly>
						</div>

						<div class="input-style-1">
							<label>전화번호</label> <input type="tel" name="mTel"
								oninput="oninputPhone(mTel)" maxlength="13"
								value="${memberInfo.m_tel}">
						</div>

						<div class="input-style-1">
							<label>이메일</label> <input type="text" name="mEmail"
								value="${memberInfo.m_email}">
						</div>

						<div class="input-style-1">
							<label>우편번호 <input type="button" id="main-btn"
								onclick="goPopup();" value="주소 검색"
								style="width: auto; height: 20px; margin-left: 10px; text-align: center; line-height: 2px;">
							</label> <input type="text" id=zipNo name="mZipNo"
								value="${memberInfo.m_zip_no}">
						</div>

						<div class="input-style-1">
							<label>주소</label> <input type="text" id=mAddress name="mAddress"
								value="${memberInfo.m_address}">
						</div>

						<div class="input-style-1">
							<label>상세 주소</label> <input type="text" id="mDetailAddress"
								name="mDetailAddress" value="${memberInfo.m_detail_address}">
						</div>

						<div style="text-align: center;">
							<input type="hidden" value="${memberInfo.m_id}" name="mId">
							<input type="button" value="정보 수정" id="main-btn"
								onclick="updateInfo()">
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
<jsp:include page="../common/footer.jsp"></jsp:include>
</html>