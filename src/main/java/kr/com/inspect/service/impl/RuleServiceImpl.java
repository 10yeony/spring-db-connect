package kr.com.inspect.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.RuleDao;
import kr.com.inspect.dto.Rule;
import kr.com.inspect.service.RuleService;

/**
 * 전사규칙에 관한 Service 구현 클래스
 * @author Yeonhee Kim
 * @version 1.0
 */
@Service
public class RuleServiceImpl implements RuleService {

	/**
	 * 전사규칙에 관한 DAO 인터페이스
	 */
	@Autowired
	private RuleDao ruleDao;
	
	/**
	 * 전사규칙 대분류, 중분류, 소분류 카테고리 리스트를 반환함
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @return 전사규칙 대분류, 중분류, 소분류 카테고리 리스트
	 */
	@Override
	public List<Rule> getRuleCategory(String top_level_id, 
											String middle_level_id) {
		
		List<Rule> list = new ArrayList<>();
		
		/* 대분류 목록 불러오기 */
		if(top_level_id == "" && middle_level_id == "") {
			list = ruleDao.getAllRuleTopLevel();
		}
		
		/* 중분류 목록 불러오기 */
		else if(middle_level_id == "") {
			list = ruleDao.getAllRuleMiddleLevel(Integer.parseInt(top_level_id));
		}
		
		/* 소분류 카테고리 목록 불러오기 */
		else {
			list = ruleDao.getAllRuleBottomLevel(Integer.parseInt(top_level_id), 
														Integer.parseInt(middle_level_id));
		}
		return list;
	}

	/**
	 * 선택된 카테고리에 해당되는 전사규칙 리스트를 조인해서 가져옴
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @return 선택된 카테고리에 해당되는 전사규칙 리스트
	 */
	@Override
	public List<Rule> getRuleListUsingJoin(String top_level_id, 
													String middle_level_id, 
													String bottom_level_id) {
		
		List<Rule> list = new ArrayList<>();
		
		/* 전사규칙 리스트를 조인 */
		if(top_level_id == "" && middle_level_id == "" && bottom_level_id == "") {
			list = ruleDao.getRuleList();
		}
		
		/* 대분류 아이디를 통해 전사규칙 리스트를 조인 */
		else if(middle_level_id == "" && bottom_level_id == "") {
			list = ruleDao.getRuleListByTopId(Integer.parseInt(top_level_id));
		}
		
		/* 대분류, 중분류 아이디를 통해 전사규칙 리스트를 조인 */
		else if(bottom_level_id == "") {
			list = ruleDao.getRuleListByTopMiddleId(Integer.parseInt(top_level_id), 
															Integer.parseInt(middle_level_id));
		}
		
		/* 대분류, 중분류, 소분류 아이디를 통해 전사규칙 리스트를 조인 */
		else {
			list = ruleDao.getRuleListByTopMiddleBottomId(Integer.parseInt(top_level_id), 
																	Integer.parseInt(middle_level_id), 
																	Integer.parseInt(bottom_level_id));
		}
		return list;
	}

}
