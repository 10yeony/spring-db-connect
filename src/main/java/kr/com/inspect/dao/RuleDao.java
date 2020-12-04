package kr.com.inspect.dao;

import java.util.List;

import kr.com.inspect.dto.Rule;

/**
 * 전사규칙과 관련된 DAO 인터페이스
 * @author Yeonhee Kim
 * @version 1.0
 */
public interface RuleDao {
	/**
	 * 전사규칙 대분류 드롭다운 목록을 리턴함
	 * @return 전사규칙 대분류 목록
	 */
	public List<Rule> getAllRuleTopLevel();
	
	/**
	 * 전사규칙 중분류 드롭다운 목록을 리턴함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @return 전사규칙 중분류 목록
	 */
	public List<Rule> getAllRuleMiddleLevel(int top_level_id);
	
	/**
	 * 전사규칙 소분류 드롭다운 목록을 리턴함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @return 전사규칙 소분류 목록
	 */
	public List<Rule> getAllRuleBottomLevel(int top_level_id, int middle_level_id);
	
	/**
	 * 전사규칙 소분류 아이디로 해당되는 항목을 리턴함
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @return 전사규칙 소분류 항목
	 */
	public Rule getRuleBottomLevel(int bottom_level_id);
	
	/**
	 * 전사규칙 리스트를 조인하여 리턴함
	 * @return 조인한 전사규칙 리스트
	 */
	public List<Rule> getRuleList();
	
	/**
	 * 대분류 아이디를 통해 전사규칙 리스트를 조인하여 리턴함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @return 조인한 전사규칙 리스트
	 */
	public List<Rule> getRuleListByTopId(int top_level_id);
	
	/**
	 * 대분류, 중분류 아이디를 통해 전사규칙 리스트를 조인하여 리턴함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @return 조인한 전사규칙 리스트
	 */
	public List<Rule> getRuleListByTopMiddleId(int top_level_id, 
														int middle_level_id);
	
	/**
	 * 대분류, 중분류, 소분류 아이디를 통해 전사규칙 리스트를 조인하여 리턴함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @return 조인한 전사규칙 리스트
	 */
	public List<Rule> getRuleListByTopMiddleBottomId(int top_level_id,
																int middle_level_id,
																int bottom_level_id);
	
	/**
	 * 대분류를 등록함
	 * @param rule 대분류 등록을 위한 Rule 객체 
	 * @return 등록된 대분류 DB row의 수
	 */
	public int registerTopLevel(Rule rule);
	
	/**
	 * 중분류를 등록함
	 * @param rule 중분류 등록을 위한 Rule 객체 
	 * @return 등록된 중분류 DB row의 수
	 */
	public int registerMiddleLevel(Rule rule);
	
	/**
	 * 전사규칙을 등록함
	 * @param rule 전사규칙 등록을 위한 Rule 객체
	 * @return 등록된 소분류 DB row의 수
	 */
	public int registerBottomLevel(Rule rule);
	
	/**
	 * 대분류가 중복되지 않았는지 해당되는 row 개수를 가져옴
	 * @param rule 대분류 중복검사를 위한 Rule 객체
	 * @return 존재하는 row의 개수
	 */
	public int isExistTopLevel(Rule rule);
	
	/**
	 * 중분류가 중복되지 않았는지 해당되는 row 개수를 가져옴
	 * @param rule 중분류 중복검사를 위한 Rule 객체
	 * @return 존재하는 row의 개수
	 */
	public int isExistMiddleLevel(Rule rule);
	
	/**
	 * 전사규칙(소분류)이 중복되지 않았는지 해당되는 row 개수를 가져옴
	 * @param rule 전사규칙(소분류) 중복검사를 위한 Rule 객체
	 * @return 존재하는 row의 개수
	 */
	public int isExistBottomLevel(Rule rule);
	
	/**
	 * 전사규칙(소분류)의 파일명을 업데이트함
	 * @param rule 전사규칙(소분류) 파일명 업데이트를 위한 Rule 객체
	 * @return 업데이트된 row의 개수
	 */
	public int updateBottomLevelFileName(Rule rule);
	
	/**
	 * 해당되는 대분류와 종속된 중분류, 소분류를 삭제함
	 * @param id 대분류 아이디
	 * @return 삭제된 row의 수
	 */
	public int deleteTopLevel(int id);
	
	/**
	 * 해당되는 중분류와 종속된 소분류를 삭제함
	 * @param id 중분류 아이디
	 * @return 삭제된 row의 수
	 */
	public int deleteMiddleLevel(int id);
	
	/**
	 * 해당되는 전사규칙(소분류)를 삭제함
	 * @param id 전사규칙(소분류) 아이디
	 * @return 삭제된 row의 수
	 */
	public int deleteBottomLevel(int id);
}
