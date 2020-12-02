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
}
