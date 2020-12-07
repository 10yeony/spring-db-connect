package kr.com.inspect.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	
	private Rule rule;
	
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

	/**
	 * 대분류/중분류/소분류를 DB에 등록함
	 * @param level 해당되는 분류(대분류/중분류/소분류)
	 * @param rule DB 등록을 위한 Rule 객체
	 * @return DB에 등록한 row의 수
	 */
	public int registerRule(String level, Rule rule) {
		int id = 0; //아이디
		int result = 0; //등록 후 DB에 추가된 row의 수
		
		switch(level) {
			case "top" :
				id = ruleDao.isExistTopLevel(rule);
				if(id == 0) { //존재하지 않는 경우에만 등록
					result = ruleDao.registerTopLevel(rule);
				}
				break;
			case "middle" :
				id = ruleDao.isExistMiddleLevel(rule);
				if(id == 0) { //존재하지 않는 경우에만 등록
					result = ruleDao.registerMiddleLevel(rule);
				}
				break;
			case "bottom" :
				id = ruleDao.isExistBottomLevel(rule); //등록 전 아이디(중복 확인)
				if(id == 0) { //존재하지 않는 경우에만 등록
					result = ruleDao.registerBottomLevel(rule);
					id = ruleDao.isExistBottomLevel(rule); //등록 후 아이디(auto increment된 아이디)
					
					/* 파일명 DB 등록(파일명이 중복되지 않도록 auto increment된 아이디 사용) */
					String fileName = "Rule"+ id ;
					rule.setBottom_level_id(id);
					rule.setFile_name(fileName);
					result += ruleDao.updateBottomLevelFileName(rule);
				}
				break;
		}
		return result;
	}
	


	/**
	 * 대분류/중분류/소분류 아이디로 해당되는 항목을 삭제함
	 * @param level 해당되는 분류(대분류/중분류/소분류)
	 * @param id 대분류/중분류/소분류 아이디
	 * @return DB에서 삭제한 row의 수
	 */
	@Override
	public int deleteRule(String level, int id) {
		int result = 0;
		switch(level) {
			case "top":
				result = ruleDao.deleteTopLevel(id);
				break;
			case "middle":
				result = ruleDao.deleteMiddleLevel(id);
				break;
			case "bottom":
				result = ruleDao.deleteBottomLevel(id);
				break;
		}
		return result;
	}

	@Override
	public Rule getRuleBottomLevel(int bottom_level_id) {
		return ruleDao.getRuleBottomLevel(bottom_level_id);
	}

	@Override
	public int updateContents(Rule rule) {
		return ruleDao.updateContents(rule);
	}

	/*
	 * @Override public int updateContents(int bottom_level_id, String contents) {
	 * int result = 0; return 0; }
	 */
	
}
