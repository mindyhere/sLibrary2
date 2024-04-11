package com.example.syLibrary2.user.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.user.model.dto.MemberDTO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	SqlSession sqlSession;

	@Override // 회원가입
	public void insert_join(MemberDTO dto) {
		sqlSession.insert("member.insert_join", dto);
	}
 
	@Override // 회원 상세페이지
	public MemberDTO detailMember(String mId) {
		return sqlSession.selectOne("member.detail_member", mId);
	}

	@Override // 회원정보 수정
	public void edit_memberInfo(MemberDTO dto) {
		sqlSession.update("member.edit_memberInfo", dto);
	}

	@Override  // 아이디 중복체크
	public int id_check(String mId) {
		 return sqlSession.selectOne("member.id_check", mId);
	} 
}