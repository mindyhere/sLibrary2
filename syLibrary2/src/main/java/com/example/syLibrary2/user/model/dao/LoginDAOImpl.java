package com.example.syLibrary2.user.model.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.user.model.dto.LoginDTO;

@Repository
public class LoginDAOImpl implements LoginDAO {
	@Autowired
	SqlSession sqlSession;

	// 비밀번호 복호화
	@Override
	public String chkPasswd(String mId) {
		String chkPasswd = sqlSession.selectOne("login.chk_passwd", mId);
		return chkPasswd;
	}

	// 로그인
	@Override
	public String loginChk(String mId, String mPasswd) {
		Map<String, String> map = new HashMap<>();
		map.put("mId", mId);
		map.put("mPasswd", mPasswd);
		String m_name = sqlSession.selectOne("login.login_check", map);
		return m_name;
	}

	// 아이디-이메일 찾기
	@Override
	public String searchIdEmail(String mEmail, String mName, String mBirthDate) {
		Map<String, Object> map = new HashMap<>();
		map.put("mEmail", mEmail);
		map.put("mName", mName);
		map.put("mBirthDate", mBirthDate);
		String searchId = sqlSession.selectOne("login.searchIdEmail", map);
		return searchId;
	}

	// 아이디-전화번호 찾기
	@Override
	public String searchIdTel(String mTel, String mName, String mBirthDate) {
		Map<String, Object> map = new HashMap<>();
		map.put("mTel", mTel);
		map.put("mName", mName);
		map.put("mBirthDate", mBirthDate);
		String searchId = sqlSession.selectOne("login.searchIdTel", map);
		return searchId;
	}

	// 비밀번호-이메일 찾기
	@Override
	public String searchPwEmail(String mEmail, String mId, String mName, String mBirthDate) {
		Map<String, Object> map = new HashMap<>();
		map.put("mEmail", mEmail);
		map.put("mId", mId);
		map.put("mName", mName);
		map.put("mBirthDate", mBirthDate);
		String searchPw = sqlSession.selectOne("login.searchPwEmail", map);
		return searchPw;
	}

	// 비밀번호-전화번호 찾기
	@Override
	public String searchPwTel(String mTel, String mId, String mName, String mBirthDate) {
		Map<String, Object> map = new HashMap<>();
		map.put("mTel", mTel);
		map.put("mId", mId);
		map.put("mName", mName);
		map.put("mBirthDate", mBirthDate);
		String searchPw = sqlSession.selectOne("login.searchPwTel", map);
		return searchPw;
	}
}
