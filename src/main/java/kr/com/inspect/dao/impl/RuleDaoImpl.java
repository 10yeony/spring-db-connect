package kr.com.inspect.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.RuleDao;
import kr.com.inspect.dto.ApiDesc;
import kr.com.inspect.dto.CustomRule;
import kr.com.inspect.dto.Rule;

/**
 * 전사규칙과 관련된 DAO 구현 클래스
 * 
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
	 * Api Mapper 네임스페이스
	 */
	private final String apiNS = "ApiMapper.";

	/**
	 * 전사규칙 대분류 드롭다운 목록을 리턴함
	 * 
	 * @return 전사규칙 대분류 목록
	 */
	@Override
	public List<Rule> getAllRuleTopLevel() {
		return sqlSession.selectList(ruleNS + "getAllTopLevel");
	}

	/**
	 * 전사규칙 중분류 드롭다운 목록을 리턴함
	 * 
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @return 전사규칙 중분류 목록
	 */
	@Override
	public List<Rule> getAllRuleMiddleLevel(int top_level_id) {
		return sqlSession.selectList(ruleNS + "getAllMiddleLevel", top_level_id);
	}

	/**
	 * 전사규칙 소분류 드롭다운 목록을 리턴함
	 * 
	 * @param top_level_id    전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @return 전사규칙 소분류 목록
	 */
	@Override
	public List<Rule> getAllRuleBottomLevel(int top_level_id, int middle_level_id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("top_level_id", top_level_id);
		map.put("middle_level_id", middle_level_id);
		return sqlSession.selectList(ruleNS + "getAllBottomLevel", map);
	}

	/**
	 * 전사규칙 소분류 아이디로 해당되는 항목을 리턴함
	 * 
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @return 전사규칙 소분류 항목
	 */
	@Override
	public Rule getRuleBottomLevel(int bottom_level_id) {
		return sqlSession.selectOne(ruleNS + "getBottomLevelById", bottom_level_id);
	}

	/**
	 * 전사규칙 리스트를 조인하여 리턴함
	 * 
	 * @return 조인한 전사규칙 리스트
	 */
	@Override
	public List<Rule> getRuleList() {
		return sqlSession.selectList(ruleNS + "getRuleList");
	}

	/**
	 * 대분류 아이디를 통해 전사규칙 리스트를 조인하여 리턴함
	 * 
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @return 조인한 전사규칙 리스트
	 */
	@Override
	public List<Rule> getRuleListByTopId(int top_level_id) {
		return sqlSession.selectList(ruleNS + "getRuleListByTopId", top_level_id);
	}

	/**
	 * 대분류, 중분류 아이디를 통해 전사규칙 리스트를 조인하여 리턴함
	 * 
	 * @param top_level_id    전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @return 조인한 전사규칙 리스트
	 */
	@Override
	public List<Rule> getRuleListByTopMiddleId(int top_level_id, int middle_level_id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("top_level_id", top_level_id);
		map.put("middle_level_id", middle_level_id);
		return sqlSession.selectList(ruleNS + "getRuleListByTopMiddleId", map);
	}

	/**
	 * 대분류, 중분류, 소분류 아이디를 통해 전사규칙 리스트를 조인하여 리턴함
	 * 
	 * @param top_level_id    전사규칙 대분류 아이디
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
		return sqlSession.selectList(ruleNS + "getRuleListByTopMiddleBottomId", map);
	}

	/**
	 * 대분류를 등록함
	 * 
	 * @param rule 대분류 등록을 위한 Rule 객체
	 * @return 등록된 대분류 DB row의 수
	 */
	@Override
	public int registerTopLevel(Rule rule) {
		return sqlSession.insert(ruleNS + "registerTopLevel", rule);
	}

	/**
	 * 중분류를 등록함
	 * 
	 * @param rule 중분류 등록을 위한 Rule 객체
	 * @return 등록된 중분류 DB row의 수
	 */
	@Override
	public int registerMiddleLevel(Rule rule) {
		return sqlSession.insert(ruleNS + "registerMiddleLevel", rule);
	}

	/**
	 * 전사규칙을 등록함
	 * 
	 * @param rule 전사규칙 등록을 위한 Rule 객체
	 * @return 등록된 소분류 DB row의 수
	 */
	@Override
	public int registerBottomLevel(Rule rule) {
		return sqlSession.insert(ruleNS + "registerBottomLevel", rule);
	}

	/**
	 * 대분류가 중복되지 않았는지 해당되는 row 개수를 가져옴
	 * 
	 * @param rule 대분류 중복검사를 위한 Rule 객체
	 * @return 존재하는 row의 개수
	 */
	@Override
	public int isExistTopLevel(Rule rule) {
		int id = 0;
		try {
			id = sqlSession.selectOne(ruleNS + "isExistTopLevel", rule);
		} catch (NullPointerException e) {
			return 0;
		}
		return id;
	}

	/**
	 * 중분류가 중복되지 않았는지 해당되는 row 개수를 가져옴
	 * 
	 * @param rule 중분류 중복검사를 위한 Rule 객체
	 * @return 존재하는 row의 개수
	 */
	@Override
	public int isExistMiddleLevel(Rule rule) {
		int id = 0;
		try {
			id = sqlSession.selectOne(ruleNS + "isExistMiddleLevel", rule);
		} catch (NullPointerException e) {
			return 0;
		}
		return id;
	}

	/**
	 * 전사규칙(소분류)이 중복되지 않았는지 해당되는 row 개수를 가져옴
	 * 
	 * @param rule 전사규칙(소분류) 중복검사를 위한 Rule 객체
	 * @return 존재하는 row의 개수
	 */
	@Override
	public int isExistBottomLevel(Rule rule) {
		int id = 0;
		try {
			id = sqlSession.selectOne(ruleNS + "isExistBottomLevel", rule);
		} catch (NullPointerException e) {
			return 0;
		}
		return id;
	}

	/**
	 * 전사규칙(소분류)의 파일명을 업데이트함
	 * 
	 * @param rule 전사규칙(소분류) 파일명 업데이트를 위한 Rule 객체
	 * @return 업데이트된 row의 개수
	 */
	@Override
	public int updateBottomLevelFileName(Rule rule) {
		return sqlSession.update(ruleNS + "updateBottomLevelFileName", rule);
	}

	/**
	 * 해당되는 대분류와 종속된 중분류, 소분류를 삭제함
	 * 
	 * @param id 대분류 아이디
	 * @return 삭제된 row의 수
	 */
	@Override
	public int deleteTopLevel(int id) {
		return sqlSession.delete(ruleNS + "deleteTopLevel", id);
	}

	/**
	 * 해당되는 중분류와 종속된 소분류를 삭제함
	 * 
	 * @param id 중분류 아이디
	 * @return 삭제된 row의 수
	 */
	@Override
	public int deleteMiddleLevel(int id) {
		return sqlSession.delete(ruleNS + "deleteMiddleLevel", id);
	}

	/**
	 * 해당되는 전사규칙(소분류)를 삭제함
	 * 
	 * @param id 전사규칙(소분류) 아이디
	 * @return 삭제된 row의 수
	 */
	@Override
	public int deleteBottomLevel(int id) {
		return sqlSession.delete(ruleNS + "deleteBottomLevel", id);
	}

	/**
	 * 사용자가 작성한 전사규칙(소분류) 코드 내용을 DB에 저장함
	 * 
	 * @param rule 전사규칙(소분류) 코드 내용 업데이트를 위한 Rule 객체
	 * @return 업데이트된 row의 수
	 */
	@Override
	public int updateContents(Rule rule) {
		return sqlSession.update(ruleNS + "updateContents", rule);
	}

	/**
	 * 사용자가 작성한 전사규칙(소분류)을 컴파일하고 결과값을 DB에 저장함
	 * 
	 * @param rule 전사규칙(소분류) 컴파일 결과값 업데이트를 위한 Rule 객체
	 * @return 업데이트된 row의 수
	 */
	public int updateRuleCompileResult(Rule rule) {
		return sqlSession.update(ruleNS + "updateRuleCompileResult", rule);
	}

	/**
	 * 클래스 아이디로 해당되는 클래스 정보를 가져옴
	 * 
	 * @param class_id DB 상의 클래스 아이디
	 * @return 해당되는 클래스 정보가 담긴 List
	 */
	public List<ApiDesc> getApiClass(int class_id) {
		return sqlSession.selectList(apiNS + "getApiClass", class_id);
	}

	/**
	 * 클래스 아이디로 해당되는 클래스 필드 정보를 가져옴
	 * 
	 * @param class_id DB 상의 클래스 아이디
	 * @return 해당되는 클래스 필드 정보가 담긴 List
	 */
	public List<ApiDesc> getApiClassField(int class_id) {
		return sqlSession.selectList(apiNS + "getApiClassField", class_id);
	}

	/**
	 * 클래스 아이디로 해당되는 클래스 생성자 정보를 가져옴
	 * 
	 * @param class_id DB 상의 클래스 아이디
	 * @return 해당되는 클래스 생성자 정보가 담긴 List
	 */
	public List<ApiDesc> getApiClassConstructor(int class_id) {
		return sqlSession.selectList(apiNS + "getApiClassConstructor", class_id);
	}

	/**
	 * 클래스 아이디로 해당되는 클래스 메소드 정보를 가져옴
	 * 
	 * @param class_id DB 상의 클래스 아이디
	 * @return 해당되는 클래스 메소드 정보가 담긴 List
	 */
	public List<ApiDesc> getApiClassMethod(int class_id) {
		return sqlSession.selectList(apiNS + "getApiClassMethod", class_id);
	}

	@Override
	public int registerCustom(CustomRule customrule) {
		return sqlSession.insert(ruleNS + "registerCustom", customrule);
	}

//	@Override
//	public int isExistCustom(CustomRule customrule) {
//		int id = 0;
//		try {
//			id = sqlSession.selectOne(ruleNS + "isExistCustom", customrule);
//		} catch (NullPointerException e) {
//			return 0;
//		}
//		return id;
//	}
}
