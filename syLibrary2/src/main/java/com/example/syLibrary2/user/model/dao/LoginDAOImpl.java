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
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	SqlSession sqlSession;

	// 로그인
	@Override
	public String loginChk(String mId, String mPasswd) {
		Map<String, String> map = new HashMap<>();
		map.put("mId", mId);
		map.put("mPasswd", mPasswd);
		log.info("map : " + map);
		String m_name = sqlSession.selectOne("login.login_check", map);
		return m_name;
	}

	// 아이디-이메일 찾기
	@Override
	public String searchIdEmail(LoginDTO dto) {
		String searchId = sqlSession.selectOne("login.searchIdEmail", dto);
		return searchId;
	}

	// 아이디-전화번호 찾기
	@Override
	public String searchIdTel(LoginDTO dto) {
		String searchId = sqlSession.selectOne("login.searchIdTel", dto);
		return searchId;
	}

	// 비밀번호-이메일 찾기
	@Override
	public String searchPwEmail(LoginDTO dto) {
		String searchPw = sqlSession.selectOne("login.searchPwEmail", dto);
		return searchPw;
	}

	// 비밀번호-전화번호 찾기
	@Override
	public String searchPwTel(LoginDTO dto) {
		String searchPw = sqlSession.selectOne("login.searchPwTel", dto);
		return searchPw;
	}
}
