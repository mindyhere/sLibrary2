package com.example.syLibrary2.admin.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.syLibrary2.admin.model.dto.MemoDTO;

@Repository
public class MemoDAOImpl implements MemoDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	public List<MemoDTO> list(int start, int end) {
		Map<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("memo.list",map);
	}

	public void insert(MemoDTO dto) {
		String memo = dto.getMe_memo();
		memo = memo.replace("<", "&lt;");
		memo = memo.replace(">", "&gt;");
		memo = memo.replace("  ","&nbsp;&nbsp;");
		dto.setMe_memo(memo);
		sqlSession.insert("memo.insert",dto);
	}

	public void delete(int me_rownum) {	
		sqlSession.delete("memo.delete",me_rownum);
	}

	public void update(MemoDTO dto) {
		sqlSession.update("memo.update",dto);
	}

	public int count() {
		return sqlSession.selectOne("memo.count");
	}

	public MemoDTO search(MemoDTO dto) {
//		MemoDTO dto = new MemoDTO();
//		try {
//			dto = session.selectOne("memo.search", me_rownum);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(session != null)
//				session.close();
//		}
//		return dto;
		return sqlSession.selectOne("memo.search", dto);
	}
}
