package kr.com.inspect.service;

import java.util.List;

import kr.com.inspect.dto.Rule;

/**
 * 전사규칙에 관한 Service 인터페이스
 * @author Yeonhee Kim
 * @version 1.0
 */
public interface RuleService {
	
	/**
	 * 전사규칙 대분류, 중분류, 소분류 카테고리 리스트를 반환함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @return 전사규칙 대분류, 중분류, 소분류 카테고리 리스트
	 */
	public List<Rule> getRuleCategory(String top_level_id, 
											String middle_level_id);
	
	/**
	 * 선택된 카테고리에 해당되는 전사규칙 리스트를 조인해서 가져옴
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @return 선택된 카테고리에 해당되는 전사규칙 리스트
	 */
	public List<Rule> getRuleListUsingJoin(String top_level_id, 
												String middle_level_id,
												String bottom_level_id);
}
