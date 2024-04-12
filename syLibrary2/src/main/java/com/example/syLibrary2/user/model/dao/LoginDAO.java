package com.example.syLibrary2.user.model.dao;

public interface LoginDAO {

	// 비밀번호 복호화
	String chkPasswd(String mId);
	
	// 로그인
	String loginChk(String mId, String mpasswd);

	// 아이디-이메일 찾기
	String searchIdEmail(String mEmail, String mName, String mBirthDate);

	// 아이디-전화번호 찾기
	String searchIdTel(String mTel, String mName, String mBirthDate);

	// 비밀번호-이메일 찾기
	String searchPwEmail(String mEmail, String mId, String mName, String mBirthDate);

	// 비밀번호-전화번호 찾기
	String searchPwTel(String mTel, String mId, String mName, String mBirthDate);
	
	// 임시 비밀번호로 업데이트
	void setRandomPw(String randomPw, String mId, String mEmail, String mTel);
	
	// 전화번호로 비밀번호 찾을 때 메일 주소 찾기
	String getEmail(String mTel, String mId, String mName, String mBirthDate);
}
