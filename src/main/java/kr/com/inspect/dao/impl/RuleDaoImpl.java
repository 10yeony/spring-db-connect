package kr.com.inspect.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.RuleDao;
import kr.com.inspect.dto.Rule;

/**
 * 전사규칙과 관련된 DAO 구현 클래스
 * @author Yeonhee Kim
 * @version 1.0
 */
@Repository
public class RuleDaoImpl implements RuleDao {
	
	/**
	 * DB 연결을 위한 SqlSession
	 */
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * Rule Mapper 네임스페이스
	 */
	private final String ruleNS = "RuleMapper.";
	
	/**
	 * 전사규칙 대분류 드롭다운 목록을 리턴함
	 * @return 전사규칙 대분류 목록
	 */
	@Override
	public List<Rule> getAllRuleTopLevel() {
		return sqlSession.selectList(ruleNS+"getAllTopLevel");
	}

	/**
	 * 전사규칙 중분류 드롭다운 목록을 리턴함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @return 전사규칙 중분류 목록
	 */
	@Override
	public List<Rule> getAllRuleMiddleLevel(int top_level_id) {
		return sqlSession.selectList(ruleNS+"getAllMiddleLevel", top_level_id);
	}

	/**
	 * 전사규칙 소분류 드롭다운 목록을 리턴함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @return 전사규칙 소분류 목록
	 */
	@Override
	public List<Rule> getAllRuleBottomLevel(int top_level_id, int middle_level_id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("top_level_id", top_level_id);
		map.put("middle_level_id", middle_level_id);
		return sqlSession.selectList(ruleNS+"getAllBottomLevel", map);
	}

	/**
	 * 전사규칙 소분류 아이디로 해당되는 항목을 리턴함
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @return 전사규칙 소분류 항목
	 */
	@Override
	public Rule getRuleBottomLevel(int bottom_level_id) {
		return sqlSession.selectOne(ruleNS+"getBottomLevelById", bottom_level_id);
	}

	/**
	 * 전사규칙 리스트를 조인하여 리턴함
	 * @return 조인한 전사규칙 리스트
	 */
	@Override
	public List<Rule> getRuleList() {
		return sqlSession.selectList(ruleNS+"getRuleList");
	}

	/**
	 * 대분류 아이디를 통해 전사규칙 리스트를 조인하여 리턴함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @return 조인한 전사규칙 리스트
	 */
	@Override
	public List<Rule> getRuleListByTopId(int top_level_id) {
		return sqlSession.selectList(ruleNS+"getRuleListByTopId", top_level_id);
	}

	/**
	 * 대분류, 중분류 아이디를 통해 전사규칙 리스트를 조인하여 리턴함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @return 조인한 전사규칙 리스트
	 */
	@Override
	public List<Rule> getRuleListByTopMiddleId(int top_level_id, int middle_level_id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("top_level_id", top_level_id);
		map.put("middle_level_id", middle_level_id);
		return sqlSession.selectList(ruleNS+"getRuleListByTopMiddleId", map);
	}

	/**
	 * 대분류, 중분류, 소분류 아이디를 통해 전사규칙 리스트를 조인하여 리턴함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @return 조인한 전사규칙 리스트
	 */
	@Override
	public List<Rule> getRuleListByTopMiddleBottomId(int top_level_id, int middle_level_id, int bottom_level_id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("top_level_id", top_level_id);
		map.put("middle_level_id", middle_level_id);
		map.put("bottom_level_id", bottom_level_id);
		return sqlSession.selectList(ruleNS+"getRuleListByTopMiddleBottomId", map);
	}
}
